package com.example.ulive.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ulive.data.dao.UserDao;
import com.example.ulive.data.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public static AppDatabase getDatabase(final Context context) {
        // first lock-check, avoid unneccesary lock if instance is still null
        if (INSTANCE == null) {
            // lock to ensure that only one thread can execute this block -- instantiate db connection
            synchronized (AppDatabase.class) {
                // second lock-check, ensures instance is still null after locking. in case two threads passed first check simultaneously
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ulive").build();
                }
            }
        }
        return INSTANCE;
    }
}
