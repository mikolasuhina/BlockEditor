package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

import java.util.ArrayList;


/**
 * Created by kolas on 09.09.16.
 */
public class BlocObj {
    private int id;
    private float x,y;
    private  int width,height;

    public PointLink getIn_Point() {
        return in_Point;
    }

    public void setIn_Point(PointLink in_Point) {
        this.in_Point = in_Point;
    }

    public PointLink getOut_Point() {
        return out_Point;
    }

    public void setOut_Point(PointLink out_Point) {
        this.out_Point = out_Point;
    }

      private PointLink in_Point;
      private PointLink out_Point;





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }





    public BlocObj(float x, float y, int width, int height) {
        this.x = x;
        in_Point=new PointLink();
        out_Point=new PointLink();
        this.y = y;
        this.width = width;
        this.height = height;


    }





}
