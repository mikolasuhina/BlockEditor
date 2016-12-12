package com.flowcharts.kolas.labs_tpcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by mikola on 27.11.2016.
 */

public class Minimization {
    private int lenghtFunctions;
    private int lenghtTrigers;
    private int rowCount;
    private int conditionsFunctionLenght;
    private int lenghtConditional;

    public static final int Q_COLUMN = 1;
    public static final int CONDITION_COLUMN = 3;
    private static final int FUNCTION_COLUMN = 4;
    public static final int TRIGER_COLUMN = 5;
    public static final char X_CHAR = 'x';
    public static final char ONE_CHAR = '1';
    public static final char V_CHAR = '▼';
    public static final char AND_CHAR = '•';
    public static final String DELETED = "deleted";
    int position;


    ArrayList<String> templateConditionsFunction = new ArrayList<>();

    String[] templateFunctions;
    String[] templateTrigers;
    ArrayList<String> firstTable[];//конституенти 1
    ArrayList<String> secondTable;//склеювання сусідніх груп
    ArrayList<ArrayList<String>> nextTables; //склеювання в межах групи

    ArrayList<String[]> table = new ArrayList<>();
    ArrayList<String []> statistic = new ArrayList<>();
    ArrayList<ArrayList> addedStrings = new ArrayList<>();

    ArrayList<String> conditionsFunction = new ArrayList<>();
    String result = "";


    public Minimization() {
        generateData();
        runMinimization();
    }

    private void generateData() {
        Collections.addAll(templateConditionsFunction, GenerateTable.tableViewData.get(Q_COLUMN).split(" "));

        templateFunctions = GenerateTable.tableViewData.get(FUNCTION_COLUMN).substring(1,GenerateTable.tableViewData.get(FUNCTION_COLUMN).length()-1).split(",");
        templateTrigers = GenerateTable.tableViewData.get(TRIGER_COLUMN).split(" ");

        lenghtFunctions = GenerateTable.tableViewData.get(FUNCTION_COLUMN + GenerateTable.columnCount).length();
        lenghtTrigers = GenerateTable.tableViewData.get(TRIGER_COLUMN + GenerateTable.columnCount).length();

        lenghtConditional = GenerateTable.tableViewData.get(CONDITION_COLUMN + GenerateTable.columnCount).length();

        rowCount = GenerateTable.tableViewData.size() / GenerateTable.columnCount - 1;
    }

    private void runMinimization() {
        createConditionFunctionFromTemplate(true);

        for (int i = 0; i < lenghtFunctions; i++) {
            runFuntionMinimization(i, FUNCTION_COLUMN);

        }
        conditionsFunction.clear();
        Collections.addAll(templateConditionsFunction, GenerateTable.tableViewData.get(CONDITION_COLUMN).substring(1,GenerateTable.tableViewData.get(CONDITION_COLUMN).length()-1).split(","));
        createConditionFunctionFromTemplate(false);

        for (int i = 0; i < lenghtTrigers; i++) {
            runFuntionMinimization(i, TRIGER_COLUMN);
        }
        String statBlocs = new String("Статистика \n По кількості блоків\n");
        String statEnters = new String("По кількості входів\n");
        for (String[] arr:statistic) {
            statBlocs+=arr[2]+": "+arr[0]+'\n';
            statEnters+=arr[2]+": "+arr[1]+'\n';
        }
        setStaticticString(statBlocs+'\n'+statEnters);
    }
    String staticticString;

    public String getStaticticString() {
        return staticticString;
    }

    public void setStaticticString(String staticticString) {
        this.staticticString = staticticString;
    }

    double countBlocs = 0;
    double countEnters = 0;
    double countBlocsAfterMin = 0;
    double countEntersAfterMin = 0;


    private void runFuntionMinimization(int i, int numberColumn) {
        table.clear();
        firstTable = new ArrayList[conditionsFunctionLenght];
        secondTable = new ArrayList<>();
        nextTables = new ArrayList<>();
        addedStrings.clear();

        for (int j = 0; j < rowCount; j++) {
            putConditionFunctionFromTemplate(i, j, numberColumn);
        }

        for (int j = 0; j < lenghtConditional; j++) {
            addedStrings.add(new ArrayList());
        }

        for (int j = 0; j < firstTable.length; j++) {
            firstTable[j] = new ArrayList<>();
        }
        for (String[] s : table) {
            if ((s[1]).equals(Model.ONE)) {
                 countBlocs++;
                int count;

                if ((count = countChar(s[0], '-')) > 0) {
                    addedStrings.get(count - 1).add(s[0].replaceAll("-", String.valueOf(X_CHAR)));
                    countEnters+=conditionsFunctionLenght-count;
                } else{
                    countEnters+=conditionsFunctionLenght;
                    firstTable[countChar(s[0], ONE_CHAR)].add(s[0]);
                }
            }
        }

        generateSecondTable();

        removeImplikants();

        searchFunction();
    }

