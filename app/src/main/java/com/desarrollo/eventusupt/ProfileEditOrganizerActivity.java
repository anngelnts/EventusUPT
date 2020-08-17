package com.desarrollo.eventusupt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.OrganizerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditOrganizerActivity extends AppCompatActivity {

    EditText organizer_edt_name;
    EditText organizer_edt_acronym;
    EditText organizer_edt_phone;
    EditText organizer_edt_status;
    EditText organizer_edt_email;
    EditText organizer_edt_facebook;
    EditText organizer_edt_website;
    Button organizer_button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_organizer);

        inicializarDatos();

        String name = getIntent().getStringExtra("name");
        String acronym = getIntent().getStringExtra("acronym");
        String phone = getIntent().getStringExtra("phone");
        String status = getIntent().getStringExtra("status");
        String email = getIntent().getStringExtra("email");
        String facebook = getIntent().getStringExtra("facebook");
        String website = getIntent().getStringExtra("website");

        organizer_edt_name.setText(name);
        organizer_edt_acronym.setText(acronym);
        organizer_edt_phone.setText(phone);
        organizer_edt_status.setText(status);
        organizer_edt_email.setText(email);
        organizer_edt_facebook.setText(facebook);
        organizer_edt_website.setText(website);

        organizer_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditOrganizerProfile();
            }
        });
    }

    private void onEditOrganizerProfile() {
        String token = SaveSharedPreference.getLoggedToken(getApplicationContext());
        String name = organizer_edt_name.getText().toString();
        String acronym = organizer_edt_acronym.getText().toString();
        String phone = organizer_edt_phone.getText().toString();
        String email = organizer_edt_email.getText().toString();
        String facebook = organizer_edt_facebook.getText().toString();
        String website = organizer_edt_website.getText().toString();

        Call<OrganizerResponse> call = RetrofitClient.getInstance().getApi().editProfileOrganizer(token, name, acronym, phone, website, facebook, email);
        call.enqueue(new Callback<OrganizerResponse>() {
            @Override
            public void onResponse(Call<OrganizerResponse> call, Response<OrganizerResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivityOrganizer.class);
                    startActivity(intent);
                    finish();

                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "Error al editar el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrganizerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarDatos() {
        organizer_edt_name = findViewById(R.id.organizer_edt_name);
        organizer_edt_acronym = findViewById(R.id.organizer_edt_acronym);
        organizer_edt_phone = findViewById(R.id.organizer_edt_phone);
        organizer_edt_status = findViewById(R.id.organizer_edt_status);
        organizer_edt_email = findViewById(R.id.organizer_edt_email);
        organizer_edt_facebook = findViewById(R.id.organizer_edt_facebook);
        organizer_edt_website = findViewById(R.id.organizer_edt_website);
        organizer_button_save = findViewById(R.id.organizer_button_save);
    }
}
