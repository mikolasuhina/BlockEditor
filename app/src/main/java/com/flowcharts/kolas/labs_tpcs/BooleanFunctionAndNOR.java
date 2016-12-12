package com.flowcharts.kolas.labs_tpcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mikola on 12.12.2016.
 */

public class BooleanFunctionAndNOR {
    String data;
    ArrayList<String> functions;

    private final int AND = 3;
    private final int NOR = 2;

    public BooleanFunctionAndNOR(String data) {
        this.data = data;
        functions = new ArrayList<>();
        Collections.addAll(functions, data.split(";"));
    }


    String generate3And2NorView() {
        String result = new String();

        for (int i = 0; i < functions.size(); i++) {

            String Func = functions.get(i).substring(0, functions.get(i).indexOf("=") + 1);
            String OR = functions.get(i).substring(functions.get(i).indexOf("=") + 1).replace(" ", "");
            functions.set(i, Func + "not(" + normalizeOR(OR) + ");");
        }

        return result;
    }

    String normalizeOR(String or) {
        String res = new String();
        ArrayList<String> orList = new ArrayList<>();
        Collections.addAll(orList, or.split(String.valueOf(Minimization.V_CHAR)));
        for (int j = 0; j < orList.size(); j++) {
            String AND = orList.get(j);
            AND = new String(cropString(AND));
            String resAND = normalizeAND(AND);
            if (j % NOR == 0) {
                if (j == orList.size() - 1)
                    res += resAND;
                else
                    res += "(" + resAND + Minimization.V_CHAR;
            }
            if (j % NOR == 1) {
                if (j == orList.size() - 1)
                    res += resAND + ")";
                else
                    res += resAND + ")" + Minimization.V_CHAR;
            }

        }
        return res;
    }

    String normalizeAND(String and) {

        ArrayList<String> andList = new ArrayList<>();
        Collections.addAll(andList, and.split(String.valueOf(Minimization.AND_CHAR)));
        String res = new String();
        for (int j = 0; j < andList.size(); j++) {
            String var = andList.get(j);
            if (j % AND == 0)
                if (j == andList.size() - 1)
                    res += var;
                else
                    res += "(" + var + Minimization.AND_CHAR;

            if (j % AND == 1) {
                if (j == andList.size() - 1)
                    res += var + ")";
                else
                    res += var + Minimization.AND_CHAR;
            }
            if (j % AND == 2)
                if (j == andList.size() - 1)
                    res += var + ")";
                else
                    res += var + ")" + Minimization.AND_CHAR;


        }
        return packString(res);
    }

    String cropString(String s) {
        return s.substring(1, s.length() - 1);
    }

    String packString(String s) {
        return "(" + s + ")";
    }

    String deleteNot(String s) {
        if (s.contains("not("))
            return s.substring(s.indexOf("not(") + 4, s.lastIndexOf(")"));
        else return s;
    }


    String generetaVHDL(String in) {
        String res = new String();


        return res;

    }
}
