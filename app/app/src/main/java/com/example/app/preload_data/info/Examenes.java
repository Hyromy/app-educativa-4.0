package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.Tema;

public class Examenes {
    private Context context;
    private ExamenDiagnostico crud;
    private Tema crudTema;
    private ExamenDiagnosticoModel[] examenes;

    public Examenes(Context context) {
        this.context = context;
        this.crud = new ExamenDiagnostico(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando examenes...");

        crud.open();
        int id = crud.nextId();

        String titulo = "Pensamiento abstracto";
        int themeID = crud.getIdBy(ExamenDiagnosticoModel.idTema, String.valueOf(getThemeIDByTtitle(titulo)));
        if (themeID > 0) {
            return;
        }

        ExamenDiagnosticoModel examen1 = new ExamenDiagnosticoModel(
                id,
                getThemeIDByTtitle(titulo),
                titulo,
                30,
                3
        );

        examenes = new ExamenDiagnosticoModel[] {examen1};
        createExamenes(examenes);

        crud.close();
        System.out.println("Examenes creados...");
    }

    private int getThemeIDByTtitle(String titulo) {
        crudTema = new Tema(this.context);
        crudTema.open();
        int id = crudTema.getIdBy(TemaModel.titulo, titulo);
        crudTema.close();
        return id;
    }

    private void createExamenes(ExamenDiagnosticoModel[] examenes) {
        for (ExamenDiagnosticoModel examen : examenes) {
            System.out.println("Insertando examen: " + examen.getData());
            try {
                crud.insert(examen);
            } catch (Exception e) {
                System.out.println("Error al insertar examen: " + e.getMessage());
            }
        }
    }
}