package com.desarrollo.eventusupt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.LoginResponse;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    TextView textbuttonRegister;
    TextView textbuttonLoginOrganizer;
    EditText editUsername;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.login_edit_username);
        editPassword = findViewById(R.id.login_edit_password);
        buttonLogin = findViewById(R.id.login_button_login);
        textbuttonRegister = findViewById(R.id.login_txtbutton_register);
        textbuttonLoginOrganizer = findViewById(R.id.login_txtbutton_login_organizer);

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

        textbuttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        textbuttonLoginOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginOrganizer();
            }
        });

    }

    private void initHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLogin(String username, String password){
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    String token = response.body().getToken();
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), token, true);
                    initHome();
                }else if(response.code() == 401){
                    Toast.makeText(LoginActivity.this, "Usuario no autorizado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //
            }
        });
    }

    private void onRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLoginOrganizer(){
        Intent intent = new Intent(this, LoginOrganizerActivity.class);
        startActivity(intent);
        finish();
    }

}
