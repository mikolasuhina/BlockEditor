package com.flowcharts.kolas.labs_tpcs.blocks;

import com.flowcharts.kolas.labs_tpcs.PointConnect;


/**
 * Created by kolas on 09.09.16.
 */
public class BlockObj {
    private int id;
    private float x, y;
    private int width, height;
    int color;
    String text;
    String text_state;
    boolean delete;

    public String getText_state() {
        return text_state;
    }

    public void setText_state(String text_state) {
        this.text_state = text_state;
    }

    BlockTypes type;
    private PointConnect in_Point;
    private PointConnect out_Point;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BlockObj(float x, float y, int width, int height, int color, BlockTypes type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
        in_Point = new PointConnect(getX() + getWidth() / 2, getY(), true);
        in_Point.setUse(false);
        out_Point = new PointConnect(getX() + getWidth() / 2, getY() + getHeight(), false);
        out_Point.setUse(false);


    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public BlockTypes getType() {
        return type;
    }

    public void setType(BlockTypes type) {
        this.type = type;
    }

    public BlockObj(BlockTypes type) {

        this.type = type;
    }

    public PointConnect getIn_Point() {
        getInLink();

        return in_Point;
    }

    public void setIn_Point(PointConnect in_Point) {
        this.in_Point = in_Point;
    }

    public PointConnect getOut_Point() {

        getOutLink();
        return out_Point;
    }

    public void setOut_Point(PointConnect out_Point) {
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


    private void getInLink() {
        in_Point.setX(getX() + getWidth() / 2);
        in_Point.setY(getY() - getHeight() / 4);

    }

    private void getOutLink() {
        out_Point.setX(getX() + getWidth() / 2);
        out_Point.setY(getY() + getHeight() + getHeight() / 4);

    }


}






