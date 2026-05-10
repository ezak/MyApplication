package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.database.model.Category;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Category category);

    @Query("SELECT * FROM categories")
    Flowable<List<Category>> observeAllCategories();

    @Query("SELECT * FROM categories WHERE parent_id = :parent")
    Flowable<List<Category>> observeAllSubCategories(int parent);

    @Query("SELECT * FROM categories WHERE parent_id = (SELECT id FROM categories WHERE name = :name LIMIT 1)")
    Flowable<List<Category>> observeAllSubCategories(String name);
}
