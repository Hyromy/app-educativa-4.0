package com.example.app.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.db.models.views.UserViewModel;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;
import com.example.app.utils.Encryptor;

public class ProfileFragment extends Fragment {
    private UserViewModel userViewModel;
    private UsuarioModel usuario;

    private TextView userMatricula;
    private EditText userName;
    private EditText userSurname;
    private EditText userSurname2;
    private EditText userNewPassword;
    private EditText userConfirmPassword;

    private ProfileModel model = new ProfileModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // recibe informacion de usuario
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                this.usuario = usuario;

                setWidgets(view);
                setInfoInWidgets();
                setListeners(view);
            }
        });

        return view;
    }

    private void setWidgets(View view) {
        userMatricula = view.findViewById(R.id.user_matricula);
        userName = view.findViewById(R.id.user_name);
        userSurname = view.findViewById(R.id.user_surname);
        userSurname2 = view.findViewById(R.id.user_surname2);
        userNewPassword = view.findViewById(R.id.new_password);
        userConfirmPassword = view.findViewById(R.id.confirm_password);
    }

    private void setInfoInWidgets() {
        userMatricula.setText(this.usuario.matriculaValue);
        userName.setText(this.usuario.nombreValue);
        userSurname.setText(this.usuario.aPaternoValue);
        userSurname2.setText(this.usuario.aMaternoValue);
    }

    private void setListeners(View view) {
        view.findViewById(R.id.btn_clear).setOnClickListener(v -> {
            clearAndSet();
        });

        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            String nombre = userName.getText().toString();
            String aPaterno = userSurname.getText().toString();
            String aMaterno = userSurname2.getText().toString();

            String newPassword = userNewPassword.getText().toString();
            String confirmPassword = userConfirmPassword.getText().toString();

            Usuario crud = new Usuario(getContext());
            crud.open();

            try {
                model.validateNames(nombre, aPaterno, aMaterno);
                usuario.nombreValue = nombre;
                usuario.aPaternoValue = aPaterno;
                usuario.aMaternoValue = aMaterno;

                if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                    model.validatePasswords(newPassword, confirmPassword);

                    Encryptor encryptor = new Encryptor();
                    newPassword = encryptor.toHash(newPassword, usuario.matriculaValue, usuario.idValue);

                    this.usuario.contrasenaValue = newPassword;
                }

                crud.update(this.usuario);
                clearAndSet();
                Toast.makeText(getContext(), "Datos actualizados (los cambios pueden tardar)", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            } finally {
                crud.close();
            }
        });
    }

    private void clear() {
        userName.setText("");
        userSurname.setText("");
        userSurname2.setText("");
        userNewPassword.setText("");
        userConfirmPassword.setText("");
    }

    private void clearAndSet() {
        clear();
        setInfoInWidgets();
    }
}