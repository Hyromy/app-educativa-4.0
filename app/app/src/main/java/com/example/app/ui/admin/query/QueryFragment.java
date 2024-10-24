package com.example.app.ui.admin.query;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.Tema;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryFragment extends Fragment {
    private QueryViewModel mViewModel;
    private String itemsType = null;
    private String table = null;

    public static QueryFragment newInstance() {
        return new QueryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new QueryViewModel();

        Bundle bundle = getArguments();
        if (bundle != null) {
            itemsType = bundle.getString("queryName");
            setToolbarTitle(itemsType);
        }

        view.findViewById(R.id.btn_agregar).setOnClickListener(addListener());
        setSearchView(view);

        Context context = getContext();
        // consultar la base de datos la tabla actual
        table = titleToTable(itemsType);
        getLogs(context, view);
    }

    private void getLogs(Context context, View view) {
        if (table.equals("tema")) {
            setTemaLogs(context, view);

        } else if (table.equals("examen_diagnostico")) {
            setExamenLogs(context, view);

        } else if (table.equals("contenido")) {
            setContenidoLogs(context, view);

        } else {
            generateAutoItems(view, itemsType);
        }
    }

    private void setTemaLogs(Context context, View view) {
        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        crudTema.close();

        for (TemaModel tema : temas) {
            generateItem(view, tema.idValue, tema.tituloValue, table);
        }
    }

    private void setExamenLogs(Context context, View view) {
        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel[] examenes = crudExamen.readAll();
        crudExamen.close();

        for (ExamenDiagnosticoModel examen : examenes) {
            generateItem(view, examen.idValue, examen.tituloValue, table);
        }
    }

    private void setContenidoLogs(Context context, View view) {
        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel[] contenidos = crudContenido.readAll();
        crudContenido.close();

        for (ContenidoModel contenido : contenidos) {
            // extraer el nombre del tema

            generateItem(view, contenido.idValue, contenido.tituloValue + " (from {theme})", table);
        }
    }

    private void setSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private String titleToTable(String s) {
        String x = null;

        if (s.equals("temas")) {
            x = "tema";

        } else if (s.equals("examenes")) {
            x = "examen_diagnostico";

        } else if (s.equals("contenidos")) {
            x = "contenido";

        } else if (s.equals("preguntas")) {
            x = "pregunta_examen";

        } else if (s.equals("actividades")) {
            x = "pregunta_actividad";
        }

        return x;
    }

    private void generateItem(View view, int id, String name, String table) {
        int dp8 = dpToPx(8);
        int dp16 = dp8 * 2;
        String tag = table + "_" + id;
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        Context context = getContext();

        TextView textID = textView(context, "id", String.valueOf(id));
        TextView textName = textView(context, "tx", name);

        LinearLayout linearInfo = linearInfo(context);
        linearInfo.addView(textID);
        linearInfo.addView(textName);

        ImageView trash = imageView(context, tag, R.drawable.ic_trash);
        trash.setOnClickListener(setDelListener(trash.getTag().toString()));

        LinearLayout linearContainer = linearContainer(context, dp16, dp8, tag);
        linearContainer.addView(linearInfo);
        linearContainer.addView(trash);
        linearContainer.setOnClickListener(editListener(linearContainer.getTag().toString()));

        linearLayout.addView(linearContainer);
    }

    // borrar despues
    private void generateAutoItems(View view, String itemsType) {
        int dp8 = dpToPx(8);
        int dp16 = dp8 * 2;
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        Context context = getContext();

        for (int i = 1; i <= Math.random() * 5 + 1; i++) {
            String tag = itemsType + "_" + i;

            TextView textID = textView(context, "id", String.valueOf(i));
            TextView textName = textView(context, "tx", "{itemName} [PRUEBA{" + tag + "}]");

            LinearLayout linearInfo = linearInfo(context);
            linearInfo.addView(textID);
            linearInfo.addView(textName);

            ImageView trash = imageView(context, tag, R.drawable.ic_trash);
            trash.setOnClickListener(setDelListener(trash.getTag().toString()));

            LinearLayout linearContainer = linearContainer(context, dp16, dp8, tag);
            linearContainer.addView(linearInfo);
            linearContainer.addView(trash);
            linearContainer.setOnClickListener(editListener(linearContainer.getTag().toString()));

            linearLayout.addView(linearContainer);
        }
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            String currentTitle = Objects.requireNonNull(activity.getSupportActionBar()).getTitle().toString();
            activity.getSupportActionBar().setTitle(currentTitle + " " + title);
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private LinearLayout linearContainer(Context context, int paddingDP, int marginDP, String tag) {
        LinearLayout linearContainer = new LinearLayout(context);
        linearContainer.setId(View.generateViewId());
        linearContainer.setTag(tag);
        linearContainer.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(marginDP, marginDP, marginDP, marginDP);
        linearContainer.setLayoutParams(params);
        linearContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_box));
        linearContainer.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.Greeuttec));
        linearContainer.setPadding(paddingDP, paddingDP, paddingDP, paddingDP);

        return linearContainer;
    }

    private LinearLayout linearInfo(Context context) {
        LinearLayout linearInfo = new LinearLayout(context);
        linearInfo.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;
        linearInfo.setLayoutParams(params);

        return linearInfo;
    }

    private TextView textView(Context context, String type, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        if (type.toLowerCase().equals("id")) {
            int dp8 = dpToPx(8);
            int dp12 = (int) (dp8 * 1.5);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setTextSize(dp12);
            textView.setPadding(0, 0, dp8, 0);

        } else if (type.toLowerCase().equals("tx")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER_VERTICAL);
        }

        return textView;
    }

    private ImageView imageView(Context context, String tag, int resID) {
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        imageView.setTag("delete_" + tag);
        imageView.setImageResource(resID);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        return imageView;
    }

    private View.OnClickListener setDelListener(String tag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(QueryFragment.this.getContext());
                alert.setTitle("Eliminar");
                alert.setMessage("¿Estás seguro de que deseas eliminar {" + tag + "}?");
                alert.setPositiveButton(android.R.string.yes, (dialog, which) -> delItem(tag, getContext()));
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        };
    }

    private void delItem(String tag, Context context) {
        // encontrar contenedor padre
        Pattern pattern = Pattern.compile("(?<=_).*_[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        tag = matcher.group();

        // eliminar de la vista
        LinearLayout linearLayout = requireView().findViewById(R.id.linear_layout);
        View view = linearLayout.findViewWithTag(tag);
        linearLayout.removeView(view);

        delFromDB(tag, context);
    }

    private void delFromDB(String tag, Context context) {
        if (tag.contains("tema")) {
            delTema(tag, context);

        } else if (tag.contains("examen_diagnostico")) {
            delExamen(tag, context);

        } else if (tag.contains("contenido")) {
            delContenido(tag, context);
        }

        messageToast("Elemento eliminado");
    }

    private void delTema(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(id);
        crudTema.delete(tema);
        crudTema.close();
    }

    private void delExamen(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel examen = crudExamen.read(id);
        crudExamen.delete(examen);
        crudExamen.close();
    }

    private void delContenido(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel contenido = crudContenido.read(id);
        crudContenido.delete(contenido);
        crudContenido.close();
    }

    private void clearList() {
        LinearLayout linearLayout = requireView().findViewById(R.id.linear_layout);
        linearLayout.removeAllViews();
    }

    private void messageToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener editListener(String tag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putChar("action", 'e');
                bundle.putString("table", titleToTable(itemsType));

                Pattern pattern = Pattern.compile("_[0-9]+");
                Matcher matcher = pattern.matcher(tag);
                matcher.find();
                bundle.putInt("id", Integer.parseInt(matcher.group().substring(1)));

                bundle.putString("itemTag", tag);
                Navigation.findNavController(view).navigate(R.id.action_nav_query_to_nav_item, bundle);
            }
        };
    }

    private View.OnClickListener addListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putChar("action", 'c');
                bundle.putString("table", titleToTable(itemsType));
                bundle.putInt("id", 0);

                Navigation.findNavController(view).navigate(R.id.action_nav_query_to_nav_item, bundle);
            }
        };
    }
}