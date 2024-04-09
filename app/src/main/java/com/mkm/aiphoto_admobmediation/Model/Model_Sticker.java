package com.mkm.aiphoto_admobmediation.Model;

public class Model_Sticker {
    private String path;
    private String stype;
    private boolean Default;

    public Model_Sticker(String path, String stype, boolean aDefault) {
        this.path = path;
        this.stype = stype;
        Default = aDefault;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public boolean isDefault() {
        return Default;
    }

    public void setDefault(boolean aDefault) {
        Default = aDefault;
    }
}