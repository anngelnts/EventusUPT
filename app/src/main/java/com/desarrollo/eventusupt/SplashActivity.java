package com.desarrollo.eventusupt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.desarrollo.eventusupt.helpers.SaveSharedPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserLogged();
    }

    private void UserLogged(){
        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            if(SaveSharedPreference.getLoggedType(getApplicationContext()).equals("1")){
                Intent intent = new Intent(this, MainActivityOrganizer.class);
                startActivity(intent);
            }else if(SaveSharedPreference.getLoggedType(getApplicationContext()).equals("2")){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
