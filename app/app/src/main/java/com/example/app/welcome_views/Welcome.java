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

import com.example.app.db.models.EstudianteModel;
import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;
import com.example.app.db.utils.crud.Estudiante;
// import com.example.app.db.utils.crud.Administrador;

import com.example.app.utils.Encryptor;

public class Welcome extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private Usuario crudUsuario;
    private Estudiante crudEstudiante;
    // private Administrador crudAdministrador;

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

        inputMatricula = findViewById(R.id.input_matricula);
        inputContrasena = findViewById(R.id.input_password);

        crudUsuario = new Usuario(getApplicationContext());
        crudEstudiante = new Estudiante(getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Greeuttec));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // verificar si existe un archivo de login
        boolean logged = false;
        if (logged) {
            toMain();
        } else {
            loginForm();
        }
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

    private void toMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

                    crudEstudiante.open();
                    EstudianteModel estudiante = crudEstudiante.read(usuario.idValue);
                    crudEstudiante.close();

                    if (usuario == null) {
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    } else {






                        // enviar a la vista principal con los datos del usuario
                        toMain();
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