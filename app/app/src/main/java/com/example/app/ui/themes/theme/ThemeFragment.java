package com.example.app.ui.themes.theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.example.app.R;

import java.util.Objects;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.ui.themes.exercise.*;
import com.example.app.utils.*;

import org.w3c.dom.Text;

public class ThemeFragment extends Fragment {
    private ThemeViewModel mViewModel;
    private TemaModel tema;
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
            setToolbarTitle(tema.tituloValue);
        }

        setThemeData(view);

        Context context = getContext();
        findAndSetExamn(context, tema);
        findAndSetExercises(context, tema);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        // TODO: Use the ViewModel
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
        for (int i = 1; i <= 3; i++) {
            setActivityFrame(context, "Actividad " + i, "Actividad", "activity_frame__" + tema.idValue + "__" + i);
        }
    }

    private void setTestFrame(Context context, ExamenDiagnosticoModel examen) {
        setFrame(context, examen.tituloValue, "Exámen Diagnóstico", "t_" + examen.idValue);
    }

    private void setActivityFrame(Context context, String title, String label, String tag) {
        setFrame(context, title, "", tag);
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

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_theme_to_nav_exercise, bundle);
            }
        };
    }
}