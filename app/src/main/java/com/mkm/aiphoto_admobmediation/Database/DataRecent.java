package com.mkm.aiphoto_admobmediation.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mkm.aiphoto_admobmediation.Model.Model_recent;

@Database(entities = {Model_recent.class},version = 1)
public abstract class DataRecent extends RoomDatabase {

    public static final String database_name = "table_recent";
    public static DataRecent INSTANCE;

    public static synchronized DataRecent getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataRecent.class, database_name)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
    public abstract InterfaceRecent daoSql();

}