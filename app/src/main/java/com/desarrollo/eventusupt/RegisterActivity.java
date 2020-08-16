package com.desarrollo.eventusupt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desarrollo.eventusupt.retrofit.ApiService;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ApiService mAPIService;

    EditText editName;
    EditText editLastname;
    EditText editPhone;
    EditText editEmail;
    EditText editPassword;
    Button buttonRegister;
    Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.register_edit_name);
        editLastname = findViewById(R.id.register_edit_lastname);
        editPhone = findViewById(R.id.register_edit_phone);
        editEmail = findViewById(R.id.register_edit_email);
        editPassword = findViewById(R.id.register_edit_password);
        buttonRegister = findViewById(R.id.register_button_finish);
        buttonCancel = findViewById(R.id.register_button_cancel);

        mAPIService = RetrofitClient.getInstance().getApi();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int audience = 2;
                String name = editName.getText().toString();
                String lastname = editLastname.getText().toString();
                String phone = editPhone.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                int status = 1;

                if(name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Por favor ingrese sus nombres", Toast.LENGTH_SHORT).show();
                } else if (lastname.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor ingrese sus apellidos", Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor ingrese su numero de celular", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    sendPost(audience, name, lastname, phone, email, password, status);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnBack();
            }
        });
    }

    private void sendPost(int audience, String name, String lastname, String phone, String email, String password, int status) {
        mAPIService.createUser(audience, name, lastname, phone, email, password, status).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    OnBack();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void OnBack(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
