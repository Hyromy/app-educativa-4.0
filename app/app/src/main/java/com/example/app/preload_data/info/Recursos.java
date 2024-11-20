package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.RecursoModel;
import com.example.app.db.utils.crud.Recurso;

public class Recursos {
    private Context context;
    private Recurso crudRecurso;
    private RecursoModel[] recursos;

    public Recursos(Context context) {
        this.context = context;
        this.crudRecurso = new Recurso(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando recursos...");

        String p = "pd_pa_p";
        String j = "jpg";
        String i = "img";

        crudRecurso.open();
        recursos = new RecursoModel[] {
                new RecursoModel("cat", j, i),
                new RecursoModel(p + "1", j, i),
                new RecursoModel(p + "2", j, i),
                new RecursoModel(p + "3", j, i),
                new RecursoModel(p + "4", j, i),
                new RecursoModel(p + "5", j, i),
                new RecursoModel(p + "6", j, i),
                new RecursoModel(p + "7", j, i),
                new RecursoModel(p + "16", j, i),
                new RecursoModel(p + "17", j, i),
                new RecursoModel(p + "18", j, i),
                new RecursoModel(p + "19", j, i),
                new RecursoModel(p + "20", j, i),
                new RecursoModel(p + "21", j, i),
                new RecursoModel(p + "22", j, i),
                new RecursoModel(p + "31", j, i),
                new RecursoModel(p + "32", j, i),
                new RecursoModel(p + "33", j, i),
                new RecursoModel(p + "34", j, i),
                new RecursoModel(p + "35", j, i),
                new RecursoModel(p + "36", j, i),

                new RecursoModel(p(1,1), j, i),
                new RecursoModel(p(1,2), j, i),
                new RecursoModel(p(1,3), j, i),
                new RecursoModel(p(1,4), j, i),
                new RecursoModel(p(2,1), j, i),
                new RecursoModel(p(2,2), j, i),
                new RecursoModel(p(2,3), j, i),
                new RecursoModel(p(2,4), j, i),
                new RecursoModel(p(3,1), j, i),
                new RecursoModel(p(3,2), j, i),
                new RecursoModel(p(3,3), j, i),
                new RecursoModel(p(3,4), j, i),
                new RecursoModel(p(4,1), j, i),
                new RecursoModel(p(4,2), j, i),
                new RecursoModel(p(4,3), j, i),
                new RecursoModel(p(4,4), j, i),
                new RecursoModel(p(5,1), j, i),
                new RecursoModel(p(5,2), j, i),
                new RecursoModel(p(5,3), j, i),
                new RecursoModel(p(5,4), j, i),
                new RecursoModel(p(6,1), j, i),
                new RecursoModel(p(6,2), j, i),
                new RecursoModel(p(6,3), j, i),
                new RecursoModel(p(6,4), j, i),
                new RecursoModel(p(7,1), j, i),
                new RecursoModel(p(7,2), j, i),
                new RecursoModel(p(7,3), j, i),
                new RecursoModel(p(7,4), j, i),
                new RecursoModel(p(16,1), j, i),
                new RecursoModel(p(16,2), j, i),
                new RecursoModel(p(16,3), j, i),
                new RecursoModel(p(16,4), j, i),
                new RecursoModel(p(17,1), j, i),
                new RecursoModel(p(17,2), j, i),
                new RecursoModel(p(17,3), j, i),
                new RecursoModel(p(17,4), j, i),
                new RecursoModel(p(18,1), j, i),
                new RecursoModel(p(18,2), j, i),
                new RecursoModel(p(18,3), j, i),
                new RecursoModel(p(18,4), j, i),
                new RecursoModel(p(19,1), j, i),
                new RecursoModel(p(19,2), j, i),
                new RecursoModel(p(19,3), j, i),
                new RecursoModel(p(19,4), j, i),
                new RecursoModel(p(20,1), j, i),
                new RecursoModel(p(20,2), j, i),
                new RecursoModel(p(20,3), j, i),
                new RecursoModel(p(20,4), j, i),
                new RecursoModel(p(21,1), j, i),
                new RecursoModel(p(21,2), j, i),
                new RecursoModel(p(21,3), j, i),
                new RecursoModel(p(21,4), j, i),
                new RecursoModel(p(22,1), j, i),
                new RecursoModel(p(22,2), j, i),
                new RecursoModel(p(22,3), j, i),
                new RecursoModel(p(22,4), j, i),
                new RecursoModel(p(31,1), j, i),
                new RecursoModel(p(31,2), j, i),
                new RecursoModel(p(31,3), j, i),
                new RecursoModel(p(31,4), j, i),
                new RecursoModel(p(32,1), j, i),
                new RecursoModel(p(32,2), j, i),
                new RecursoModel(p(32,3), j, i),
                new RecursoModel(p(32,4), j, i),
                new RecursoModel(p(33,1), j, i),
                new RecursoModel(p(33,2), j, i),
                new RecursoModel(p(33,3), j, i),
                new RecursoModel(p(33,4), j, i),
                new RecursoModel(p(34,1), j, i),
                new RecursoModel(p(34,2), j, i),
                new RecursoModel(p(34,3), j, i),
                new RecursoModel(p(34,4), j, i),
                new RecursoModel(p(35,1), j, i),
                new RecursoModel(p(35,2), j, i),
                new RecursoModel(p(35,3), j, i),
                new RecursoModel(p(35,4), j, i),
                new RecursoModel(p(36,1), j, i),
                new RecursoModel(p(36,2), j, i),
                new RecursoModel(p(36,3), j, i),
                new RecursoModel(p(36,4), j, i),
        };

        createRecursos(recursos);

        crudRecurso.close();
        System.out.println("Recursos creados.");
    }

    private String p(int p, int r) {
        return "pd_pa_p" + p + "_r" + r;
    }

    private void createRecursos(RecursoModel[] recursos) {
        RecursoModel[] recursosExistentes = crudRecurso.readAll();
        if (recursosExistentes.length > 0) {
            return;
        }

        for (RecursoModel recurso : recursos) {
            System.out.println("Insertando recurso: " + recurso.getData());
            try {
                crudRecurso.insert(recurso);
            } catch (Exception e) {
                System.out.println("Error al insertar recurso: " + e.getMessage());
            }
        }
    }
}