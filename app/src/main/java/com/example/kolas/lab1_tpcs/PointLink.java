package com.example.kolas.lab1_tpcs;

/**
 * Created by kolas on 10.09.16.
 */
public class PointLink {
    private float x;
    private float y;
    boolean type_for_rhomh;

    public boolean isType_for_rhomh() {
        return type_for_rhomh;
    }

    public void setType_for_rhomh(boolean type_for_rhomh) {
        this.type_for_rhomh = type_for_rhomh;
    }

    private boolean in_out_type;//true-in;false -out

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    private boolean use;

    public PointLink() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public PointLink(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PointLink(float x, float y, boolean in_out_type) {
        this.x = x;
        this.y = y;
        this.in_out_type = in_out_type;
    }

    public boolean isIn_out_type() {

        return in_out_type;
    }

    public void setIn_out_type(boolean in_out_type) {
        this.in_out_type = in_out_type;
    }
}
