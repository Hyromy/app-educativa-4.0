package com.example.app.ui.admin.query.items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

import java.util.Objects;

public class ItemFragment extends Fragment {
    private String table = null;
    private int id;
    private LinearLayout layout;

    private ItemViewModel mViewModel;

    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.linear_layout);

        Bundle bundle = getArguments();
        if (bundle != null) {
            table = bundle.getString("table");
            loadFormOf(table, getContext());

            id = bundle.getInt("id");
            if (id > 0) {
                extractData(table, id);
                setToolbarTitle("Editar registro");
            } else {
                setToolbarTitle("Crear registro");
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            activity.getSupportActionBar().setTitle(title);
        }
    }

    private void loadFormOf(String table, Context context) {
        if (table.equals("tema")) {
            loadTema();
        } else if (table.equals("examen_diagnostico")) {
            loadExamen(context);
        } else if (table.equals("pregunta_examen")) {
            loadPregunta();
        } else if (table.equals("pregunta_actividad")) {
            loadActividad();
        } else if (table.equals("contenido")) {
            loadContenido();
        }
    }

    private void loadTema() {
        Toast.makeText(getContext(), "Cargando tema", Toast.LENGTH_SHORT).show();
    }

    private void loadExamen(Context context) {
        TextView textView = setLabel(context, "Título");
        layout.addView(textView);
    }

    private void loadPregunta() {
        Toast.makeText(getContext(), "Cargando pregunta", Toast.LENGTH_SHORT).show();
    }

    private void loadActividad() {
        Toast.makeText(getContext(), "Cargando actividad", Toast.LENGTH_SHORT).show();
    }

    private void loadContenido() {
        Toast.makeText(getContext(), "Cargando contenido", Toast.LENGTH_SHORT).show();
    }

    private void extractData(String table, int id) {
        Toast.makeText(getContext(), "Extrayendo informacion", Toast.LENGTH_SHORT).show();

        if (table.equals("tema")) {

        } else if (table.equals("examen_diagnostico")) {

        } else if (table.equals("pregunta_examen")) {

        } else if (table.equals("pregunta_actividad")) {

        } else if (table.equals("contenido")) {

        }
    }

    private TextView setLabel(Context context, String text) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(params);
        tv.setText(text);

        return tv;
    }

    private EditText setEntry(Context context, char type, String id) {
        EditText et = new EditText(context);
        if (type == 't') {
            et.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (type == 'n') {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        return et;
    }
}