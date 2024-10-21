package com.example.app.welcome_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.MainActivity;
import com.example.app.R;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;

import com.example.app.utils.Encryptor;

import com.example.app.preload_data.Admin;

public class Welcome extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private Usuario crudUsuario;

    private Encryptor encryptor = new Encryptor();

    private EditText inputMatricula;
    private EditText inputContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setStatusBar();
        setEditTexts();
        setCruds();

        hasSesionFile();
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

    private void setStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Greeuttec));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void setEditTexts() {
        inputMatricula = findViewById(R.id.input_matricula);
        inputContrasena = findViewById(R.id.input_password);
    }

    private void setCruds() {
        crudUsuario = new Usuario(getApplicationContext());
        Admin createAdmins = new Admin(getApplicationContext(), true);

    }

    private void hasSesionFile() {
        // verificar si existe un archivo de login
        boolean logged = false;
        if (logged) {
            toMain(new UsuarioModel());
        } else {
            loginForm();
        }
    }

    private void toMain(UsuarioModel usuario) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();
    }

    private void loginForm() {
        // Access btn
        findViewById(R.id.btn_access).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                crudUsuario.open();

                String matricula = inputMatricula.getText().toString();
                int id = crudUsuario.getIdBy(UsuarioModel.matricula, matricula);

                if (id <= 0) {
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    crudUsuario.close();
                } else {
                    String contrasena = inputContrasena.getText().toString();
                    contrasena = encryptor.toHash(contrasena, matricula, id);
                    UsuarioModel usuario = crudUsuario.login(matricula, contrasena);
                    crudUsuario.close();

                    if (usuario == null) {
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        toMain(usuario);
                    }
                }
            }
        });

        // Exit btn
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finishAffinity();
            }
        });

        // Sign up btn
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        // Forgot password btn
        findViewById(R.id.btn_forgot).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
}