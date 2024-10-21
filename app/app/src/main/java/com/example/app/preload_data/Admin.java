package com.example.app.preload_data;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;
import com.example.app.utils.Encryptor;

import android.content.Context;

public class Admin {
    private Context context;
    private Usuario crud;
    private Encryptor encryptor;
    private UsuarioModel[] admins;

    public Admin(Context context, boolean autoRun) {
        this.context = context;
        this.crud = new Usuario(this.context);
        this.encryptor = new Encryptor();

        if (autoRun) {
            run();
        }
    }

    public void run() {
        System.out.println("Creando administradores...");

        crud.open();
        int id = crud.nextId();

        System.out.println("ID: " + id);

        UsuarioModel admin1 = new UsuarioModel(
                id,
                "1111",
                encryptor.toHash("30931368.Hfj", "1111", id),
                "Admin",
                "admin",
                "admin",
                true
        );

        admins = new UsuarioModel[] {admin1};
        createAdmins(admins);

        crud.close();
        System.out.println("Administradores creados...");
    }

    private void createAdmins(UsuarioModel[] admins) {
        for (UsuarioModel admin : admins) {
            System.out.println("Insertando administrador: " + admin.getData());
            try {
                crud.insert(admin);
            } catch (Exception e) {
                System.out.println("Error al insertar administrador: " + e.getMessage());
            }
        }
    }
}