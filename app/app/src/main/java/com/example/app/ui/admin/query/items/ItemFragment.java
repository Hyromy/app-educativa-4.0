package com.example.app.ui.admin.query.items;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.utils.drawer.ItemsDrawer;

public class ItemFragment extends Fragment {
    private String table = null;
    private int id;
    private LinearLayout layout;
    private Button btnSave;

    private ItemsDrawer drawer = new ItemsDrawer();

    private ItemModel model = new ItemModel();

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
        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(saveItem);

        Bundle bundle = getArguments();
        if (bundle != null) {
            table = bundle.getString("table");
            loadFormOf(table, getContext());

            id = bundle.getInt("id");
            if (id > 0) {
                extractData(table);
                setToolbarTitle("Editar registro");
            } else {
                setToolbarTitle("Crear registro");
            }
        }
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            activity.getSupportActionBar().setTitle(title);
        }
    }

    private void loadFormOf(String table, Context context) {
        if (table.equals("tema")) {
            drawer.loadTema(context, layout);

        } else if (table.equals("examen_diagnostico")) {
            drawer.loadExamen(context, layout);

        } else if (table.equals("contenido")) {
            drawer.loadContenido(context, layout);

        } else if (table.equals("pregunta_")) {
            // aqui
            drawer.loadPregunta(context, layout);

        }
    }

    private void extractData(String table) {
        Context context = getContext();
        if (table.equals("tema")) {
            drawer.extractTema(context, layout, id);

        } else if (table.equals("examen_diagnostico")) {
            drawer.extractExamenDiagnostico(context, layout, id);

        } else if (table.equals("contenido")) {
            drawer.extractContenido(context, layout, id);

        } else if (table.equals("pregunta_")) {
            drawer.extractPreguntaExamen(context, layout, id);

        }
    }

    private View.OnClickListener saveItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getContext();
            if (table.equals("tema")) {
                if (id <= 0) {
                    model.insertTema(context, layout);
                } else {
                    model.updateTema(context, layout, id);
                }
            } else if (table.equals("examen_diagnostico")) {
                if (id <= 0) {
                    model.insertExamenDiagnostico(context, layout);
                } else {
                    model.updateExamenDiagnostico(context, layout, id);
                }
            } else if (table.equals("contenido")) {
                if (id <= 0) {
                    model.insertContenido(context, layout);
                } else {
                    model.updateContenido(context, layout, id);
                }
            } else if (table.equals("pregunta_examen")) {
                notImplemented(context);
                if (id <= 0) {
                    // insert
                } else {
                    // update
                }
            } else if (table.equals("pregunta_actividad")) {
                notImplemented(context);
                if (id <= 0) {
                    // insert
                } else {
                    // update
                }
            }
        }
    };

    // borrar despues
    private void notImplemented(Context context) {
        Toast.makeText(context, "Aún no implementado", Toast.LENGTH_SHORT).show();
    }
}