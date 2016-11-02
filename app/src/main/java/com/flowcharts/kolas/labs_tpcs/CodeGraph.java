package com.flowcharts.kolas.labs_tpcs;

import android.content.Intent;

import com.flowcharts.kolas.labs_tpcs.blocks.BlockObj;
import com.flowcharts.kolas.labs_tpcs.blocks.BlockTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.flowcharts.kolas.labs_tpcs.Model.centers;

/**
 * Created by mikola on 31.10.2016.
 */

public class CodeGraph {
    String[][] matrixLGraph;
    BlockObj[] blockObjcts;
    GraphObj[] graphObjcts;
    public static final String RESTART = "restart";
    public static final String SUCCESSFUL = "successful";
    int beginIndex;
    int count;

    public CodeGraph(String[][] matrixLGraph, BlockObj[] blockObjcts, GraphObj[] graphObjcts) {
        this.matrixLGraph = matrixLGraph;
        this.graphObjcts = graphObjcts;
        this.blockObjcts = blockObjcts;
        count = graphObjcts.length;
        beginIndex = 0;
        for (int i = 0; i < count; i++) {
            if (blockObjcts[i].getType() == BlockTypes.BEGIN) {
                beginIndex = i;
                break;
            }
        }
        coddingGraph((int) (Math.log(count) / Math.log(2)) + 1);
    }

    ArrayList<String> allcode = new ArrayList<>();
    List<GraphObj> addingGraphObj = new ArrayList<>();
    String beginCode;

    public String[][] getMatrixLGraph() {
        return matrixLGraph;
    }

    public void setMatrixLGraph(String[][] matrixLGraph) {
        this.matrixLGraph = matrixLGraph;
    }

    public GraphObj[] getGraphObjcts() {
        return graphObjcts;
    }

    public void setGraphObjcts(GraphObj[] graphObjcts) {
        this.graphObjcts = graphObjcts;
    }

    void coddingGraph(int size) {

        int cellSize = size;
        beginCode = "";
        for (int i = 0; i < cellSize; i++) {
            beginCode += '0';
        }
        graphObjcts[beginIndex].setCode(beginCode);
        allcode.add(beginCode);

        String res = codding(beginIndex, beginCode);
        if (res.equals(RESTART))
            restart(size + 1);
        else {

            for (int i = 0; i < count; i++) {
                if (!matrixLGraph[i][beginIndex].equals(Model.NULL)) {
                    if (!isCorrectCodes(beginCode, graphObjcts[i].getCode())) {
                        String curentCode = new String(graphObjcts[i].getCode());
                        matrixLGraph[i][beginIndex]=Model.NULL;
                        addingNewGrapfObj(curentCode, beginCode, graphObjcts[i].top_text, i);
                    }
                }
            }
            GraphObj[] newGraphObjtc = new GraphObj[allcode.size()];
            for (int i = 0; i < graphObjcts.length; i++) {
                newGraphObjtc[i] = graphObjcts[i];
            }
            for (int i = 0; i < addingGraphObj.size(); i++) {
                newGraphObjtc[i + graphObjcts.length] = addingGraphObj.get(i);
            }
            graphObjcts = newGraphObjtc.clone();

            int radius = 300;
            float x;
            float y;
            int count = allcode.size();


            for (int i = 0; i < allcode.size(); i++) {
                y = ((float) (centers + radius * Math.sin((2 * Math.PI / count) * i)));
                x = ((float) (centers + radius * Math.cos((2 * Math.PI / count) * i)));
                newGraphObjtc[i].setCenter_x(x);
                newGraphObjtc[i].setCenter_y(y);
                newGraphObjtc[i].setRadius(50);
                if (newGraphObjtc[i].getCode() == null)
                    newGraphObjtc[i].setCode(allcode.get(i));
                newGraphObjtc[i].setAngle((float) ((2 * Math.PI / count) * i));

            }

        }
    }

    private String[][] addNewRowToMatrix(int from, int to) {
        int newLenght = matrixLGraph.length + 1;
        String[][] newgraphMatrixL = new String[newLenght][newLenght];
        //копія старої матриці
        for (int i = 0; i < matrixLGraph.length; i++) {
            for (int j = 0; j < matrixLGraph.length; j++) {
                newgraphMatrixL[i][j] = matrixLGraph[i][j];
            }
        }
        //додвання стопчика
        for (int i = 0; i < newLenght; i++) {
            if (i == from)
                newgraphMatrixL[i][to] = Model.ONE;

            else
                newgraphMatrixL[i][to] = Model.NULL;

        }
        //додавання рядка
        for (int i = 0; i < newLenght; i++) {
            newgraphMatrixL[to][i] = Model.NULL;
        }
        return newgraphMatrixL;
    }

