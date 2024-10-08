package com.example.app.ui.themes.theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;

import java.util.Objects;

public class ThemeFragment extends Fragment {

    private ThemeViewModel mViewModel;

    public static ThemeFragment newInstance() {
        return new ThemeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String themeName = getArguments().getString("themeName");

            System.out.println("Theme Name: " + themeName);

            if (requireActivity() instanceof AppCompatActivity) {
                ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(themeName);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        // TODO: Use the ViewModel
    }
}