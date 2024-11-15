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

import com.example.app.R;
import com.example.app.utils.drawer.ItemsDrawer;

public class ItemFragment extends Fragment {
    public static String table = null;
    private int id;
    private String itemTag;
    private LinearLayout layout;
    private Button btnSave;

    private ItemsDrawer drawer = new ItemsDrawer();

    private ItemModel model = new ItemModel();

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
            itemTag = bundle.getString("itemTag");
            loadFormOf(table, getContext(), view);

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

    private void loadFormOf(String table, Context context, View view) {
        if (table.equals("tema")) {
            drawer.loadTema(context, layout);

        } else if (table.equals("examen_diagnostico")) {
            drawer.loadExamen(context, layout);

        } else if (table.equals("contenido")) {
            drawer.loadContenido(context, layout);

        } else if (table.equals("pregunta_")) {
            drawer.loadPregunta(context, layout, view.findViewById(R.id.btn_save));

        } else if (table.equals("respuesta_")) {
            drawer.loadRespuesta(context, layout, view.findViewById(R.id.btn_save));
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
            if (itemTag.contains("examen")) {
                drawer.extractPreguntaExamen(context, layout, id);

            } else if (itemTag.contains("actividad")) {
                drawer.extractPreguntaActividad(context, layout, id);
            }

        } else if (table.equals("respuesta_")) {
            if (itemTag.contains("examen")) {
                drawer.extractRespuestaExamen(context, layout, id);

            } else if(itemTag.contains("actividad")) {
                drawer.extractRespuestaActividad(context, layout, id);
            }
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
                if (id <= 0) {
                    model.insertPreguntaExamen(context, layout);
                } else {
                    model.updatePreguntaExamen(context, layout, id);
                }
            } else if (table.equals("pregunta_actividad")) {
                if (id <= 0) {
                    model.insertPreguntaActividad(context, layout);
                } else {
                    model.updatePreguntaActividad(context, layout, id);
                }
            } else if (table.equals("respuesta_examen")) {
                if (id <= 0) {
                    model.insertRespuestaExamen(context, layout);
                } else {
                    model.updateRespuestaExamen(context, layout, id);
                }
            } else if (table.equals("respuesta_actividad")) {
                if (id <= 0) {
                    model.insertRespuestaActividad(context, layout);
                } else {
                    model.updateRespuestaActividad(context, layout, id);
                }
            }
        }
    };
}