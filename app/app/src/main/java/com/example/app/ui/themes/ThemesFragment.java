package com.example.app.ui.themes;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.db.models.TemaModel;
import com.example.app.db.models.views.UserViewModel;
import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Tema;

public class ThemesFragment extends Fragment {
    private ThemesViewModel mViewModel;
    private UserViewModel userViewModel;
    private UsuarioModel usuario;

    private Tema crudTema;

    private LinearLayout themesLayout;

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
                Context context = getContext();
                this.usuario = usuario;

                setWidgets(view);
                crudTema = new Tema(context);
                crudTema.open();
                TemaModel[] temas = crudTema.readAll();
                for (TemaModel tema : temas) {
                    setThemeFrame(tema, context);
                }
                crudTema.close();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemesViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setWidgets(View view) {
        themesLayout = view.findViewById(R.id.themes_layout);
    }

    private void setThemeFrame(TemaModel tema, Context context) {
        TextView theme = new TextView(context);
        theme.setId(View.generateViewId());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 16);
        theme.setLayoutParams(params);

        theme.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        theme.setPadding(32, 32, 32, 32);
        theme.setTextSize(32);
        theme.setTextColor(getResources().getColor(R.color.white));
        theme.setBackground(getResources().getDrawable(R.drawable.rounded_box));
        theme.setBackgroundTintList(getResources().getColorStateList(R.color.Greeuttec));
        theme.setText(tema.tituloValue);
        theme.setTag("tema_" + tema.idValue);

        theme.setOnClickListener(onThemeClickListener(tema));

        themesLayout.addView(theme);
    }

    private View.OnClickListener onThemeClickListener(TemaModel tema) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("tema", tema);
                bundle.putSerializable("usuario", usuario);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_themes_to_nav_theme, bundle);
            }
        };
    }
}