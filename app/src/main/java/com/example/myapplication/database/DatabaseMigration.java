package com.example.myapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class DatabaseMigration
{
    private static final String TAG = "DatabaseMigration";

    static void populateInitialData(@NonNull SupportSQLiteDatabase db) {
        db.beginTransaction();
        try {
            // --- Level 1: Top Categories ---
            // We use IDs 1-10 for the main navigation items seen in screenshot 1
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (1, 'Social Protection', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (2, 'Dashboards', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (3, 'Insurees and Policies', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (4, 'Claims', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (5, 'Administration', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (6, 'Tools', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (7, 'Profile', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (8, 'Tasks Management', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (9, 'Legal and Finance', 1, NULL);");
            db.execSQL("INSERT INTO categories (id, name, is_active, parent_id) VALUES (10, 'Grievance', 1, NULL);");

            // --- Level 2: Sub-categories for 'Social Protection' (Parent ID = 1) ---
            // Based on Screenshot 2
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Benefit Plans', 1, 1);");
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Individuals', 1, 1);");
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Groups', 1, 1);");
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Import Data - API', 1, 1);");

            // --- Level 2: Sub-categories for 'Claims' (Parent ID = 4) ---
            // Based on Screenshot 3
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Health Facility Claims', 1, 4);");
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Reviews', 1, 4);");
            db.execSQL("INSERT INTO categories (name, is_active, parent_id) VALUES ('Batch Runs', 1, 4);");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    static Migration[] getMigrations(@NonNull Context appContext /* not used */)
    {
        return new Migration[] {
            MIGRATION_1_2
        };
    }

    static final Migration MIGRATION_0_1 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database)
        {

        }
    };

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Creating the 'claims' table
            database.execSQL("CREATE TABLE IF NOT EXISTS `claims` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`policy_number` TEXT, " +
                    "`claim_amount` REAL NOT NULL, " +
                    "`status` TEXT, " +
                    "`is_synced` INTEGER NOT NULL DEFAULT 0)");
        }
    };
}