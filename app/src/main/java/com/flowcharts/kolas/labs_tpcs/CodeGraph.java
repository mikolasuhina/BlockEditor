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
    public String condition;
    public static final String RESTART = "restart";
    public static final String SUCCESSFUL = "successful";
    int fromIndex;
    int count;

    public CodeGraph(String[][] matrixLGraph, BlockObj[] blockObjcts, GraphObj[] graphObjcts) {
        this.matrixLGraph = matrixLGraph;
        this.graphObjcts = graphObjcts;
        this.blockObjcts = blockObjcts;
        count = graphObjcts.length;
        fromIndex = 0;
        for (int i = 0; i < count; i++) {
            if (blockObjcts[i].getType() == BlockTypes.BEGIN) {
                fromIndex = i;
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
        graphObjcts[fromIndex].setCode(beginCode);
        allcode.add(beginCode);

        String res = codding(fromIndex, beginCode);
        if (res.equals(RESTART))
            restart(size + 1);
        else {
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    if (!matrixLGraph[i][j].equals(Model.NULL)) {
                        if (!isCorrectCodes(graphObjcts[j].getCode(), graphObjcts[i].getCode()) && !graphObjcts[j].getCode().equals(graphObjcts[i].getCode())) {
                            String fromCode = new String(graphObjcts[i].getCode());
                            String toCode = new String(graphObjcts[j].getCode());
                            condition = matrixLGraph[i][j];
                            matrixLGraph[i][j] = Model.NULL;
                            fromIndex = j;
                            addingNewGrapfObj(fromCode, toCode, graphObjcts[i].top_text, i);

                        }
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
            if (i == from) {
                if (isConditioninMatrix(condition))
                    newgraphMatrixL[i][to] = Model.ONE;
                else newgraphMatrixL[i][to] = condition;
            } else
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
            if (mCurentCode.charAt(j) !=mEndCode.charAt(j) )
                listOfCodes.add(replaceChar(mCurentCode, j));
        }
        //максимальний id
        int max = getMaxId(graphObjcts) + addingGraphObj.size();
        //попередня довжина всіх кодів
        int preCount = allcode.size();
        for (int j = 0; j < listOfCodes.size(); j++) {
            //якщо не містить, додаєм новий код і виходим
            if (!allcode.contains(listOfCodes.get(j))) {
                String tmp = listOfCodes.get(j);

                allcode.add(tmp);
                GraphObj tmpGraph = new GraphObj(max + 1, topText += "'", Model.NULL);
                tmpGraph.setCode(tmp);
                addingGraphObj.add(tmpGraph);

                matrixLGraph = addNewRowToMatrix(from, matrixLGraph.length).clone();
                if (isCorrectCodes(tmp, mEndCode)) {
                    matrixLGraph[matrixLGraph.length - 1][fromIndex] = Model.ONE;
                    return;
                } else addingNewGrapfObj(tmp, codeEnd, topText, matrixLGraph.length - 1);
                break;
            }
        }
        //якщо нічого не додано, додаєм нову вершину і збільшуєм розрядність коду на 1
        if (allcode.size() == preCount) {
            mCurentCode += "1";
            topText += "'";
            codeEnd += '0';
            addCharOnewToEndCode();

            allcode.add(mCurentCode);
            GraphObj tmpGraph = new GraphObj(max + 1, topText, Model.NULL);
            tmpGraph.setCode(mCurentCode);
            addingGraphObj.add(tmpGraph);

            matrixLGraph = addNewRowToMatrix(from, matrixLGraph.length).clone();
            addingNewGrapfObj(mCurentCode, codeEnd, topText, matrixLGraph.length - 1);
        }

    }

    private void addCharOnewToEndCode() {
        for (int i = 0; i < allcode.size(); i++) {
            //додавання "0" до уже існуючих
            for (int j = 0; j < graphObjcts.length; j++) {
                if (graphObjcts[j].getCode().equals(allcode.get(i))) {
                    graphObjcts[j].setCode(allcode.get(i) + '0');
                    break;
                }
            }
            //додавання "0" до тих, що будуть додаватись
            for (int j = 0; j < addingGraphObj.size(); j++) {
                if (addingGraphObj.get(j).getCode().equals(allcode.get(i))) {
                    addingGraphObj.get(j).setCode(allcode.get(i) + '0');
                    break;
                }
            }

            allcode.set(i, allcode.get(i) + '0');

        }

    }


    private boolean isConditioninMatrix(String condition) {
        for (int i = 0; i < matrixLGraph.length; i++) {
            for (int j = 0; j < matrixLGraph.length; j++) {
                if (condition.equals(matrixLGraph[i][j]))
                    return true;
            }
        }
        return false;
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


    private boolean isCorrectCodes(String codeFrom, String codeTo) {
        int lenght = codeFrom.length();
        int countDifChar = 0;
        for (int i = 0; i < lenght; i++) {
            if (codeFrom.charAt(i) != codeTo.charAt(i))
                countDifChar++;
        }
        if (countDifChar == 1)
            return true;
        else return false;
    }

    private String codding(int index, String curentCode) {
        String code = curentCode;

        for (int i = 0; i < count; i++) {
            if (!matrixLGraph[index][i].equals(Model.NULL))
                if (graphObjcts[i].getCode() == null) {
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
