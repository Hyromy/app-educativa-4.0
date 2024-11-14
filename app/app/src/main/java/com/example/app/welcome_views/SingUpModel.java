package com.example.app.welcome_views;

import com.example.app.exceptions.UserRegistrationException;

import java.util.regex.Pattern;

public class SingUpModel {
    protected void valideMatricula(String matricula) throws UserRegistrationException {
        if (matricula.length() != 10) {
            throw new UserRegistrationException("La matrícula debe tener 10 caracteres");

        } else if (!matricula.matches("[0-9]+")) {
            throw new UserRegistrationException("La matrícula solo puede contener números");
        }
    }

    public void validateName(String name) throws UserRegistrationException {
        if (name.length() < 3) {
            throw new UserRegistrationException("Los nombres y apellidos deben tener 3 letras o más");

        } else if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚ ]+")) {
            throw new UserRegistrationException("Los nombres y apellidos solo puede contener letras");
        }
    }

    public void validatePassword(String password) throws UserRegistrationException {
        String pwd = "La contraseña ";
        String msg = pwd + "debe contener ";
        String emsg = pwd + "no puede contener ";
        char[] illegalChars = {'\\', '"','\'', ';', '#', '|', '=', '?', '&', '^', '`'};

        if (password.length() < 8) {
            throw new UserRegistrationException(msg + "8 caracteres o más");

        } else if (!password.matches(".*[0-9].*")) {
            throw new UserRegistrationException(msg + "algún número");

        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z]).+$")) {
            throw new UserRegistrationException(msg + "mayúscula y minúscula");

        } else {
            if (password.matches(".* .*")) {
                throw new UserRegistrationException(emsg + "espacios en blanco");

            } else if (password.matches(".*/{2,}.*")) {
                throw new UserRegistrationException(emsg + "dos diagonales //");

            } else if (password.matches(".*-{2,}.*")) {
                throw new UserRegistrationException(emsg + "dos guiones --");

            } else if (password.matches(".*\\*/.*|.*/\\*.*")) {
                throw new UserRegistrationException(emsg + "los caracteres: /*, */");

            } else {
                for (char c : illegalChars) {
                    System.out.println(c);
                    if (password.matches(".*" + (c == '\\' ? "\\\\" : Pattern.quote(String.valueOf(c))) + ".*")) {
                        throw new UserRegistrationException("Caracteres inválidos: " + join(illegalChars, ", "));
                    }
                }
            }
        }
    }

    private String join(char[] chars, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append(chars[i]);
            if (i < chars.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}