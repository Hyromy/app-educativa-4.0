package com.example.app.ui.debug.view_db;

import android.os.Bundle;

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

public class ViewDBFragment extends Fragment {
    private Button btnUsuario;
    private Button btnTema;
    private Button btnRecurso;

    private Button btnContenido;
    private Button btnExamenDiagnostico;

    private Button btnResultadoExamen;
    private Button btnCompletaContenido;
    private Button btnPreguntaExamen;
    private Button btnPreguntaActividad;
    private Button btnApoyo;

    private Button btnRespuestaExamen;
    private Button btnRespuestaActividad;
    private Button btnParrafo;

    private Button[] notImplemented;

    public static ViewDBFragment newInstance() {
        return new ViewDBFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        setButtons(view);
        setListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_db, container, false);
    }

    public void setButtons(View view) {
        btnUsuario = view.findViewById(R.id.btn_tb_usuario);
        btnTema = view.findViewById(R.id.btn_tb_tema);
        btnRecurso = view.findViewById(R.id.btn_tb_recurso);

        btnContenido = view.findViewById(R.id.btn_tb_contenido);
        btnExamenDiagnostico = view.findViewById(R.id.btn_tb_examen_diagnostico);

        btnResultadoExamen = view.findViewById(R.id.btn_tb_resultado_examen);
        btnCompletaContenido = view.findViewById(R.id.btn_tb_completa_contenido);
        btnPreguntaExamen = view.findViewById(R.id.btn_tb_pregunta_examen);
        btnPreguntaActividad = view.findViewById(R.id.btn_tb_pregunta_actividad);
        btnApoyo = view.findViewById(R.id.btn_tb_apoyo);

        btnRespuestaExamen = view.findViewById(R.id.btn_tb_respuesta_examen);
        btnRespuestaActividad = view.findViewById(R.id.btn_tb_respuesta_actividad);
        btnParrafo = view.findViewById(R.id.btn_tb_parrafo);

        notImplemented = new Button[] {btnRecurso, btnApoyo, btnParrafo};
    }

    public void setListeners() {
        btnUsuario.setOnClickListener(clickListener(btnUsuario.getTag().toString()));
        btnTema.setOnClickListener(clickListener(btnTema.getTag().toString()));
        btnRecurso.setOnClickListener(clickListener(btnRecurso.getTag().toString()));

        btnContenido.setOnClickListener(clickListener(btnContenido.getTag().toString()));
        btnExamenDiagnostico.setOnClickListener(clickListener(btnExamenDiagnostico.getTag().toString()));

        btnResultadoExamen.setOnClickListener(clickListener(btnResultadoExamen.getTag().toString()));
        btnCompletaContenido.setOnClickListener(clickListener(btnCompletaContenido.getTag().toString()));
        btnPreguntaExamen.setOnClickListener(clickListener(btnPreguntaExamen.getTag().toString()));
        btnPreguntaActividad.setOnClickListener(clickListener(btnPreguntaActividad.getTag().toString()));
        btnApoyo.setOnClickListener(clickListener(btnApoyo.getTag().toString()));

        btnRespuestaExamen.setOnClickListener(clickListener(btnRespuestaExamen.getTag().toString()));
        btnRespuestaActividad.setOnClickListener(clickListener(btnRespuestaActividad.getTag().toString()));
        btnParrafo.setOnClickListener(clickListener(btnParrafo.getTag().toString()));
    }

    public View.OnClickListener clickListener(String tag) {
        for (int i = 0; i < notImplemented.length; i++) {
            if (notImplemented[i].getTag().toString().equals(tag)) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "CRUD no implementado", Toast.LENGTH_SHORT).show();
                    }
                };
            }
        }

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("table", tag);

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_view_db_to_nav_view_table, bundle);
            }
        };
    }
}