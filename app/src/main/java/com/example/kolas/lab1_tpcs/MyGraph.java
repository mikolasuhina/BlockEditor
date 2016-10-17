package com.example.kolas.lab1_tpcs;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by mikola on 12.10.2016.
 */

public class MyGraph implements Serializable {
    public MyGraph(String[][] allGraphLinks, GraphObj[] graphObjs) {
        this.allGraphLinks = allGraphLinks;
        mGraphObjs = graphObjs;

    }

    String [][]  allGraphLinks;
    GraphObj [] mGraphObjs;




}