    private void addingNewGrapfObj(String curentCode, String codeEnd, String topText, int from) {
        String mCurentCode = curentCode;
        String mEndCode = codeEnd;
        //отримую всі можливі коди
        ArrayList<String> listOfCodes = new ArrayList<String>();
        for (int j = 0; j < mCurentCode.length(); j++) {
            if (mCurentCode.charAt(j) == '1')
                listOfCodes.add(replaceChar(mCurentCode, j));
        }
        //максимальний id
        int max = getMaxId(graphObjcts);
        //попередня довжина всіх кодів
        int preCount = allcode.size();
        for (int j = 0; j < listOfCodes.size(); j++) {
            //якщо не містить, додаєм новий код і виходим
            if (!allcode.contains(listOfCodes.get(j))) {
                String tmp = listOfCodes.get(j);
                allcode.add(tmp);
                addingGraphObj.add(new GraphObj(max + 1, topText += "'", Model.NULL));
                matrixLGraph = addNewRowToMatrix(from,matrixLGraph.length).clone();
                if (isCorrectCodes(tmp, mEndCode)){
                    matrixLGraph[matrixLGraph.length-1][beginIndex]=Model.ONE;
                    return;
                }
                else addingNewGrapfObj(tmp, codeEnd, topText, from);
                break;
            }
        }
        //якщо нічого не додано, додаєм нову вершину і збільшуєм розрядність коду на 1
        if (allcode.size() == preCount) {
            addCharOnewToEndCode();
            allcode.add(mCurentCode + "1");
            addingGraphObj.add(new GraphObj(max + 1, topText + "'", Model.NULL));
            addingNewGrapfObj(mCurentCode + "1", codeEnd, topText + "'", matrixLGraph.length - 1);
        }

    }

    private void addCharOnewToEndCode() {
        for (int i = 0; i < allcode.size(); i++) {
            allcode.set(i, allcode.get(i) + '0');
            graphObjcts[i].setCode(allcode.get(i));
        }

    }

    private int getMaxId(GraphObj[] mgraphObjcts) {
        int max = Integer.MIN_VALUE;
        for (GraphObj obj : mgraphObjcts) {
            if (obj.getId() > max)
                max = obj.getId();

        }
        return max;
    }

    private void restart(int newSize) {

        allcode.clear();
        for (int i = 0; i < graphObjcts.length; i++) {
            graphObjcts[i].setCode(null);
        }
        coddingGraph(newSize);


    }


    private boolean isCorrectCodes(String codeBegin, String parentCode) {
        int lenght = codeBegin.length();
        int countDifChar = 0;
        for (int i = 0; i < lenght; i++) {
            if (codeBegin.charAt(i) != parentCode.charAt(i))
                countDifChar++;
        }
        if (countDifChar == 1)
            return true;
        else return false;
    }

    private String codding(int index, String curentCode) {
        String code = curentCode;
        if (index != beginIndex || allcode.size() == 1) {
            for (int i = 0; i < count; i++) {
                if (!matrixLGraph[index][i].equals(Model.NULL) && graphObjcts[i].getCode() == null) {
                    int tmpCount = allcode.size();
                    String tmpCode = null;
                    //----------------------------
                    //визначаю код для блока
                    for (int j = 0; j < code.length(); j++) {
                        if (!allcode.contains(replaceChar(code, j))) {
                            tmpCode = replaceChar(code, j);
                            graphObjcts[i].setCode(tmpCode);
                            allcode.add(tmpCode);
                            break;
                        }
                    }
                    //-----------------------------
                    if (allcode.size() == tmpCount)
                        return RESTART;

                    if (codding(i, tmpCode).equals(RESTART))
                        return RESTART;

                }
            }
        }

        return SUCCESSFUL;

    }

    private String replaceChar(String code, int index) {
        char[] array = code.toCharArray();
        String res = code;
        char c = res.charAt(index);
        if (c == '1')
            c = '0';
        else c = '1';
        array[index] = c;
        res = "";
        for (int i = 0; i < array.length; i++) {
            res += array[i];
        }
        return res;

    }
}
