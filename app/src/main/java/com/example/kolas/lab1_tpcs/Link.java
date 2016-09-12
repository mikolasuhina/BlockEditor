package com.example.kolas.lab1_tpcs;

import java.util.ArrayList;

/**
 * Created by kolas on 09.09.16.
 */
public class Link {
    ArrayList<SimpleArrow> arrows;
    int id_from,id_to;
    PointLink first_point;

    public Link(int id_from, boolean f_point) {
        this.id_from = id_from;
        this.f_point = f_point;
    }

    public boolean isF_point() {

        return f_point;
    }

    public void setF_point(boolean f_point) {
        this.f_point = f_point;
    }

    boolean f_point;


    public PointLink getFirst_point() {
        return first_point;
    }

    public void setFirst_point(PointLink first_point) {
        this.first_point = first_point;
    }

    public PointLink getSecond_point() {
        return second_point;
    }

    public void setSecond_point(PointLink second_point) {
        this.second_point = second_point;
    }



    PointLink second_point;

    public int getId_from() {
        return id_from;
    }

    public void setId_from(int id_from) {
        this.id_from = id_from;
    }

    public int getId_to() {
        return id_to;
    }

    public void setId_to(int id_to) {
        this.id_to = id_to;
    }

    public Link(int id_from) {
        this.id_from = id_from;
    }

    public ArrayList<SimpleArrow> getArrows() {

        return arrows;
    }

    public void setArrows(ArrayList<SimpleArrow> arrows) {
        this.arrows = arrows;
    }

    public Link(ArrayList<SimpleArrow> arrows) {

        this.arrows = arrows;
    }

    public Link(int id_from, int id_to, ArrayList<SimpleArrow>arrows) {

        this.id_from = id_from;
        this.id_to = id_to;
        this.arrows=arrows;

    }
}
