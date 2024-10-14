package com.example.app.ui.themes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.app.R;
import com.example.app.db.models.views.UserViewModel;
import com.example.app.db.models.UsuarioModel;

public class ThemesFragment extends Fragment {
    private ThemesViewModel mViewModel;
    private UserViewModel userViewModel;
    private UsuarioModel usuario;

    private TextView logout;

    public static ThemesFragment newInstance() {
        return new ThemesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_themes, container, false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                this.usuario = usuario;

                setWidgets(view);
                setInfoInWidgets();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.theme_1).setOnClickListener(onThemeClickListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemesViewModel.class);
        // TODO: Use the ViewModel
    }

    private View.OnClickListener onThemeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String themeName = "{ThemeName}";

            // meto todos los datos que necesito en el bundle
            Bundle bundle = new Bundle();
            bundle.putString("themeName", themeName);

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_themes_to_nav_theme, bundle); // navego a la siguiente pantalla con el bundle
        }
    };

    private void setWidgets(View view) {
        logout = view.findViewById(R.id.log);
    }

    private void setInfoInWidgets() {
        logout.setText(usuario.getData());
    }
}