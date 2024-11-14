package com.example.app.welcome_views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.R;
import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;
import com.example.app.exceptions.UserRegistrationException;

public class ForgotPassword extends AppCompatActivity {
    private ForgotPasswordModel model = new ForgotPasswordModel();

    private long backPressedTime;
    private Toast backToast;
    private Usuario crud;

    EditText Matricula;
    EditText NewPassword;
    EditText PasswordConfirm;

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
                Matricula = findViewById(R.id.matricula);
                NewPassword = findViewById(R.id.input_new_password);
                PasswordConfirm = findViewById(R.id.input_password_confirm);

                String matricula = Matricula.getText().toString();
                String newPassword = NewPassword.getText().toString();
                String passwordConfirm = PasswordConfirm.getText().toString();

                crud = new Usuario(context);
                crud.open();

                try {
                    model.inputsNotEmtpy(matricula, newPassword, passwordConfirm);

                    UsuarioModel usuario = model.findUser(crud, matricula);

                    model.passwordAreSame(newPassword, passwordConfirm);

                    model.validate(usuario.matriculaValue, newPassword);
                    model.resetPass(usuario, newPassword, crud, context);
                    clear();

                } catch (IllegalArgumentException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                } catch (UserRegistrationException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Toast.makeText(context, "Ocurrió un problema, intentalo más tarde", Toast.LENGTH_SHORT).show();

                } finally {
                    crud.close();
                }
            }
        };
    }

    private void clear() {
        Matricula.setText("");
        NewPassword.setText("");
        PasswordConfirm.setText("");
    }
}