package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.Tema;

public class Contenidos {
    private Context context;
    private Contenido crud;
    private Tema crudTema;
    private ContenidoModel[] contenidos;

    public Contenidos(Context context) {
        this.context = context;
        this.crud = new Contenido(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando contenidos...");
        crud.open();

        String titulo = "Pensamiento abstracto";
        int themeID = getThemeIDByTtitle(titulo);

        ContenidoModel contenido1 = new ContenidoModel(
                themeID,
                "Abstracción básica",
                "Visión general y superficial de los conceptos y datos",
                0,
                10
        );

        ContenidoModel contenido2 = new ContenidoModel(
                themeID,
                "Abstracción simple",
                "Aspectos fundamentales y esenciales de entidades",
                1,
                10
        );

        ContenidoModel contenido3 = new ContenidoModel(
                themeID,
                "Abstracción desarrollada",
                "Compresión profunda y detallada de objetos",
                2,
                10
        );

        ContenidoModel contenido4 = new ContenidoModel(
                themeID,
                "Abstracción destacada",
                "Visión exahustiva y completa de los elementos",
                3,
                10
        );

        contenidos = new ContenidoModel[] {contenido1, contenido2, contenido3, contenido4};
        createContenidos(contenidos);

        crud.close();
        System.out.println("Contenidos creados...");
    }

    private int getThemeIDByTtitle(String titulo) {
        crudTema = new Tema(this.context);
        crudTema.open();
        int id = crudTema.getIdBy(TemaModel.titulo, titulo);
        crudTema.close();
        return id;
    }

    private void createContenidos(ContenidoModel[] contenidos) {
        for (ContenidoModel contenido : contenidos) {
            System.out.println("Insertando contenido: " + contenido.getData());
            try {
                crud.insert(contenido);
            } catch (Exception e) {
                System.out.println("Error al insertar contenido: " + e.getMessage());
            }
        }
    }
}