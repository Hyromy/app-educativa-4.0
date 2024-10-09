package com.example.app.ui.themes.theme;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.Objects;

import com.example.app.ui.themes.exercise.*;
import com.example.app.utils.*;

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            setToolbarTitle(bundle.getString("themeName"));
        }

        view.findViewById(R.id.btn_to_test).setOnClickListener(onExerciseClickListener);
        view.findViewById(R.id.btn_to_activity1).setOnClickListener(onExerciseClickListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(title);
        }
    }

    private View.OnClickListener onExerciseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String activityName = (String) view.getTag();

            // meto todos los datos que necesito en el bundle
            Bundle bundle = new Bundle();
            bundle.putString("activityName", activityName);

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_theme_to_nav_exercise, bundle); // navego a la siguiente pantalla con el bundle
        }
    };
}