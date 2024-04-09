package com.mkm.aiphoto_admobmediation.Model;

import android.graphics.drawable.Drawable;

public class model_share {
    private Drawable image;
    private String name;

    public model_share(Drawable image, String name) {
        this.image = image;
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}