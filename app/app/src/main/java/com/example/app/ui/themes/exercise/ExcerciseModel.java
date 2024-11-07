package com.example.app.ui.themes.exercise;

import android.content.Context;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.models.RespuestaExamenModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.PreguntaActividad;
import com.example.app.db.utils.crud.PreguntaExamen;
import com.example.app.db.utils.crud.RespuestaActividad;
import com.example.app.db.utils.crud.RespuestaExamen;

import java.util.ArrayList;
import java.util.Collections;

public class ExcerciseModel {
    private Context context;
    private String dataTag;
    private int length;

    public ExcerciseModel(Context context, String dataTag) {
        this.context = context;
        this.dataTag = dataTag;

        this.length = dataTag.length();
    }

    public ExamenDiagnosticoModel getExamenDiagnostico() {
        int id = Integer.parseInt(dataTag.substring(2, length));
        ExamenDiagnostico crud = new ExamenDiagnostico(context);
        crud.open();
        ExamenDiagnosticoModel examen = crud.read(id);
        crud.close();

        return examen;
    }

    public ContenidoModel getContenido() {
        int id = Integer.parseInt(dataTag.substring(2, length));
        Contenido crud = new Contenido(context);
        crud.open();
        ContenidoModel contenido = crud.read(id);
        crud.close();

        return contenido;
    }

    public PreguntaExamenModel[] getPreguntasFromExamen(ExamenDiagnosticoModel examen) {
        int nPreguntas = examen.nPreguntasValue;
        ArrayList<PreguntaExamenModel> preguntas = new ArrayList<>();

        PreguntaExamen crud = new PreguntaExamen(context);
        crud.open();
        PreguntaExamenModel[] totalPreguntas = crud.readAll();
        crud.close();

        if (totalPreguntas.length < nPreguntas) {
            return null;
        }

        for (PreguntaExamenModel pregunta : totalPreguntas) {
            if (pregunta.idExamenValue == examen.idValue) {
                preguntas.add(pregunta);
            }
        }
        Collections.shuffle(preguntas);

        PreguntaExamenModel[] output = new PreguntaExamenModel[nPreguntas];
        for (int i = 0; i < nPreguntas; i++) {
            output[i] = preguntas.get(i);
        }

        return output;
    }

    public RespuestaExamenModel[] getRespuestasFromPregunta(PreguntaExamenModel pregunta) {
        ArrayList<RespuestaExamenModel> respuestas = new ArrayList<>();

        RespuestaExamen crud = new RespuestaExamen(context);
        crud.open();
        RespuestaExamenModel[] totalRespuestas = crud.readAll();
        crud.close();

        for (RespuestaExamenModel respuesta : totalRespuestas) {
            if (respuesta.idPreguntaExamenValue == pregunta.idValue) {
                respuestas.add(respuesta);
            }
        }
        Collections.shuffle(respuestas);
        return respuestas.toArray(new RespuestaExamenModel[0]);
    }

    public int getMaxScoreFromAnswers(RespuestaExamenModel[] respuestas) {
        int maxScore = 0;
        for (RespuestaExamenModel respuesta : respuestas) {
            if (respuesta.puntajeValue > maxScore) {
                maxScore = respuesta.puntajeValue;
            }
        }
        return maxScore;
    }

    public PreguntaActividadModel[] getPreguntasFromActividad(ContenidoModel contenido) {
        int nPreguntas = contenido.nPreguntasValue;
        ArrayList<PreguntaActividadModel> preguntas = new ArrayList<>();

        PreguntaActividad crud = new PreguntaActividad(context);
        crud.open();
        PreguntaActividadModel[] totalPreguntas = crud.readAll();
        crud.close();

        if (totalPreguntas.length < nPreguntas) {
            return null;
        }

        for (PreguntaActividadModel pregunta : totalPreguntas) {
            if (pregunta.idContenidoValue == contenido.idValue) {
                preguntas.add(pregunta);
            }
        }
        Collections.shuffle(preguntas);

        PreguntaActividadModel[] output = new PreguntaActividadModel[nPreguntas];
        for (int i = 0; i < nPreguntas; i++) {
            output[i] = preguntas.get(i);
        }

        return output;
    }

    public RespuestaActividadModel[] getRespuestasFromPregunta(PreguntaActividadModel pregunta) {
        ArrayList<RespuestaActividadModel> respuestas = new ArrayList<>();

        RespuestaActividad crud = new RespuestaActividad(context);
        crud.open();
        RespuestaActividadModel[] totalRespuestas = crud.readAll();
        crud.close();

        for (RespuestaActividadModel respuesta : totalRespuestas) {
            if (respuesta.idPreguntaActividadValue == pregunta.idValue) {
                respuestas.add(respuesta);
            }
        }
        Collections.shuffle(respuestas);
        return respuestas.toArray(new RespuestaActividadModel[0]);
    }

    public int getMaxScoreFromAnswers(RespuestaActividadModel[] respuestas) {
        for (RespuestaActividadModel respuesta : respuestas) {
            if (respuesta.esCorrectoValue) {
                return 1;
            }
        }
        return 0;
    }
}