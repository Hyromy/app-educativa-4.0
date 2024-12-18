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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.db.models.CompletaContenidoModel;
import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.models.RespuestaExamenModel;
import com.example.app.db.models.ResultadoExamenModel;
import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.CompletaContenido;
import com.example.app.db.utils.crud.ResultadoExamen;
import com.example.app.utils.Teacher;
import com.example.app.utils.drawer.ExcerciseDrawer;

import java.util.Objects;

public class ExerciseFragment extends Fragment {
    private UsuarioModel usuario;

    private String dataTag = null;
    private ExcerciseModel model;
    private ExcerciseDrawer drawer;
    private LinearLayout layout;
    private LinearLayout headerLayout;
    private LinearLayout screenLayout;

    private int iQuestion = 0;
    private int score = 0;
    private int maxScore = 0;
    private int levels = 0;
    private int questions = 0;

    private PreguntaExamenModel[] preguntasExamen;
    private PreguntaActividadModel[] preguntasActividad;

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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            dataTag = bundle.getString("testTag");
            usuario = (UsuarioModel) bundle.getSerializable("usuario");

            layout = view.findViewById(R.id.layout);
            headerLayout = view.findViewById(R.id.header_layout);
            screenLayout = view.findViewById(R.id.screen_layout);

            Context context = getContext();
            drawer = new ExcerciseDrawer(context);
            load(context);
        }
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(title);
        }
    }

    private void setNextButton(boolean isTest) {
        if (isTest) {
            screenLayout.findViewById(R.id.btn_next).setOnClickListener(v -> {
                if (isSelectedAnswer()) {
                    nextQuestion(true);
                } else {
                    notSelectedAnswer();
                }
            });
        } else {
            screenLayout.findViewById(R.id.btn_next).setOnClickListener(v -> {
                if (isSelectedAnswer()) {
                    nextQuestion(false);
                } else {
                    notSelectedAnswer();
                }
            });
        }
    }

    private void notSelectedAnswer() {
        Toast.makeText(getContext(), "Selecciona una respuesta", Toast.LENGTH_SHORT).show();
    }

    private boolean isSelectedAnswer() {
        RadioGroup rg = screenLayout.findViewWithTag("rg_answers");
        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton rb = rg.findViewById(selectedId);
            score += Integer.parseInt(rb.getTag().toString());

            return true;
        } else {
            return false;
        }
    }

    private void nextQuestion(boolean isTest) {
        clearLayout();
        iQuestion++;
        setCurrentQuestionOnHeader(iQuestion + 1);

        if (isTest) {
            loadCurrentQuestion(preguntasExamen);
        } else {
            loadCurrentQuestion(preguntasActividad);
        }
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
            setNextButton(true);

            ExamenDiagnosticoModel examen = model.getExamenDiagnostico();
            setToolbarTitle(examen.tituloValue);

            try {
                preguntasExamen = model.getPreguntasFromExamen(examen);
                levels = examen.nivelMaximoValue;
                questions = examen.nPreguntasValue;

                loadExamen(examen, preguntasExamen);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al cargar examen: " + e.getMessage() + " => " + e.getCause());
                Toast.makeText(context, "Ocurrió un problema al cargar el exámen", Toast.LENGTH_SHORT).show();
                exit();
            }

        } else if (dataTag.startsWith("c")) {
            setNextButton(false);

            ContenidoModel contenido = model.getContenido();
            setToolbarTitle(contenido.tituloValue);

            try {
                preguntasActividad = model.getPreguntasFromActividad(contenido);
                questions = contenido.nPreguntasValue;

                loadActividad(contenido, preguntasActividad);
            } catch (Exception e) {
                Toast.makeText(context, "Ocurrió un problema al cargar la actividad", Toast.LENGTH_SHORT).show();
                exit();
            }
        }
    }

    private void loadExamen(ExamenDiagnosticoModel examen, PreguntaExamenModel[] preguntas) {
        setCurrentQuestionOnHeader(1);
        setMaxQuestionsOnHeader(examen.nPreguntasValue);
        loadCurrentQuestion(preguntas);
    }

    private void loadActividad(ContenidoModel contenido, PreguntaActividadModel[] preguntas) {
        setCurrentQuestionOnHeader(1);
        setMaxQuestionsOnHeader(contenido.nPreguntasValue);
        loadCurrentQuestion(preguntas);
    }

    private void loadCurrentQuestion(PreguntaExamenModel[] preguntas) {
        if (iQuestion < preguntas.length) {
            loadQuestion(preguntas[iQuestion]);
            RespuestaExamenModel[] respuestas = model.getRespuestasFromPregunta(preguntas[iQuestion]);
            maxScore += model.getMaxScoreFromAnswers(respuestas);

            RadioGroup rg = drawer.setRadioGroup(drawer.setRadioButtons(respuestas), "answers");
            layout.addView(rg);
        } else {
            endQuestions(true);
        }
    }

    private void loadCurrentQuestion(PreguntaActividadModel[] preguntas) {
        if (iQuestion < preguntas.length) {
            loadQuestion(preguntas[iQuestion]);
            RespuestaActividadModel[] respuestas = model.getRespuestasFromPregunta(preguntas[iQuestion]);
            maxScore += model.getMaxScoreFromAnswers(respuestas);

            RadioGroup rg = drawer.setRadioGroup(drawer.setRadioButtons(respuestas), "answers");
            layout.addView(rg);
        } else {
            endQuestions(false);
        }
    }

    private void endQuestions(boolean isTest) {
        boolean isAdmin = usuario.tipoAdministradorValue;
        if (isTest) {
            if (isAdmin) {
                showResults();
            } else {
                setLogOfcompleteTest();
            }
        } else {
            if (!isAdmin) {
                if (!isClearActivity()) {
                    String msg = "Sigue practicando, tuviste " + (maxScore - score) + " errores";
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Resultados: (" + score + "/" + maxScore + ")", Toast.LENGTH_SHORT).show();
            }
        }

        exit();
    }

    private void showResults() {
        Teacher teacher = new Teacher();
        int level = teacher.knowledgeLevel(
                levels,
                questions,
                maxScore / questions,
                score
        );
        String message = "Resultados: (" + score + "/" + maxScore + ") Nv." + level;
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setLogOfcompleteTest() {
        int idUsuario = usuario.idValue;
        int idExamen = preguntasExamen[0].idExamenValue;
        Context context = getContext();

        ResultadoExamen crud = new ResultadoExamen(context);
        crud.open();

        CompletaContenido crud2 = new CompletaContenido(context);
        crud2.open();
        if (!crud.existLogFrom(idUsuario, idExamen)) {
            Teacher teacher = new Teacher();
            int level = teacher.knowledgeLevel(
                    levels,
                    questions,
                    maxScore / questions,
                    score
            );

            ResultadoExamenModel resultado = new ResultadoExamenModel(
                    idUsuario,
                    idExamen,
                    score,
                    level
            );
            crud.insert(resultado);

            int[] contentsId = crud2.getLevelsContentFromTestAnswer(preguntasExamen[0]);
            for (int i = 0; i < level; i++) {
                crud2.insert(new CompletaContenidoModel(
                        idUsuario,
                        contentsId[i]
                ));
            }

            String msg = "Exámen completado, resultado: (" + level + "/" + levels + ")";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "ya hiciste este examen", Toast.LENGTH_SHORT).show();
        }
        crud.close();
        crud2.close();
    }

    private boolean isClearActivity() {
        boolean isClear = false;
        if (score == maxScore) {
            isClear = true;

            int idUsuario = usuario.idValue;
            int idContenido = preguntasActividad[0].idContenidoValue;

            CompletaContenido crud = new CompletaContenido(getContext());
            crud.open();
            if (!crud.existLogFrom(idUsuario, idContenido)) {
                CompletaContenidoModel log = new CompletaContenidoModel(
                        idUsuario,
                        idContenido
                );

                crud.insert(log);

                String msg = "Actividad completada";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "ya hiciste esta actividad", Toast.LENGTH_SHORT).show();
            }
            crud.close();
        }

        return isClear;
    }

    private void exit() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigateUp();
    }

    private void loadQuestion(PreguntaExamenModel pregunta) {
        TextView tvs[] = drawer.setTextViews(pregunta.textoValue);
        for (int i = 0; i < tvs.length; i++) {
            layout.addView(tvs[i]);

            if (pregunta.idRecursoValue > 0) {
                layout.addView(drawer.setImageView(pregunta.idRecursoValue));
            }
        }
    }

    private void loadQuestion(PreguntaActividadModel pregunta) {
        TextView tvs[] = drawer.setTextViews(pregunta.textoValue);
        for (TextView tv : tvs) {
            layout.addView(tv);

            if (pregunta.idRecursoValue > 0) {
                layout.addView(drawer.setImageView(pregunta.idRecursoValue));
            }
        }
    }
}