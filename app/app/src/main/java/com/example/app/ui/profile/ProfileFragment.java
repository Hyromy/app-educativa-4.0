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

import com.example.app.R;
import com.example.app.databinding.FragmentHomeBinding;
import com.example.app.ui.home.HomeViewModel;
import com.example.app.ui.themes.ThemesFragment;
import com.example.app.ui.themes.ThemesViewModel;

import com.example.app.db.models.UsuarioModel;

public class ProfileFragment extends Fragment {
    private ThemesViewModel mViewModel;



    public static ThemesFragment newInstance() {
        return new ThemesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemesViewModel.class);
        // TODO: Use the ViewModel
    }

    // onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UsuarioModel usuario = (UsuarioModel) getArguments().getSerializable("usuario");
            System.out.println(usuario.getData());
        }
    }
}