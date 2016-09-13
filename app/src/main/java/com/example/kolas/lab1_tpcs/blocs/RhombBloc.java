package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

import java.util.ArrayList;

/**
 * Created by kolas on 10.09.16.
 */
public class RhombBloc extends BlocObj {

    ArrayList<PointLink> outPoints;
    private PointLink pointL;
    private PointLink pointR;
    private int first;


    private int second;
    int nuberPointLink;

    public int getNuberPointLink() {
        return nuberPointLink;
    }

    public void setNuberPointLink(int nuberPointLink) {
        this.nuberPointLink = nuberPointLink;
    }

    public PointLink getPointL() {

        return pointL;
    }

    public void setPointL(PointLink pointL) {
        this.pointL = pointL;
    }

    public PointLink getPointR() {

        return pointR;
    }


    private void updateL() {
        pointR.setX(getX() - getHeight() / 4);
        pointR.setY((float) (getY() + getHeight() / 2));
    }

    private void updateR() {
        pointL.setX(getX() + getWidth() + getHeight() / 4);
        pointL.setY((float) (getY() + getHeight() / 2));
    }

    public void setPointR(PointLink pointR) {
        this.pointR = pointR;
    }

    public ArrayList<PointLink> getOutPoints() {
        update();
        return outPoints;
    }

    public void setOutPoints(ArrayList<PointLink> outPoints) {
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

    public RhombBloc(float x, float y, int width, int height, int color, BlocTypes type) {
        super(x, y, width, height, color, type);
        pointR = new PointLink(x + height / 4, (float) (y + height / 2), false);
        pointL = new PointLink(x - width / 4, (float) (y + height / 2), false);
        outPoints = new ArrayList<>();
        outPoints.add(super.getOut_Point());
        outPoints.add(pointL);
        outPoints.add(pointR);
        first = 100;
        second = 100;
        setText("X");

    }

    @Override
    public PointLink getOut_Point() {
        update();
        return outPoints.get(nuberPointLink);

    }


    private void update() {
        super.getOut_Point();
        updateL();
        updateR();


    }
}
