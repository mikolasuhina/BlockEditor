package com.example.kolas.lab1_tpcs;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by mikola on 12.10.2016.
 */

public class MyGraph implements Serializable {
    HashMap<Integer, GraphObj> allGraphsObjs;
    HashMap<Integer, GraphLink> allGraphLinks;

    public MyGraph(HashMap<Integer, GraphObj> allGraphsObjs, HashMap<Integer, GraphLink> allGraphLinks) {
        this.allGraphsObjs = allGraphsObjs;
        this.allGraphLinks = allGraphLinks;
    }

}
