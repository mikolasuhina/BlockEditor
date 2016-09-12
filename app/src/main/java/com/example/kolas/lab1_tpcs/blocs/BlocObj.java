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
    int color;
    BlocTypes type;
    private PointLink in_Point;
    private PointLink out_Point;

    public BlocObj(float x, float y, int width, int height, int color, BlocTypes type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
        in_Point =new PointLink(getX()+getWidth()/2,getY(),true);
        in_Point.setUse(false);
        out_Point=new PointLink(getX()+getWidth()/2,getY()+getHeight(),false);
        out_Point.setUse(false);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public BlocTypes getType() {
        return type;
    }

    public void setType(BlocTypes type) {
        this.type = type;
    }

    public BlocObj(BlocTypes type) {

        this.type = type;
    }

    public PointLink getIn_Point() {
        getInLink();

        return in_Point;
    }

    public void setIn_Point(PointLink in_Point) {
        this.in_Point = in_Point;
    }

    public PointLink getOut_Point() {

        getOutLink();
        return out_Point;
    }

    public void setOut_Point(PointLink out_Point) {
        this.out_Point = out_Point;
    }







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



   private void getInLink(){
       in_Point.setX(getX()+getWidth()/2);
       in_Point.setY(getY()-getHeight()/4);

   }

    private void getOutLink(){
        out_Point.setX(getX()+getWidth()/2);
        out_Point.setY(getY()+getHeight()+getHeight()/4);

    }


    }






