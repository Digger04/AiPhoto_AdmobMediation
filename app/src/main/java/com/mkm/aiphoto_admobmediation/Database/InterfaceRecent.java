package com.mkm.aiphoto_admobmediation.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mkm.aiphoto_admobmediation.Model.Model_recent;

import java.util.List;

@Dao
public interface InterfaceRecent {

    @Query("SELECT * FROM recent")
    List<Model_recent> getall();

    @Insert
    void insert (Model_recent history);

    @Delete
    void delete (Model_recent history);

}
