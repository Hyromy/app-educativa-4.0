package com.example.app.ui.admin.query;

public class QueryModel {
    public String titleToTable(String s) {
        String x = null;

        if (s.equals("temas")) {
            x = "tema";

        } else if (s.equals("examenes")) {
            x = "examen_diagnostico";

        } else if (s.equals("contenidos")) {
            x = "contenido";

        } else if (s.equals("preguntas")) {
            x = "pregunta_";

        } else if (s.equals("respuestas")) {
            x = "respuesta_";

        } else if (s.equals("apoyos")) {
            x = "apoyo";

        } else if (s.equals("recursos")) {
            x = "recurso";
        }

        return x;
    }
}