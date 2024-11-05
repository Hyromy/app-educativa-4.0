package com.example.app.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.app.R;

public class AdminFragment extends Fragment {
    private LinearLayout Temas;
    private LinearLayout Examenes;
    private LinearLayout Contenidos;
    private LinearLayout Preguntas;
    private LinearLayout Respuestas;
    private LinearLayout Apoyos;
    private LinearLayout Recursos;

    public static AdminFragment newInstance() {
        return new AdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        setButtons(view);
        setListeners();
    }

    private void setButtons(View view) {
        Temas = view.findViewById(R.id.gst_temas);
        Examenes = view.findViewById(R.id.gst_examenes);
        Contenidos = view.findViewById(R.id.gst_contenidos);
        Preguntas = view.findViewById(R.id.gst_preguntas);
        Respuestas = view.findViewById(R.id.gst_respuestas);
        Apoyos = view.findViewById(R.id.gst_apoyos);
        Recursos = view.findViewById(R.id.gst_recursos);
    }

    private void setListeners() {
        Temas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "temas");
            }
        });

        Examenes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "examenes");
            }
        });

        Contenidos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "contenidos");
            }
        });

        Preguntas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "preguntas");
            }
        });

        Respuestas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "respuestas");
            }
        });

        Apoyos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "apoyos");
            }
        });

        Recursos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "recursos");
            }
        });
    }

    private void toQuery(View view, String queryName) {
        Bundle bundle = new Bundle();
        bundle.putString("queryName", queryName);

        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_nav_admin_to_nav_query, bundle);
    }
}