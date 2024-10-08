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


import com.example.app.R;

public class ThemesFragment extends Fragment {
    private ThemesViewModel mViewModel;

    public static ThemesFragment newInstance() {
        return new ThemesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_themes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.theme_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String themeName = "{ThemeName}";

                Bundle bundle = new Bundle();
                bundle.putString("themeName", themeName);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_themes_to_nav_theme, bundle);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemesViewModel.class);
        // TODO: Use the ViewModel
    }
}