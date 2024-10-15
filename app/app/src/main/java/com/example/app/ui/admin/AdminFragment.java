package com.example.app.ui.admin;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.example.app.R;

public class AdminFragment extends Fragment {
    private AdminViewModel mViewModel;
    private Button btnTemas;
    private Button btnExamenes;
    private Button btnPreguntas;
    private Button btnActividades;
    private Button btnContenidos;

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
        mViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        View view = getView();
        setButtons(view);
        setListeners();
    }

    private void setButtons(View view) {
        btnTemas = view.findViewById(R.id.btn_temas);
        btnExamenes = view.findViewById(R.id.btn_examenes);
        btnPreguntas = view.findViewById(R.id.btn_preguntas);
        btnActividades = view.findViewById(R.id.btn_actividades);
        btnContenidos = view.findViewById(R.id.btn_contenidos);
    }

    private void setListeners() {
        btnTemas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "temas");
            }
        });

        btnExamenes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "examenes");
            }
        });

        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "preguntas");
            }
        });

        btnActividades.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "actividades");
            }
        });

        btnContenidos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toQuery(view, "contenidos");
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