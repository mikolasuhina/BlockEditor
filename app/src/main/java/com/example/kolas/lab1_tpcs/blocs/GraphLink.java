package com.example.kolas.lab1_tpcs.blocs;

/**
 * Created by mikola on 02.10.2016.
 */

public class GraphLink {
    int id;
    int id_from;
    int id_to;
    String text;
    boolean cycle;

    public boolean isCycle() {
        return cycle;
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GraphLink(int id, int id_from, int id_to, String text) {

        this.id = id;
        this.id_from = id_from;
        this.id_to = id_to;
        this.text = text;
    }
}
