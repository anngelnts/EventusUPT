package com.desarrollo.eventusupt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editUsername;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.login_edit_username);
        editPassword = findViewById(R.id.login_edit_password);
        buttonLogin = findViewById(R.id.login_button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                if(username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
                }
                else {
                    onLogin(username, password);
                }
            }
        });
    }

    private void onLogin(String username, String password){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
