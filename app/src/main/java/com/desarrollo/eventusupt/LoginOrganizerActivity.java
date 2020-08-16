package com.desarrollo.eventusupt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOrganizerActivity extends AppCompatActivity {

    Button buttonLoginOrganizer;
    TextView textbuttonBack;
    EditText editUsername;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_organizer);

        editUsername = findViewById(R.id.loginOrganizer_edit_username);
        editPassword = findViewById(R.id.loginOrganizer_edit_password);
        buttonLoginOrganizer = findViewById(R.id.loginOrganizer_button_login);
        textbuttonBack = findViewById(R.id.loginOrganizer_txtbutton_login_organizer);

        buttonLoginOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                if(username.isEmpty()) {
                    Toast.makeText(LoginOrganizerActivity.this, "por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(LoginOrganizerActivity.this, "por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
                }
                else {
                    onLogin(username, password);
                }
            }
        });

        textbuttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

    }

    private void initHome(){
        Intent intent = new Intent(this, MainActivityOrganizer.class);
        startActivity(intent);
        finish();
    }

    private void onBack(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLogin(String username, String password){
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().loginOrganizer(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    String token = response.body().getToken();
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), token, true, "1");
                    initHome();
                }else if(response.code() == 401){
                    Toast.makeText(LoginOrganizerActivity.this, "Organizador no autorizado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //
            }
        });
    }


}
