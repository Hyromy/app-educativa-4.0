package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Welcome extends AppCompatActivity {
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

    private void toMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginForm() {
        // Access btn
        findViewById(R.id.btn_access).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean login = true;
                if (login) {
                    toMain();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Exit btn
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finishAffinity();
            }
        });

    }
}