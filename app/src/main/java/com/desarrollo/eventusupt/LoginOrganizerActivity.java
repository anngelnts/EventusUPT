package com.desarrollo.eventusupt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                onLogin();
            }
        });

        textbuttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

    }

    private void onBack(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLogin(){
        Intent intent = new Intent(this, MainActivityOrganizer.class);
        startActivity(intent);
        finish();
    }


}
