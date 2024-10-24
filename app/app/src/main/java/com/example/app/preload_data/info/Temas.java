package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Tema;

public class Temas {
    private Context context;
    private Tema crud;
    private TemaModel[] temas;

    public Temas(Context context) {
        this.context = context;
        this.crud = new Tema(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando temas...");

        crud.open();
        int id = crud.nextId();
        TemaModel tema1 = new TemaModel(
                id,
                "Pensamiento abstracto",
                "Desarrolla actividades cognitivas: comprende conceptos complejos, analiza patrones y relaciones, resuelve problemas que van mas allá de lo concreto o lo evidente."
        );

        id = crud.nextId();
        TemaModel tema2 = new TemaModel(
                id,
                "Resolución de problemas",
                "[Incompleto]"
        );

        temas = new TemaModel[] {tema1, tema2};
        createTemas(temas);

        crud.close();
        System.out.println("Temas creados...");
    }

    private void createTemas(TemaModel[] temas) {
        for (TemaModel tema : temas) {
            System.out.println("Insertando tema: " + tema.getData());
            try {
                crud.insert(tema);
            } catch (Exception e) {
                System.out.println("Error al insertar tema: " + e.getMessage());
            }
        }
    }
}