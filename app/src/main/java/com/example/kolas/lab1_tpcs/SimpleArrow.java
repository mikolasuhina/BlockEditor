package com.example.kolas.lab1_tpcs;

/**
 * Created by kolas on 09.09.16.
 */
public class SimpleArrow
{
    float x_from,x_to,y_from,y_to;

    public float getX_from() {
        return x_from;
    }

    public void setX_from(float x_from) {
        this.x_from = x_from;
    }

    public float getX_to() {
        return x_to;
    }

    public void setX_to(float x_to) {
        this.x_to = x_to;
    }

    public float getY_from() {
        return y_from;
    }

    public void setY_from(float y_from) {
        this.y_from = y_from;
    }

    public float getY_to() {
        return y_to;
    }

    public void setY_to(float y_to) {
        this.y_to = y_to;
    }

    public SimpleArrow(float x_from, float x_to, float y_from, float y_to) {

        this.x_from = x_from;
        this.x_to = x_to;
        this.y_from = y_from;
        this.y_to = y_to;
    }

    public SimpleArrow() {
    }
}
