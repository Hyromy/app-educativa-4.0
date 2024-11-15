package com.example.app.ui.themes.theme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;

import java.util.Objects;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.ResultadoExamenModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.CompletaContenido;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.ResultadoExamen;

public class ThemeFragment extends Fragment {
    private TemaModel tema;
    private UsuarioModel usuario;
    private LinearLayout layoutTheme;

    public static ThemeFragment newInstance() {
        return new ThemeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutTheme = view.findViewById(R.id.layout_theme);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tema = (TemaModel) bundle.getSerializable("tema");
            usuario = (UsuarioModel) bundle.getSerializable("usuario");
            setToolbarTitle(tema.tituloValue);
        }

        setThemeData(view);
        Context context = getContext();
        boolean resultTestLog = userHaveResultTestLog(context);
        if (!resultTestLog) {
            findAndSetExamn(context, tema);
            if (usuario.tipoAdministradorValue) {
                findAndSetExercises(context, tema);
            }
        } else if (resultTestLog) {
            findAndSetExercises(context, tema);
        }
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(title);
        }
    }

    private void setThemeData(View view) {
        TextView title = view.findViewById(R.id.theme_name);
        title.setText(tema.tituloValue);

        TextView description = view.findViewById(R.id.theme_description);
        description.setText(tema.descripcionValue);
    }

    private void findAndSetExamn(Context context, TemaModel tema) {
        ExamenDiagnosticoModel examen = null;

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel[] examenes = crudExamen.readAll();
        crudExamen.close();

        for (ExamenDiagnosticoModel iExamen : examenes) {
            if (iExamen.idTemaValue == tema.idValue) {
                examen = iExamen;
                setTestFrame(context, examen);
                break;
            }
        }
    }

    private void findAndSetExercises(Context context, TemaModel tema) {
        int level = 0;
        ResultadoExamen crudResultadoExamen = new ResultadoExamen(context);
        crudResultadoExamen.open();
        if (crudResultadoExamen.existLogFrom(usuario.idValue, tema.idValue)) {
            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            crudExamen.open();
            int examenId = crudExamen.getIdBy(ExamenDiagnosticoModel.idTema, String.valueOf(tema.idValue));
            ResultadoExamenModel resultado = crudResultadoExamen.getLogFromForeignKey(usuario.idValue, examenId);
            level = resultado.nivelObtenidoValue;
            crudExamen.close();
        }
        crudResultadoExamen.close();

        ContenidoModel contenido = null;

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel[] contenidos = crudContenido.readAll();
        crudContenido.close();

        CompletaContenido crudCompletaContenido = new CompletaContenido(context);
        crudCompletaContenido.open();
        int[] ids = crudCompletaContenido.getIdContentsCompletedByUser(usuario);
        crudCompletaContenido.close();

        mainLoop:
        for (ContenidoModel iContenido : contenidos) {
            if (iContenido.idTemaValue == tema.idValue) {
                for (int idCompletado : ids) {
                    if (idCompletado == iContenido.idValue) {
                        continue mainLoop;
                    }
                }

                contenido = iContenido;
                setActivityFrame(context, contenido.tituloValue, contenido.descripcionValue, String.valueOf(contenido.idValue));
            }
        }
    }

    private void setTestFrame(Context context, ExamenDiagnosticoModel examen) {
        setFrame(context, examen.tituloValue, "Exámen Diagnóstico", "t_" + examen.idValue);
    }

    private void setActivityFrame(Context context, String title, String label, String tag) {
        setFrame(context, title, label, "c_" + tag);
    }

    private void setFrame(Context context, String title, String label,String tag) {
        LinearLayout layout = new LinearLayout(context);
        layout.setId(View.generateViewId());
        layout.setTag(tag);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(32, 32, 32, 0);
        layout.setLayoutParams(layoutParams);
        layout.setBackground(context.getDrawable(R.drawable.rounded_box));
        layout.setBackgroundTintList(context.getResources().getColorStateList(R.color.Greeuttec, context.getTheme()));
        layout.setPadding(48, 48, 48, 48);

        TextView text = new TextView(context);
        text.setText(title);
        text.setTextSize(24);
        layout.addView(text);

        LinearLayout linear = new LinearLayout(context);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearParams.setMargins(0, 32, 0, 0);
        linear.setLayoutParams(linearParams);

        text = new TextView(context);
        text.setText(label);
        text.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        text.setPadding(32, 0, 32, 0);
        linear.addView(text);

        Button button = new Button(context);
        button.setText("Realizar");
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        button.setOnClickListener(onClickTest(tag));
        linear.addView(button);
        layout.addView(linear);

        layoutTheme.addView(layout);
    }

    private View.OnClickListener onClickTest(String tag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("testTag", tag);
                bundle.putSerializable("usuario", usuario);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_theme_to_nav_exercise, bundle);
            }
        };
    }

    private boolean userHaveResultTestLog(Context context) {
        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        int idTema = crudExamen.getIdBy(ExamenDiagnosticoModel.idTema, String.valueOf(tema.idValue));
        crudExamen.close();

        ResultadoExamen crudResultado = new ResultadoExamen(context);
        crudResultado.open();
        boolean log = crudResultado.existLogFrom(usuario.idValue, idTema);
        crudResultado.close();

        return log;
    }
}