package com.desarrollo.eventusupt;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityOrganizer extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewOrganizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_organizer);
        setupBottomNavigationBar();
    }

    private void setupBottomNavigationBar(){
        bottomNavigationViewOrganizer = findViewById(R.id.buttom_navigation_organizer);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_organizer);
        NavigationUI.setupWithNavController(bottomNavigationViewOrganizer, navHostFragment.getNavController());
    }

}
