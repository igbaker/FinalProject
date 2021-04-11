package com.example.finalproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;
    public static final String TABLE_NAME = "FAVOURITES";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_LINK = "LINK";
    public static final String COL_ID = "_id";
    protected static final String DATABASE_NAME = "FinalProject";
    protected static final int VERSION_NUM = 1;

    //gets instance of database or creates new if there isn't one available
    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " text, "
                + COL_LINK + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
