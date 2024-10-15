package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.models.views.UserViewModel;
import com.example.app.welcome_views.Welcome;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.databinding.ActivityMainBinding;

import com.example.app.db.utils.crud.Usuario;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private DialogInterface.OnClickListener logoutListener;

    private long backPressedTime;
    private Toast backToast;

    private View navHeaderView;

    private TextView userName;
    private TextView userSurname;
    private TextView userSurname2;
    private TextView userMatricula;

    private UsuarioModel usuario;
    private Usuario crudUsuario;

    private UserViewModel userViewModel;

    private int[] fragmentsId = {
            R.id.nav_profile,
            R.id.nav_themes,
            R.id.nav_admin
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                fragmentsId
        )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        inflater(navigationView);
        setTextViews();

        // Logout listener
        logoutListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int i) {
                startActivity(new Intent(getApplicationContext(), Welcome.class));
                finish();
            }
        };

        // Logout btn
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Cerrar sesión");
                alert.setMessage("¿Estás seguro de que deseas cerrar sesión?");
                alert.setPositiveButton(android.R.string.yes, logoutListener);
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        });

        // Settings
        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // No disponible
                Toast.makeText(getApplicationContext(),"Opción aún no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        getUserInfo();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2500 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Presiona atrás otra vez para salir de la aplicación", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void getUserInfo() {
        Intent intent = getIntent();
        usuario = (UsuarioModel) intent.getSerializableExtra("usuario");

        if (usuario != null) {
            userViewModel.setUsuario(usuario);
            setUserInfoInViews();

            if (!usuario.tipoAdministradorValue) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.nav_admin).setVisible(false);
            }

        } else {
            // No debería llegar aquí
            startActivity(new Intent(getApplicationContext(), Welcome.class));
            finish();
        }
    }

    private void inflater(NavigationView navigationView) {
        LayoutInflater inflater = LayoutInflater.from(this);
        navHeaderView = inflater.inflate(R.layout.nav_header_main, navigationView, false);
        navigationView.addHeaderView(navHeaderView);
    }

    private void setTextViews() {
        userName = navHeaderView.findViewById(R.id.user_name);
        userSurname = navHeaderView.findViewById(R.id.user_surname);
        userSurname2 = navHeaderView.findViewById(R.id.user_surname2);
        userMatricula = navHeaderView.findViewById(R.id.user_matricula);
    }

    private void setUserInfoInViews() {
        userName.setText(usuario.nombreValue);
        userSurname.setText(usuario.aPaternoValue);
        userSurname2.setText(usuario.aMaternoValue);
        userMatricula.setText(usuario.matriculaValue);
    }
}