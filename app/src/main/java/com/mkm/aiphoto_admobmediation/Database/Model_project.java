package com.mkm.aiphoto_admobmediation.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project")
public class Model_project {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String path;
    private String style;
    private String days;
    private String size;
    private String polishView;
    private String gridView;
    private String path1;
    private String path2;
    private String path3;
    private String path4;
    private String path5;
    private String path6;
    private String path7;
    private String path8;
    private String path9;
    private String text;
    private int number_layout;
    private int number_filter;
    private int number_color;
    private int number_border;
    private int number_radius;
    private int number_gradient;
    private int number_ratio;
    private int number_tabsticker;
    private int number_sticker;

    public Model_project(String path, String style, String days, String size, String polishView, String gridView, String path1, String path2, String path3, String path4, String path5, String path6, String path7, String path8, String path9, String text, int number_layout, int number_filter, int number_color, int number_border, int number_radius, int number_gradient, int number_ratio, int number_tabsticker, int number_sticker) {
        this.path = path;
        this.style = style;
        this.days = days;
        this.size = size;
        this.polishView = polishView;
        this.gridView = gridView;
        this.path1 = path1;
        this.path2 = path2;
        this.path3 = path3;
        this.path4 = path4;
        this.path5 = path5;
        this.path6 = path6;
        this.path7 = path7;
        this.path8 = path8;
        this.path9 = path9;
        this.text = text;
        this.number_layout = number_layout;
        this.number_filter = number_filter;
        this.number_color = number_color;
        this.number_border = number_border;
        this.number_radius = number_radius;
        this.number_gradient = number_gradient;
        this.number_ratio = number_ratio;
        this.number_tabsticker = number_tabsticker;
        this.number_sticker = number_sticker;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPolishView() {
        return polishView;
    }

    public void setPolishView(String polishView) {
        this.polishView = polishView;
    }

    public String getGridView() {
        return gridView;
    }

    public void setGridView(String gridView) {
        this.gridView = gridView;
    }

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }

    public String getPath3() {
        return path3;
    }

    public void setPath3(String path3) {
        this.path3 = path3;
    }

    public String getPath4() {
        return path4;
    }

    public void setPath4(String path4) {
        this.path4 = path4;
    }

    public String getPath5() {
        return path5;
    }

    public void setPath5(String path5) {
        this.path5 = path5;
    }

    public String getPath6() {
        return path6;
    }

    public void setPath6(String path6) {
        this.path6 = path6;
    }

    public String getPath7() {
        return path7;
    }

    public void setPath7(String path7) {
        this.path7 = path7;
    }

    public String getPath8() {
        return path8;
    }

    public void setPath8(String path8) {
        this.path8 = path8;
    }

    public String getPath9() {
        return path9;
    }

    public void setPath9(String path9) {
        this.path9 = path9;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber_layout() {
        return number_layout;
    }

    public void setNumber_layout(int number_layout) {
        this.number_layout = number_layout;
    }

    public int getNumber_filter() {
        return number_filter;
    }

    public void setNumber_filter(int number_filter) {
        this.number_filter = number_filter;
    }

    public int getNumber_color() {
        return number_color;
    }

    public void setNumber_color(int number_color) {
        this.number_color = number_color;
    }

    public int getNumber_border() {
        return number_border;
    }

    public void setNumber_border(int number_border) {
        this.number_border = number_border;
    }

    public int getNumber_radius() {
        return number_radius;
    }

    public void setNumber_radius(int number_radius) {
        this.number_radius = number_radius;
    }

    public int getNumber_gradient() {
        return number_gradient;
    }

    public void setNumber_gradient(int number_gradient) {
        this.number_gradient = number_gradient;
    }

    public int getNumber_ratio() {
        return number_ratio;
    }

    public void setNumber_ratio(int number_ratio) {
        this.number_ratio = number_ratio;
    }

    public int getNumber_tabsticker() {
        return number_tabsticker;
    }

    public void setNumber_tabsticker(int number_tabsticker) {
        this.number_tabsticker = number_tabsticker;
    }

    public int getNumber_sticker() {
        return number_sticker;
    }

    public void setNumber_sticker(int number_sticker) {
        this.number_sticker = number_sticker;
    }

}
