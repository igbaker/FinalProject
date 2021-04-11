package com.example.finalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.database.FavouritesRepository;
import com.example.finalproject.rss.RssItem;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    // for database
    private final List<RssItem> favourites = new ArrayList<>();
    MessageListAdapter messageListAdapter = new MessageListAdapter();
    FavouritesRepository favouritesRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //open database instance
        favouritesRepository = new FavouritesRepository(this);

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


        setListeners();
        //load favourites from database
        load();

    }

    //load favourites from database
    private void load() {
        favourites.addAll(favouritesRepository.findAll());
        messageListAdapter.notifyDataSetChanged();
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
                        .setMessage("Click on your favourites to view or delete the article")
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

        // set how list adpater will function
        ListView messageList = findViewById(R.id.list2);
        messageList.setAdapter(messageListAdapter);


        // long click to delete item
        messageList.setOnItemLongClickListener(((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.
                    setTitle(getString(R.string.delete))
                    .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                        favouritesRepository.delete((RssItem) messageListAdapter.getItem(position));
                        favourites.remove(position);
                        messageListAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, (click, arg) -> {
                    })
                    .show();
            return false;
        }));

    }

    private class MessageListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return favourites.size();
        }

        @Override
        public Object getItem(int position) {
            return favourites.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
                return R.layout.activity_favourites;

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(getItemViewType(position), parent, false);


            return newView;
        }
    }

}