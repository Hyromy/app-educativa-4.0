package com.example.app.ui.prueba_fragmento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.databinding.PruebaFragmentoBinding;

public class PruebaFragmento extends Fragment {
    private PruebaFragmentoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container,
                         Bundle savedInstanceState) {
        PruebaFragmentoViewModel pruebaFragmentoViewModel =
                new ViewModelProvider(this).get(PruebaFragmentoViewModel.class);

        binding = PruebaFragmentoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFragmento;
        pruebaFragmentoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}