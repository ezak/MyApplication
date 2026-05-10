package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.Category;

@Dao
public interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Category category);

}
