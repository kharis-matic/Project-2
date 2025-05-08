package com.example.ulive;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "ulive.db";

    





    // Instance of this DatabaseHelper object
    private static DatabaseHelper INSTANCE;

    /**
     * Private constructor to ensure it follows Singleton pattern
     * */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Instantiates a new DatabaseHelper object if it does not exist.
     * @param context
     * @return Instance of the DatabaseHelper class.
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    /**
     * Called when the database is created for the first time
     * */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
