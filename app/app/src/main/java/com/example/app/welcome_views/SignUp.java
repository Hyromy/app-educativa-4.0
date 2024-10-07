package com.example.app.welcome_views;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.Welcome;

import java.util.concurrent.CountDownLatch;

public class SignUp extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sing_up);

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

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showAlertAndContinue("Registro", "Función aún no disponible", () -> {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(intent);
                });
            }
        });

        findViewById(R.id.btn_to_sign_in).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
            }
        });
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

    private void showAlertAndContinue(String title, String message, Runnable onContinue) {
        runOnUiThread(() -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUp.this);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton(android.R.string.yes, (dialog, which) -> onContinue.run());
            alert.show();
        });
    }
}