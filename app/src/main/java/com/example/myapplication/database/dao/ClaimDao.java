package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.database.model.Claim;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ClaimDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(Claim claim);

    @Update
    Single<Integer> update(Claim claim);

    @Delete
    Single<Integer> delete(Claim claim);

    @Query("SELECT * FROM claims ORDER BY is_synced DESC")
    Single<List<Claim>> getAllClaims();

    @Query("SELECT * FROM claims WHERE id = :id")
    Flowable<Claim> getClaimById(int id);
}
