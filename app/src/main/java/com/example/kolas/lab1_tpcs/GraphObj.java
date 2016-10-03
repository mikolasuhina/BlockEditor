package com.example.kolas.lab1_tpcs;

/**
 * Created by mikola on 02.10.2016.
 */

public class GraphObj {

    int id;
    float radius;
    float center_x;
    float center_y;
    String top_text;
    String bottom_text;

    public GraphObj(int id, String top_text, String bottom_text) {
        this.id = id;
        this.top_text = top_text;
        this.bottom_text = bottom_text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getCenter_x() {
        return center_x;
    }

    public void setCenter_x(float center_x) {
        this.center_x = center_x;
    }

    public float getCenter_y() {
        return center_y;
    }

    public void setCenter_y(float center_y) {
        this.center_y = center_y;
    }

    public String getTop_text() {
        return top_text;
    }

    public void setTop_text(String top_text) {
        this.top_text = top_text;
    }

    public String getBottom_text() {
        return bottom_text;
    }

    public void setBottom_text(String bottom_text) {
        this.bottom_text = bottom_text;
    }

    public GraphObj(int id, float radius, float center_x, float center_y, String top_text, String bottom_text) {

        this.id = id;
        this.radius = radius;
        this.center_x = center_x;
        this.center_y = center_y;
        this.top_text = top_text;
        this.bottom_text = bottom_text;
    }
}
