package com.example.app.preload_data.info;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;
import com.example.app.utils.Encryptor;

import android.content.Context;

public class Usuarios {
    private Context context;
    private Usuario crud;
    private Encryptor encryptor;
    private UsuarioModel[] users;

    public Usuarios(Context context) {
        this.context = context;
        this.crud = new Usuario(this.context);
        this.encryptor = new Encryptor();

        run();
    }

    public void run() {
        System.out.println("Creando usuarios...");

        crud.open();
        int id = crud.nextId();

        UsuarioModel admin1 = new UsuarioModel(
                id,
                "1111",
                encryptor.toHash("30931368.Hfj", "1111", id),
                "Admin",
                "admin",
                "admin",
                true
        );

        UsuarioModel user1 = new UsuarioModel(
                id + 1,
                "2523260021",
                encryptor.toHash("30931368.Hfj", "2523260021", id + 1),
                "Joel",
                "González",
                "Cruz",
                false
        );

        users = new UsuarioModel[] {admin1, user1};
        createUsers(users);

        crud.close();
        System.out.println("Usuarios creados...");
    }

    private void createUsers(UsuarioModel[] admins) {
        for (UsuarioModel admin : admins) {
            System.out.println("Insertando usuario: " + admin.getData());
            try {
                crud.insert(admin);
            } catch (Exception e) {
                System.out.println("Error al insertar usuario: " + e.getMessage());
            }
        }
    }
}