    public String getResult() {
        return result;
    }

    private void searchFunction() {

        ArrayList<String> column = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        for (ArrayList<String> list : nextTables) {
            for (String s : list) {
                if (s != DELETED) {
                    column.add(s);
                }

            }
        }
        for (ArrayList<String> list : firstTable) {
            for (String s : list) {
                if (s != DELETED) {
                    column.add(s);
                }
            }
        }
        for (String[] s : table) {
            if ((s[1]).equals(Model.ONE)) {
                row.add(s[0]);

            }
        }
        String[][] matrixPokr = new String[column.size()][row.size()];
        for (String[] array : matrixPokr) {
            Arrays.fill(array, "");
        }
        for (int i = 0; i < column.size(); i++) {
            for (int j = 0; j < row.size(); j++) {
                if (checkForRemove(column.get(i), row.get(j))) {
                    matrixPokr[i][j] = String.valueOf(V_CHAR);
                }
            }

        }
        ArrayList<String> kern = new ArrayList<>();
        //шукаю ядро функції
        for (int i = 0; i < row.size(); i++) {

            for (int j = 0; j < column.size(); j++) {
                int count = 0;
                if (matrixPokr[j][i].equals(V_CHAR)) {
                    count++;
                }
                if (count == 1) {

                    for (int k = 0; k < row.size(); k++) {
                        if (matrixPokr[j][k].equals(V_CHAR)) {
                            row.set(k, DELETED);
                        }
                    }
                    kern.add(column.get(j));

                }
            }

        }
        //шукаю інші імпліканти, щоб покрити  конституенти, що залишились

        while (checkAllDeleted(row)) {
            int maxCount = Integer.MIN_VALUE;
            int pos = 0;
            for (int i = 0; i < column.size(); i++) {
                int maxCountInRow = 0;
                for (int j = 0; j < row.size(); j++) {
                    if (!kern.contains(column.get(i))) {
                        if (!row.get(j).equals(DELETED))
                            maxCountInRow++;
                    }
                }
                if (maxCountInRow > maxCount) {
                    maxCount = maxCountInRow;
                    pos = i;
                }
            }
            kern.add(column.get(pos));
            for (int i = 0; i < row.size(); i++) {
                if (matrixPokr[pos][i].equals(String.valueOf(V_CHAR))) {
                    row.set(i, DELETED);
                }
            }
        }
        for (String s:kern) {
           countBlocsAfterMin++;
            for (int i = 0; i <s.length() ; i++) {
               if(s.charAt(i)!=X_CHAR)
                   countEntersAfterMin++;
            }
        }

        printResult(kern);


    }



    private void printResult(ArrayList<String> kern) {
        if (position < lenghtFunctions) {
            result += (templateFunctions[position] + " = (");
            statistic.add(new String[]{String.valueOf((countBlocs/countBlocsAfterMin)),
                    String.valueOf((countEnters/countEntersAfterMin)),templateFunctions[position]});
        } else {
            result += (templateTrigers[position - lenghtFunctions] + " = ");
            statistic.add(new String[]{String.valueOf((countBlocs/countBlocsAfterMin)),
                    String.valueOf((countEnters/countEntersAfterMin)),templateTrigers[position - lenghtFunctions]});
        }
        for (int i = 0; i <kern.size() ; i++) {
            if(i!=kern.size()-1)
            result += (convertToNormalView(kern.get(i))+ ' ' + V_CHAR + ' ');
            else  result += (convertToNormalView(kern.get(i)));
        }
        result +=(");"+'\n');
        position++;
    }

    private String convertToNormalView(String s){
        String result = new String("(");
        for (int i = 0; i <s.length() ; i++) {
          if(s.charAt(i)!=X_CHAR) {
              if (s.charAt(i) == ONE_CHAR)
                  result += (templateConditionsFunction.get(i) + AND_CHAR);
              else {
                  result += ("not (" + templateConditionsFunction.get(i) + ")" + AND_CHAR);
              }
          }
        }
        if(result.charAt(result.length()-1)==AND_CHAR)
            result = new String(result.substring(0,result.length()-1));
        result+=")";
       return result;
    }
    private boolean checkAllDeleted(ArrayList<String> row) {
        for (String s : row) {
            if (!s.equals(DELETED))
                return true;
        }
        return false;
    }


