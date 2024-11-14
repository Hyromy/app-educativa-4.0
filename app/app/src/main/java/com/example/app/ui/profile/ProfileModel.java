package com.example.app.ui.profile;

import com.example.app.exceptions.UserRegistrationException;
import com.example.app.welcome_views.SingUpModel;

public class ProfileModel {
    public void validateNames(String name, String lastName, String mothersName) throws UserRegistrationException {
        SingUpModel singUpModel = new SingUpModel();

        singUpModel.validateName(name);
        singUpModel.validateName(lastName);
        singUpModel.validateName(mothersName);
    }

    public void validatePasswords(String newPassword, String confirmPassword) throws UserRegistrationException {
        if (!newPassword.equals(confirmPassword)) {
            throw new UserRegistrationException("Las contraseñas no coinciden");
        }

        SingUpModel singUpModel = new SingUpModel();

        singUpModel.validatePassword(newPassword);
        singUpModel.validatePassword(confirmPassword);
    }
}