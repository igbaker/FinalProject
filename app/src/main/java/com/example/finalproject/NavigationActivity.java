package com.example.finalproject;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar bar;
    private int i = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //progress bar
        bar = (ProgressBar) findViewById(R.id.bar);

        // setup toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        // setup navigationdrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // welcome message
        View snack = findViewById(R.id.news_reader);
        Snackbar.make(snack, "Hello! Please enjoy your reading experience :)", Snackbar.LENGTH_LONG)
                .show();

        // loads shared preferences
        loadEmail();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        // handles clicks on the action bar items
        switch (item.getItemId()) {
            case R.id.menu1:
                message = "Home sweet home";
                Intent intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                break;
            case R.id.menu2:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.
                    setTitle("Info")
                    .setMessage("Please edit your profile information here and navigate to the reader or favourites page by clicking the respective buttons")
                    .show();
            return false;

        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // handles clicks on the action bar items
        switch (item.getItemId()) {
            case R.id.nav1:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav2:
                Intent intent2 = new Intent(this, NavigationActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav3:
                Intent intent3 = new Intent(this, ReaderActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav4:
                Intent intent4 = new Intent(this, FavouritesActivity.class);
                startActivity(intent4);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void setListeners() {
    // goes to favourites activity on button click
        findViewById(R.id.favourites)
                .setOnClickListener(v ->
                        startActivity(
                                new Intent(NavigationActivity.this, com.example.finalproject.FavouritesActivity.class)
                        )
                );

        //goes to reader activity and displays progress bar
        Button btn = (Button)findViewById(R.id.news_reader);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(NavigationActivity.this, com.example.finalproject.ReaderActivity.class)
                );
                i = bar.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 1;
                            handler.post(new Runnable() {
                                public void run() {
                                    bar.setProgress(i);
                                }
                            });
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

    }

    //loads email from shared prefs
    private void loadEmail() {
        EditText emailText = findViewById(R.id.email);
        emailText.setText(
                getSharedPreferences(LoginActivity.LAB, Context.MODE_PRIVATE)
                        .getString("email", "")
        );
    }

}
