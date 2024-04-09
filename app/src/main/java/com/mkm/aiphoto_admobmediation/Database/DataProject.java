package com.mkm.aiphoto_admobmediation.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Model_project.class},version = 2)
public abstract class DataProject extends RoomDatabase {
    public static final String database_name = "table_project";
    public static DataProject INSTANCE;

    public static synchronized DataProject getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataProject.class, database_name)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
    public abstract InterfaceProject daoSql();
}
