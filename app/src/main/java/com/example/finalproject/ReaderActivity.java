package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.finalproject.database.FavouritesRepository;
import com.example.finalproject.rss.RssItem;
import com.example.finalproject.rss.RssSax;
import com.google.android.material.navigation.NavigationView;

public class ReaderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //for rssfeed
    private ListView messageList;
    ArrayAdapter<String> adapter;

    //for fragment
    DetailsFragment aFragment = new DetailsFragment();
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_TITLE = "ITEM_TITLE";
    public static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public static final String ITEM_DATE = "ITEM_DATE";
    public static final String ITEM_LINK = "ITEM_LINK";

    //for database
    FavouritesRepository favouritesRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        // get rssfeed
        messageList = (ListView) findViewById(R.id.list1);

        adapter = new ArrayAdapter<String>(this, R.layout.listview_each_item);
        new GetRssFeed().execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");

        // get database connection
        favouritesRepository = new FavouritesRepository(this);

        // asks user if they want to save rssitem to favourites
        messageList.setOnItemLongClickListener(((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.
                    setTitle("Add to favourites?")
                    .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                        favouritesRepository.save(adapter.getItem(position));

                    })
                    .setNegativeButton(R.string.no, (click, arg) -> {
                    })
                    .show();

            return false;
        }));

        //toolbar setup
        Toolbar myToolbar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        //navigation drawer setup
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                        .setMessage("Please short click on the article to view information. Long click to add to favourites")
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
            case R.id.nav2: //launches activity
                Intent intent2 = new Intent(this, NavigationActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav3: //launches activity
                Intent intent3 = new Intent(this, ReaderActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav4: //launches activity
                Intent intent4 = new Intent(this, FavouritesActivity.class);
                startActivity(intent4);
                break;

        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private class GetRssFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                // intializes reader and gets titles for listview
                RssSax rssReader = new RssSax(params[0]);
                for (RssItem item : rssReader.getItems()) {
                    adapter.add(item.getTitle());

                    // short click listener to get details fragment
                    messageList.setOnItemClickListener(((parent, view, position, id) -> {

                        Bundle dataToPass = new Bundle();

                        dataToPass.putLong(ITEM_ID, id);
                        dataToPass.putString(ITEM_TITLE, item.getTitle());
                        dataToPass.putString(ITEM_DESCRIPTION, item.getDescription());
                        dataToPass.putString(ITEM_DATE, item.getDate());
                        dataToPass.putString(ITEM_LINK, item.getLink());

                        Intent nextActivity = new Intent(ReaderActivity.this, EmptyActivity.class);
                        nextActivity.putExtras(dataToPass);
                        startActivity(nextActivity);

                    }));
                }
            } catch (Exception e) {
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            messageList.setAdapter(adapter);
        }
    }

}