    private void removeImplikants() {
        for (int i = nextTables.size() - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                for (String s1 : nextTables.get(i)) {
                    for (int k = 0; k < nextTables.get(j).size(); k++) {
                        if (!s1.equals(nextTables.get(j).get(k)) && (s1 != DELETED || nextTables.get(j).get(k) != DELETED))
                            if (checkForRemove(s1, nextTables.get(j).get(k))) {
                                nextTables.get(j).set(k, DELETED);
                            }
                    }
                    for (ArrayList<String> list : firstTable) {
                        for (int k = 0; k < list.size(); k++) {

                            if (!s1.equals(list.get(k)) && (s1 != DELETED || list.get(k) != DELETED))
                                if (checkForRemove(s1, list.get(k))) {
                                    list.set(k, DELETED);
                                }
                        }

                    }
                }

            }


        }
    }

    private boolean checkForRemove(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != X_CHAR)
                if (s1.charAt(i) != s2.charAt(i))
                    return false;
        }
        return true;
    }


    private void generateNextTables(ArrayList<String> list) {

        ArrayList<String> tmpTable = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    if (checkCorrectImplicantsFor(list.get(i), list.get(j)))
                        putNew(list.get(i), list.get(j), tmpTable);
                }
            }

        }

        nextTables.add(tmpTable);
        if (nextTables.size() <= addedStrings.size())
            nextTables.get(nextTables.size() - 1).addAll(addedStrings.get(nextTables.size() - 1));
        if (!tmpTable.isEmpty() || nextTables.size() <= addedStrings.size()) {
            generateNextTables(tmpTable);
        } else nextTables.remove(nextTables.size() - 1);

    }

    private boolean checkCorrectImplicantsFor(String s1, String s2) {
        int lenght = s1.length();
        for (int i = 0; i < lenght; i++) {
            if (s1.charAt(i) == X_CHAR) {
                if (s2.charAt(i) != X_CHAR) {
                    return false;
                }
            }

        }
        return true;
    }

    private void generateSecondTable() {
        for (int i = 0; i < conditionsFunctionLenght - 1; i++) {
            for (int j = 0; j < firstTable[i].size(); j++) {
                for (int k = 0; k < firstTable[i + 1].size(); k++) {
                    putNew(firstTable[i].get(j), firstTable[i + 1].get(k), secondTable);
                }
            }
        }
        nextTables.add(secondTable);
        if (nextTables.size() <= addedStrings.size())
            nextTables.get(nextTables.size() - 1).addAll(addedStrings.get(nextTables.size() - 1));

        if (!secondTable.isEmpty() || nextTables.size() <= addedStrings.size()) {

            generateNextTables(secondTable);
        } else nextTables.remove(nextTables.size() - 1);
    }

    private void putNew(String s1, String s2, ArrayList<String> list) {
        int countDifChar = 0;
        int positionOnList = 0;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                countDifChar++;
                positionOnList = i;
            }
            if (countDifChar > 1)
                return;
        }
        StringBuilder newS = new StringBuilder(s1);
        newS.setCharAt(positionOnList, X_CHAR);
        if (!list.contains(String.valueOf(newS)))
            list.add(String.valueOf(newS));
    }


    private int countChar(String s, char c) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c)
                res++;
        }

        return res;
    }

    private void putConditionFunctionFromTemplate(int i, int j, int numberColumn) {
        char f = GenerateTable.tableViewData.get(GenerateTable.columnCount * (j + 1) + numberColumn).charAt(i);
        table.add(new String[]{conditionsFunction.get(j), String.valueOf(f)});

    }

    private void createConditionFunctionFromTemplate(boolean onlyQ) {
        for (int i = 0; i < rowCount; i++) {
            if(onlyQ)
                conditionsFunction.add(GenerateTable.tableViewData.get(GenerateTable.columnCount * (i + 1) + Q_COLUMN));

            else{
                conditionsFunction.add(GenerateTable.tableViewData.get(GenerateTable.columnCount * (i + 1) + Q_COLUMN)
                        + GenerateTable.tableViewData.get(GenerateTable.columnCount * (i + 1) + CONDITION_COLUMN));
            }
        }
        if (!conditionsFunction.isEmpty())
            conditionsFunctionLenght = conditionsFunction.get(0).length();
    }
}
