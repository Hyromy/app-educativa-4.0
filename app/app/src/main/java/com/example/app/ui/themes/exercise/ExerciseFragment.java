package com.example.app.ui.themes.exercise;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.utils.crud.RespuestaExamen;
import com.example.app.utils.drawer.ExcerciseDrawer;

import java.util.Objects;

public class ExerciseFragment extends Fragment {
    private String dataTag = null;
    private ExcerciseModel model;
    private ExcerciseDrawer drawer;
    private LinearLayout layout;
    private LinearLayout headerLayout;
    private LinearLayout screenLayout;
    private int iQuestion = 0;
    private int maxScore = 0;

    private PreguntaExamenModel[] preguntas;

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            dataTag = bundle.getString("testTag");

            layout = view.findViewById(R.id.layout);
            headerLayout = view.findViewById(R.id.header_layout);
            screenLayout = view.findViewById(R.id.screen_layout);

            Context context = getContext();
            setNextButton();
            drawer = new ExcerciseDrawer(context);
            load(context);
        }
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(title);
        }
    }

    private void setNextButton() {
        screenLayout.findViewById(R.id.btn_next).setOnClickListener(v -> {
            clearLayout();
            iQuestion++;
            setCurrentQuestionOnHeader(iQuestion + 1);
            loadCurrentQuestion(preguntas);
        });
    }

    private void setMaxQuestionsOnHeader(int maxQuestions) {
        ((TextView) headerLayout.findViewById(R.id.max_question)).setText(String.valueOf(maxQuestions));
    }

    private void setCurrentQuestionOnHeader(int iQuestion) {
        ((TextView) headerLayout.findViewById(R.id.i_question)).setText(String.valueOf(iQuestion));
    }

    private void clearLayout() {
        layout.removeAllViews();
    }

    private void load(Context context) {
        model = new ExcerciseModel(context, dataTag);

        if (dataTag.startsWith("t")) {
            ExamenDiagnosticoModel examen = model.getExamenDiagnostico();
            setToolbarTitle(examen.tituloValue);

            preguntas = model.getPreguntasFromExamen(examen);
            loadExamen(examen, preguntas);

        } else if (dataTag.startsWith("c")) {
            ContenidoModel contenido = model.getContenido();
            setToolbarTitle(contenido.tituloValue);
        }
    }

    private void loadExamen(ExamenDiagnosticoModel examen, PreguntaExamenModel[] preguntas) {
        setCurrentQuestionOnHeader(1);
        setMaxQuestionsOnHeader(examen.nPreguntasValue);
        loadCurrentQuestion(preguntas);
    }

    private void loadCurrentQuestion(PreguntaExamenModel[] preguntas) {
        if (iQuestion < preguntas.length) {
            loadQuestion(preguntas[iQuestion]);
        } else {
            endQuestions();
        }
    }

    private void endQuestions() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigateUp();
    }

    private void loadQuestion(PreguntaExamenModel pregunta) {
        TextView tv = drawer.setTextView(pregunta.textoValue);
        layout.addView(tv);
    }
}