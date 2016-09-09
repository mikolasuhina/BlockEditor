package com.example.kolas.lab1_tpcs;

/**
 * Created by kolas on 09.09.16.
 */
public class Link {
    int id_from,id_to;

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

    public Link(int id_from, int id_to) {

        this.id_from = id_from;
        this.id_to = id_to;
    }
}
