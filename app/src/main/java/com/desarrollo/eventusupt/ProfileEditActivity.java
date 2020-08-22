package com.desarrollo.eventusupt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.eventusupt.fragments.ProfileFragment;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity {

    TextView user_text_username;
    TextView user_text_email;
    EditText user_edt_name;
    EditText user_edt_lastname;
    EditText user_edt_phone;
    EditText user_edt_email;
    EditText user_edt_status;
    Button user_button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        
        inicializarDatos();

        String name = getIntent().getStringExtra("name");
        String lastname = getIntent().getStringExtra("lastname");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String status = getIntent().getStringExtra("status");

        user_text_username.setText(name);
        user_text_email.setText(email);
        user_edt_name.setText(name);
        user_edt_lastname.setText(lastname);
        user_edt_phone.setText(phone);
        user_edt_email.setText(email);
        user_edt_status.setText(status);

        user_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditUserProfile();
            }
        });
    }

    private void inicializarDatos() {
        user_text_username = findViewById(R.id.user_text_username);
        user_text_email = findViewById(R.id.user_text_email);
        user_edt_name = findViewById(R.id.user_edt_name);
        user_edt_lastname = findViewById(R.id.user_edt_lastname);
        user_edt_phone = findViewById(R.id.user_edt_phone);
        user_edt_email = findViewById(R.id.user_edt_email);
        user_edt_status = findViewById(R.id.user_edt_status);
        user_button_save = findViewById(R.id.user_button_save);
    }

    private void onEditUserProfile() {
        String token = SaveSharedPreference.getLoggedToken(getApplicationContext());
        String name = user_edt_name.getText().toString();
        String lastname = user_edt_lastname.getText().toString();
        String phone = user_edt_phone.getText().toString();
        String email = user_edt_email.getText().toString();

        Call<UserResponse> call = RetrofitClient.getInstance().getApi().editProfileUser(token, name, lastname, phone, email);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "Error al editar el perfil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
