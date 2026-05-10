package com.example.myapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.database.dao.CategoryDao;
import com.example.myapplication.model.Category;


@Database(entities = {Category.class}, version = 1)


public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "application.db";

    private static volatile AppDatabase INSTANCE;

    public abstract CategoryDao categoriesDao();


    public static AppDatabase getInstance(@NonNull Context appContext)
    {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = buildDatabase(appContext);
            }
        }

        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context appContext)
    {
        return Room.databaseBuilder(appContext.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        // This is called only when the database is created for the first time
                        DatabaseMigration.populateInitialData(db);
                    }
                })
                .addMigrations(DatabaseMigration.getMigrations(appContext))
                .build();
    }
}