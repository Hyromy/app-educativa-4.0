package com.example.app.welcome_views;

import android.app.AlertDialog;
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

import com.example.app.R;

import com.example.app.db.models.UsuarioModel;
import com.example.app.db.utils.crud.Usuario;

import com.example.app.utils.Encryptor;

public class SignUp extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private SingUpModel model = new SingUpModel();
    private Usuario crudUsuario;

    private Encryptor encryptor = new Encryptor();

    private EditText input_matricula;
    private EditText input_name;
    private EditText input_surname;
    private EditText input_surname2;
    private EditText input_password;
    private EditText input_confirm_password;

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

        crudUsuario = new Usuario(getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Greeuttec));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getEditTexts();
        clear();

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);

                String matricula = input_matricula.getText().toString();
                String name = input_name.getText().toString();
                String surname = input_surname.getText().toString();
                String surname2 = input_surname2.getText().toString();
                String password = input_password.getText().toString();
                String confirmPassword = input_confirm_password.getText().toString();

                try {
                    model.valideMatricula(matricula);
                    model.validateName(name);
                    model.validateName(surname);
                    model.validateName(surname2);
                    model.validatePassword(password);

                    if (!password.equals(confirmPassword)) {
                        toast.setText("Las contraseñas no coinciden");
                    } else {
                        crudUsuario.open();
                        int id = crudUsuario.nextId();
                        String passwordHash = encryptor.toHash(password, matricula, id);

                        UsuarioModel usuario = new UsuarioModel(
                            id,
                            matricula,
                            passwordHash,
                            name,
                            surname,
                            surname2,
                                false
                        );
                        crudUsuario.insert(usuario);
                        crudUsuario.close();

                        toast.setText("Usuario registrado");
                        clear();
                    }
                } catch (Exception e) {
                    toast.setText(e.getMessage());
                } finally {
                    toast.show();
                }
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

    private void getEditTexts() {
        input_matricula = findViewById(R.id.input_matricula);
        input_name = findViewById(R.id.input_name);
        input_surname = findViewById(R.id.input_surname);
        input_surname2 = findViewById(R.id.input_surname2);
        input_password = findViewById(R.id.input_password);
        input_confirm_password = findViewById(R.id.input_confirm_password);
    }

    private void clear() {
        input_matricula.setText("");
        input_name.setText("");
        input_surname.setText("");
        input_surname2.setText("");
        input_password.setText("");
        input_confirm_password.setText("");
    }
}