package com.mkm.aiphoto_admobmediation.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Recent")
public class Model_recent {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String path;
    private long currentime;

    public Model_recent(String path, long currentime) {
        this.path = path;
        this.currentime = currentime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getCurrentime() {
        return currentime;
    }

    public void setCurrentime(long currentime) {
        this.currentime = currentime;
    }
}