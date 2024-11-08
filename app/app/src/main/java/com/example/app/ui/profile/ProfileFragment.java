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
import com.example.app.ui.themes.ThemesFragment;

import com.example.app.db.models.UsuarioModel;

public class ProfileFragment extends Fragment {
    private UserViewModel userViewModel;
    private UsuarioModel usuario;

    private TextView userMatricula;
    private EditText userName;
    private EditText userSurname;
    private EditText userSurname2;
    private EditText userNewPassword;
    private EditText userConfirmPassword;

    public static ThemesFragment newInstance() {
        return new ThemesFragment();
    }

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
            clear();
            setInfoInWidgets();
        });

        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Función aún no disponible", Toast.LENGTH_SHORT).show();
        });
    }

    private void clear() {
        userName.setText("");
        userSurname.setText("");
        userSurname2.setText("");
        userNewPassword.setText("");
        userConfirmPassword.setText("");
    }
}