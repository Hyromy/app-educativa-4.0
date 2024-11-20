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

        crudRecurso.open();
        recursos = new RecursoModel[] {
                new RecursoModel("cat", "jpg", "img")
        };

        createRecursos(recursos);

        crudRecurso.close();
        System.out.println("Recursos creados.");
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