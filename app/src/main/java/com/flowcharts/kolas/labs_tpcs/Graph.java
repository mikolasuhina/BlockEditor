package com.flowcharts.kolas.labs_tpcs;

import java.io.Serializable;

/**
 * Created by mikola on 12.10.2016.
 */

public class Graph implements Serializable {
    public Graph(String[][] allGraphLinks, GraphObj[] graphObjs) {
        this.allGraphLinks = allGraphLinks;
        mGraphObjs = graphObjs;

    }

    String [][]  allGraphLinks;
    GraphObj [] mGraphObjs;




}
