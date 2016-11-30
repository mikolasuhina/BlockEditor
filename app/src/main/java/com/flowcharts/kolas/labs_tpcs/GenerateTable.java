package com.flowcharts.kolas.labs_tpcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by mikola on 16.11.2016.
 */

public class GenerateTable {
    private String matrixLGraph[][];
    private GraphObj[] mGraphObjs;
    private ArrayList<String[]> table = new ArrayList<>();

    private String conditions[];
    private String functions[];

    public static ArrayList<String> tableViewData = new ArrayList<>();
    public final static int columnCount = 6;


    public GenerateTable(String[][] matrixLGraph, GraphObj[] graphObjs) {
        this.matrixLGraph = matrixLGraph;
        mGraphObjs = graphObjs;
        tableViewData.clear();
        getConditionLayout();
        createTable();
    }

    private void createTable() {
        int lenght = mGraphObjs[0].getCode().length();
        String[] rowTable;
        rowTable = new String[columnCount];
        rowTable[0] = "Перехід";
        rowTable[1] = generateLayoutState(lenght, false);
        rowTable[2] = generateLayoutState(lenght, true);
        rowTable[3] = Arrays.toString(conditions);
        rowTable[4] = Arrays.toString(functions);
        rowTable[5] = generateLayoutTrigers(lenght);
        table.add(rowTable);

        for (int i = 0; i < matrixLGraph.length; i++) {
            for (int j = 0; j < matrixLGraph.length; j++) {
                if (!matrixLGraph[i][j].equals(Model.NULL)) {
                    rowTable = new String[columnCount];
                    rowTable[0] = mGraphObjs[i].getTop_text() + " - > " + mGraphObjs[j].getTop_text();
                    rowTable[1] = mGraphObjs[i].getCode();
                    rowTable[2] = mGraphObjs[j].getCode();
                    rowTable[3] = generateStringCondition(matrixLGraph[i][j]);
                    rowTable[4] = generateStringFunctions(mGraphObjs[i].getBottom_text());
                    rowTable[5] = generateStringRSTrigers(mGraphObjs[i].getCode(), mGraphObjs[j].getCode());
                    table.add(rowTable);
                }
            }

        }

        for (String[] row : table) {
            for (int i = 0; i < row.length; i++) {
                tableViewData.add(row[i]);
            }
        }


    }



    private String generateLayoutTrigers(int lenght) {
        String res = "";
        for (int i = 0; i < lenght; i++)
            res += "R" + i + " S" + i+" ";
        return res;
    }

    private String generateStringRSTrigers(String codeFrom, String codeTo) {
        int lenght = codeFrom.length();
        String res = "";
        for (int i = 0; i < lenght; i++) {
            if (codeFrom.charAt(i) == '0' && codeTo.charAt(i) == '0') {
                res += "-0";
            } else if (codeFrom.charAt(i) == '1' && codeTo.charAt(i) == '0') {
                res += "01";
            } else if (codeFrom.charAt(i) == '0' && codeTo.charAt(i) == '1') {
                res += "10";
            } else if (codeFrom.charAt(i) == '1' && codeTo.charAt(i) == '1') {
                res += "0-";
            }
        }
        return res;
    }

    private String generateStringFunctions(String bottom_text) {
        bottom_text.replaceAll("", " ");
        String res = "";
        String tmpCondition;
        ArrayList<String> curConditiond = new ArrayList<>();
        Collections.addAll(curConditiond, bottom_text.split(","));

        for (int j = 0; j < functions.length; j++) {
            for (int k = 0; k < curConditiond.size(); k++) {
                tmpCondition = curConditiond.get(k);
                if (functions[j].equals(tmpCondition)) {
                    res += '1';
                }

            }
            if (res.length() < j + 1)
                res += '0';

        }

        return res;
    }

    private String generateStringCondition(String s) {
        s= new String(s.replaceAll(" ", ""));
        String res = "";
        String tmpCondition;
        ArrayList<String> curConditiond = new ArrayList<>();
        Collections.addAll(curConditiond, s.split(","));

        for (int j = 0; j < conditions.length; j++) {
            for (int k = 0; k < curConditiond.size(); k++) {
                boolean cond;
                if (curConditiond.get(k).contains("(not")) {
                    tmpCondition = curConditiond.get(k).substring(curConditiond.get(k).indexOf("(not") + 4, curConditiond.get(k).indexOf(")"));
                    cond = false;

                } else {
                    tmpCondition = curConditiond.get(k);
                    cond = true;
                }
                if (conditions[j].equals(tmpCondition)) {
                    if (cond)
                        res += '1';
                    else res += '0';
                    break;
                }

            }
            if (res.length() != j + 1)
                res += '-';

        }

        return res;
    }

    private String generateLayoutState(int length, boolean b) {
        String res = "";
        for (int i = 0; i < length; i++) {
            if (!b) {
                res += "Q" + i + " ";
            } else {
                res += "Q" + i + "► ";
            }

        }
        return res;
    }

    public void getConditionLayout() {
        conditions = new String[Model.conditions.size()];
        Model.conditions.toArray(conditions);

        functions = new String[Model.functions.size()];
        Model.functions.toArray(functions);

    }
}
