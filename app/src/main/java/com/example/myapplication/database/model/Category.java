package com.example.myapplication.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "categories",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "parent_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("parent_id")}
)
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "is_active")
    private boolean isActive;

    // New field: If null (or 0), this is a top-level category
    @ColumnInfo(name = "parent_id")
    private Integer parentId;

    public Category(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
        this.isActive = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
}
