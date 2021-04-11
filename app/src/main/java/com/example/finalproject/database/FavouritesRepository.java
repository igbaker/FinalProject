package com.example.finalproject.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.example.finalproject.rss.RssItem;
import java.util.ArrayList;
import java.util.List;

public class FavouritesRepository {

    private final DBHelper helper;
    private final Context context;
    private SQLiteDatabase db;

    //gets database instance
    public FavouritesRepository(Context context) {
        this.helper = DBHelper.getInstance(context);
        this.context = context;
    }

    //saves item to database
    public RssItem save(RssItem favourite) {
        db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBHelper.COL_TITLE, favourite.getTitle());
        row.put(DBHelper.COL_LINK, favourite.getLink());

        long id = db.insert(DBHelper.TABLE_NAME, null, row);
        db.close();
        return new RssItem(id, favourite);
    }

    //saves item to database
    public RssItem save(String favourite) {
        db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBHelper.COL_TITLE, favourite);
        //row.put(DBHelper.COL_LINK, favourite.getLink());

        long id = db.insert(DBHelper.TABLE_NAME, null, row);
        db.close();
        return new RssItem(id, favourite);
    }

    // gets stored messages
    public List<RssItem> findAll() {
        db = helper.getReadableDatabase();
        String[] columns = {
            DBHelper.COL_ID,
            DBHelper.COL_TITLE,
            DBHelper.COL_LINK
        };
        Cursor results = db.query(
            false,
            DBHelper.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null,
            null
        );
        List<RssItem> favourites = new ArrayList<>(results.getCount());
        if (results.getCount() > 0) {
            while (results.moveToNext()) {
                RssItem favourite = new RssItem(
                    // ID
                    results.getLong(results.getColumnIndex(DBHelper.COL_ID)),
                    // title
                    results.getString(results.getColumnIndex(DBHelper.COL_TITLE)),
                    // link
                        results.getString(results.getColumnIndex(DBHelper.COL_LINK))
                );
                favourites.add(favourite);
            }
            results.moveToFirst();
            printCursor(results, db.getVersion());
        }
        // closes database
        results.close();
        db.close();
        return favourites;
    }

    public void delete(RssItem favourite) {
        db = helper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, "_id=?", new String[]{favourite.getId().toString()});
        db.close();
    }

    // prints cursor
    private void printCursor(Cursor c, int version) {
        String activity = ((Activity) context).getComponentName().flattenToString();
        StringBuilder columnNames = new StringBuilder();
        Log.i(activity, "Database Version: " + version);
        Log.i(activity, "Number of Columns: " + c.getColumnCount());
        for (int i = 0; i < c.getColumnCount(); i++) {
            columnNames.append(c.getColumnName(i));
            if (i != c.getColumnCount()) {
                columnNames.append(", ");
            }
        }
        Log.i(activity, "Columns: " + TextUtils.join(",", c.getColumnNames()));
        Log.i(activity, "Number of Results: " + c.getCount());
        Log.i(activity, "Results:\n");
        while (c.moveToNext()) {
            StringBuilder resultRow = new StringBuilder();
            for (int i = 0; i < c.getColumnCount(); i++) {
                resultRow
                    .append(c.getColumnName(i))
                    .append(": ")
                    .append(c.getString(i))
                    .append("; ");
            }
            Log.i(activity, resultRow.toString());
        }
    }
}
