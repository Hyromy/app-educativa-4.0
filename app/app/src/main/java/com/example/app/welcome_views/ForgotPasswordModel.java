package com.example.app.welcome_views;

import android.content.Context;
import android.widget.Toast;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;
import com.example.app.exceptions.UserRegistrationException;
import com.example.app.utils.Encryptor;

public class ForgotPasswordModel {
    protected void inputsNotEmtpy(String matricula, String newPass, String confirmPass) throws IllegalArgumentException {
        if (matricula.isEmpty() && newPass.isEmpty() && confirmPass.isEmpty()) {
            throw new IllegalArgumentException("Completa todos los campos");
        }

        if (matricula.isEmpty()) {
            throw new IllegalArgumentException("Ingresa tu matrícula");
        }

        if (newPass.isEmpty()) {
            throw new IllegalArgumentException("Ingresa tu nueva contraseña");
        }

        if (confirmPass.isEmpty()) {
            throw new IllegalArgumentException("Confirma tu nueva contraseña");
        }
    }

    protected UsuarioModel findUser(Usuario crud, String matricula) throws IllegalArgumentException {
        int id = crud.getIdBy(UsuarioModel.matricula, matricula);
        UsuarioModel user = crud.read(id);

        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        return user;
    }

    protected void passwordAreSame(String newPass, String confirmPass) throws IllegalArgumentException {
        if (!newPass.equals(confirmPass)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
    }

    protected void validate(String matricula, String newPass) throws UserRegistrationException {
        SingUpModel singUpModel = new SingUpModel();

        singUpModel.valideMatricula(matricula);
        singUpModel.validatePassword(newPass);
    }

    protected void resetPass(UsuarioModel usuario, String newPassword, Usuario crud, Context context) {
        Encryptor encryptor = new Encryptor();
        newPassword = encryptor.toHash(newPassword, usuario.matriculaValue, usuario.idValue);

        usuario.contrasenaValue = newPassword;
        crud.update(usuario);

        Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
    }
}