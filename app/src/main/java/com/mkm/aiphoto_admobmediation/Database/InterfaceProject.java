package com.mkm.aiphoto_admobmediation.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InterfaceProject {
    @Query("SELECT * FROM project")
    List<Model_project> getall();

    @Insert
    void insert (Model_project project);

    @Delete
    void delete (Model_project project);
}
