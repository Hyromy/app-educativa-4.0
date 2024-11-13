package com.example.app.welcome_views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.R;
import com.example.app.exceptions.EmptyInputException;
import com.example.app.exceptions.UserNotFoundException;

public class ForgotPassword extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

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

        findViewById(R.id.btn_set_new_password).setOnClickListener(resetPassListener());

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

    private View.OnClickListener resetPassListener() {
        Context context = getApplicationContext();

        return new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    inputsNotEmtpy();
                    findUser();
                    passwordAreSame();

                    resetPass();

                } catch (EmptyInputException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                } catch (UserNotFoundException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                } catch (IllegalArgumentException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(context, "Ocurrió un problema, intentalo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void inputsNotEmtpy() throws EmptyInputException {
        if (findViewById(R.id.matricula).toString().isEmpty() &&
            findViewById(R.id.input_new_password).toString().isEmpty() &&
            findViewById(R.id.input_password_confirm).toString().isEmpty()) {
            throw new EmptyInputException("completa todos los campos");
        }

        if (findViewById(R.id.matricula).toString().isEmpty()) {
            throw new EmptyInputException("Ingresa tu matrícula");
        }

        if (findViewById(R.id.input_new_password).toString().isEmpty()) {
            throw new EmptyInputException("Ingresa tu nueva contraseña");
        }

        if (findViewById(R.id.input_password_confirm).toString().isEmpty()) {
            throw new EmptyInputException("Confirma tu nueva contraseña");
        }
    }

    private void findUser() throws UserNotFoundException {
        throw new UserNotFoundException("(no implementado) Usuario no encontrado");
    }

    public void passwordAreSame() throws IllegalArgumentException {
        throw new IllegalArgumentException("(no implementado) Las contraseñas no coinciden");
    }

    private void resetPass() {
        Toast.makeText(getApplicationContext(), "(no implementado) Contraseña cambiada", Toast.LENGTH_SHORT).show();
    }
}