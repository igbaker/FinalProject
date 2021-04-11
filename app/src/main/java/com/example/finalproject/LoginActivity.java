package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalproject.database.DBHelper;
import com.example.finalproject.database.FavouritesRepository;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    static final String LAB = "The Guardian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadEmail();
        setListeners();
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveEmail();
    }

    // goes to next activity when login button pressed
    private void setListeners() {
        findViewById(R.id.login)
                .setOnClickListener(v ->
                        startActivity(
                                new Intent(LoginActivity.this, NavigationActivity.class)
                        )
                );
    }

    //saves email in shared preferences
    private void saveEmail() {
        EditText email = findViewById(R.id.email);
        getSharedPreferences(LAB, Context.MODE_PRIVATE).edit()
                .putString("email", email.getText().toString())
                .apply();
    }

    //loads email in shared preferences
    private void loadEmail() {
        EditText email = findViewById(R.id.email);
        email.setText(
                getSharedPreferences(LAB, Context.MODE_PRIVATE)
                        .getString("email", "")
        );
    }
}