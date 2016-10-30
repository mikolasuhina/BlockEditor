package com.flowcharts.kolas.labs_tpcs.blocks;

import com.flowcharts.kolas.labs_tpcs.PointConnect;

import java.util.ArrayList;

/**
 * Created by kolas on 10.09.16.
 */
public class RhombBlock extends BlockObj {

    ArrayList<PointConnect> outPoints;
    private PointConnect pointL;
    private PointConnect pointR;

    public PointConnect getPointB() {
        return pointB;
    }

    public void setPointB(PointConnect pointB) {
        this.pointB = pointB;
    }

    public static int getFREE() {
        return FREE;
    }

    private PointConnect pointB;

     private int first;
     public static final int FREE=100;

    private int second;
    int nuberPointLink;

    public int getNuberPointLink() {
        return nuberPointLink;
    }

    public void setNuberPointLink(int nuberPointLink) {
        this.nuberPointLink = nuberPointLink;
    }

    public PointConnect getPointL() {

        return pointL;
    }

    public void setPointL(PointConnect pointL) {
        this.pointL = pointL;
    }

    public PointConnect getPointR() {

        return pointR;
    }


    private void updateL() {
        pointR.setX(getX() - getHeight() / 4);
        pointR.setY((float) (getY() + getHeight() / 2));
    }
    private void updateB() {
        pointB.setX(getX()  + getHeight()/2);
        pointB.setY((float) (getY() + getHeight()+ getHeight() / 4));
    }

    private void updateR() {
        pointL.setX(getX() + getWidth() + getHeight() / 4);
        pointL.setY((float) (getY() + getHeight() / 2));
    }

    public void setPointR(PointConnect pointR) {
        this.pointR = pointR;
    }

    public ArrayList<PointConnect> getOutPoints() {
        update();
        return outPoints;
    }

    public void setOutPoints(ArrayList<PointConnect> outPoints) {
        this.outPoints = outPoints;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public RhombBlock(float x, float y, int width, int height, int color, BlockTypes type) {
        super(x, y, width, height, color, type);
        pointR = new PointConnect(x + height / 4, (float) (y + height / 2), false);
        pointL = new PointConnect(x - width / 4, (float) (y + height / 2), false);
        pointB = new PointConnect(x + width /2, (float) (y + height +height/4), false);
        outPoints = new ArrayList<>();
        outPoints.add(pointB);
        outPoints.add(pointL);
        outPoints.add(pointR);
        first = FREE;
        second = FREE;
        setText("X");

    }

    @Override
    public PointConnect getOut_Point() {
        update();
        return outPoints.get(nuberPointLink);

    }


    private void update() {
        updateB();
        updateL();
        updateR();


    }
}
