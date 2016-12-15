package com.flowcharts.kolas.labs_tpcs;

/**
 * Created by mikola on 13.12.2016.
 */

public class Signal {
    String KEY;
    String VALUE;

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public Signal(String KEY, String VALUE) {

        this.KEY = KEY;
        this.VALUE = VALUE;
    }
}
