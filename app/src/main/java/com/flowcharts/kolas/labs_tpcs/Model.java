package com.flowcharts.kolas.labs_tpcs;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.flowcharts.kolas.labs_tpcs.blocks.BeginBlock;
import com.flowcharts.kolas.labs_tpcs.blocks.BlockObj;
import com.flowcharts.kolas.labs_tpcs.blocks.BlockTypes;
import com.flowcharts.kolas.labs_tpcs.blocks.EndBlock;
import com.flowcharts.kolas.labs_tpcs.blocks.RectBlock;
import com.flowcharts.kolas.labs_tpcs.blocks.RhombBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kolas on 10.09.16.
 */
public class Model {
    int id_counter;
    int IdCrossing;
    Graph graph;

    public String[][] matrixS;
    public String[][] matrixL;
    public String[][] matrixLGraph;

    public static final String ONE = "1";
    public static final String ONE_TRUE = "+1";
    public static final String ONE_FALSE = "-1";
    public static final String NULL = "";
    public static final String TAG = "Mylogs";

    public static boolean showGraph;
    boolean errors;
    boolean flag;

    HashMap<Integer, BlockObj> allBlocs;
    HashMap<Integer, Link> allLinks;
    GraphObj[] graphObjs;

    BlockObj[] blocksMatrixLGraph;
    BlockObj[] blocksMatrixS;
    BlockObj[] blocksMatrixL;

    BlockObj thisBloc;
    Link thisLinc;
    ArrayList<SimpleArrow> thisArrows;
    SimpleArrow thisSimpleArrow;


    MySurfaceView mySurfaceView;
    MainActivity mainActivity;


    float centers;
    float centerL;
    float centerR;

    public float getCenterL() {
        return centerL;
    }

    public void setCenterL(float centerL) {
        this.centerL = centerL;
    }

    public float getCenterR() {
        return centerR;
    }

    public void setCenterR(float centerR) {
        this.centerR = centerR;
    }


    public float getCenters() {
        return centers;
    }

    public void setCenters(float centers) {
        this.centers = centers;
    }

    public Model(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        allBlocs = new HashMap<>();
        allLinks = new HashMap<>();
        thisArrows = new ArrayList<>();
        mySurfaceView = mainActivity.fsurface;


    }

    public static boolean isShowGraph() {
        return showGraph;
    }

    public static void setShowGraph(boolean showGraph) {
        Model.showGraph = showGraph;
    }

    private void addNewBloc(BlockObj newBloc) {
        allBlocs.put(newBloc.getId(), newBloc);
    }

    private void addNewLinc(Link newLinc) {
        allLinks.put(newLinc.getId(), newLinc);
    }

    void addingNewBloc(BlockTypes type, float x, float y) {
        int w = 100;
        int h = 100;
        if (thisBloc != null)
            thisBloc.setColor(Color.WHITE);
        switch (type) {
            case RECT: {
                thisBloc = new RectBlock(x, y, w, h, Color.GREEN, type);
                break;
            }
            case BEGIN: {
                thisBloc = new BeginBlock(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case END: {
                thisBloc = new EndBlock(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case RHOMB: {
                thisBloc = new RhombBlock(x, y, w, h, Color.GREEN, type);
                break;
            }
        }
        setIdforBloc();
        coloring();
        addNewBloc(thisBloc);

    }

    private void setIdforBloc() {
        thisBloc.setId(id_counter);
        id_counter++;


    }


    private void coloring() {
        for (BlockObj obj : allBlocs.values()) {
            if (!obj.equals(thisBloc))
                obj.setColor(Color.WHITE);
            else obj.setColor(Color.GREEN);


        }
    }


    void setPosForThisBloc(float x, float y) {
        if (Math.abs(centers - x) < 100)
            thisBloc.setX(centers - thisBloc.getWidth() / 2);
        else if (Math.abs(centerR - x) < 100)
            thisBloc.setX(centerR - thisBloc.getWidth() / 2);
        else if (Math.abs(centerL - x) < 100)
            thisBloc.setX(centerL - thisBloc.getWidth() / 2);
        else
            thisBloc.setX(x - thisBloc.getWidth() / 2);

        thisBloc.setY(y - thisBloc.getHeight() / 2);
    }

    private boolean col(float x, float y) {
        for (BlockObj obj : allBlocs.values()) {
            if (x > obj.getX() && x < obj.getX() + obj.getWidth() && y > obj.getY() && y < obj.getY() + obj.getHeight()) {
                IdCrossing = obj.getId();
                return true;
            }
        }
        return false;
    }

    void checkThisBloc(float x, float y) {
        if (col(x, y)) {
            searchThisBlocCrossing();
        }

    }

    public Graph getGraph() {

        return graph = new Graph(matrixLGraph, graphObjs);
    }

    private void searchThisBlocCrossing() {

        thisBloc = allBlocs.get(IdCrossing);

        coloring();
        if (thisBloc.getType() == BlockTypes.RHOMB && flag)
            mainActivity.showDialogRhomb();


    }

    void addingNewLink() {
        PointConnect pl = thisBloc.getOut_Point();
        thisSimpleArrow = new SimpleArrow(pl.getX(), pl.getY());
        thisArrows = new ArrayList<>();
        if (thisBloc.getType() != BlockTypes.RHOMB)
            thisLinc = new Link(thisBloc.getId());

        flag = false;

    }

    boolean setBlocFrom(float x, float y) {

        if (thisBloc != null && thisBloc.getType() != BlockTypes.END) {
            if (thisBloc.getType() != BlockTypes.RHOMB) {
                mainActivity.isblocfrom = true;
                if (!thisBloc.getOut_Point().isUse())
                    thisBloc.getOut_Point().setUse(true);
            }

            //  flag = true;
            return true;
        } else
            checkThisBloc(x, y);
        if (thisBloc != null)
            if (thisBloc.getOut_Point().isUse() && thisBloc.getType() != BlockTypes.RHOMB)
                thisBloc = null;
        return false;
    }

    void searshBlocForConnect() {
        for (BlockObj block : allBlocs.values()) {
            if (block.getType() != BlockTypes.END) {
                if (!block.getOut_Point().isUse())
                    block.setColor(Color.YELLOW);
            }
        }
    }

    void setLinkParam() {
        thisBloc = null;
        flag = true;
    }

    private void calcway(float x, float y) {
        if (Math.abs((double) (thisSimpleArrow.getX_from() - x)) > 50) {
            if (thisSimpleArrow.getX_from() - x < 0)
                thisSimpleArrow.setType(TypeLines.HORISONTAL_LEFT);
            else
                thisSimpleArrow.setType(TypeLines.HORISONTAL_R);
            thisSimpleArrow.setX_to(x);
            thisSimpleArrow.setY_to(thisSimpleArrow.getY_from());
            thisSimpleArrow.setHorizontal(true);

            addSizeToArrow(true);

            thisSimpleArrow = new SimpleArrow(x, thisSimpleArrow.getY_from());


        } else if (Math.abs((double) (thisSimpleArrow.getY_from() - y)) > 50) {

            if (thisSimpleArrow.getY_from() - y > 0)
                thisSimpleArrow.setType(TypeLines.VERTICAL_TOP);
            else thisSimpleArrow.setType(TypeLines.vERTICAL_BOT);
            thisSimpleArrow.setX_to(thisSimpleArrow.getX_from());
            thisSimpleArrow.setY_to(y);
            thisSimpleArrow.setHorizontal(false);
            addSizeToArrow(false);
            thisSimpleArrow = new SimpleArrow(thisSimpleArrow.getX_from(), y);


        }
    }


    void searchBtocTo(float x, float y) {
        if (!col(x, y) || IdCrossing == thisBloc.getId()) {
            calcway(x, y);
        } else {

            searchThisBlocCrossing();
            thisBloc.getIn_Point().setUse(true);
            if (thisArrows.size() >= 1)
                if (thisArrows.get(thisArrows.size() - 1).isHorizontal())
                    thisArrows.get(thisArrows.size() - 1).setX_to(thisBloc.getIn_Point().getX());
                else
                    thisArrows.get(thisArrows.size() - 1).setY_to(thisBloc.getIn_Point().getY());

            else calcThreeArrows();

            thisLinc.setId_to(thisBloc.getId());

            thisLinc.setId(id_counter++);
            thisLinc.setArrows(thisArrows);
            addNewLinc(thisLinc);

            mainActivity.setArrow(false);
            mainActivity.setIsblocfrom(false);

        }

    }


    void drawnewLineIn(float begin_x, float begin_y, float end_x, float end_y, ArrayList<SimpleArrow> sa) {
        if (sa.size() >= 1) {

            if (sa.get(sa.size() - 1).isHorizontal())
                corectHorizontalEnd(end_x, sa);
            else
                corectVerticalEnd(end_y, sa);


            if (sa.get(0).isHorizontal()) {
                corectHorizontalBegin(begin_x, sa);
            } else corectVerticalBegin(begin_y, sa);

        }
    }

    private void corectHorizontalEnd(float end_x, ArrayList<SimpleArrow> sa) {

        if (sa.size() >= 1) {
            sa.get(sa.size() - 1).setX_to(end_x);


        }


    }

    private void corectVerticalEnd(float end_y, ArrayList<SimpleArrow> sa) {
        if (sa.size() >= 1) {

            sa.get(sa.size() - 1).setY_to(end_y);


        }


    }

    private void corectHorizontalBegin(float begin_x, ArrayList<SimpleArrow> sa) {

        if (sa.size() >= 1) {
            sa.get(0).setX_from(begin_x);

        }

    }

    private void corectVerticalBegin(float begin_y, ArrayList<SimpleArrow> sa) {
        if (sa.size() >= 1) {
            sa.get(0).setY_from(begin_y);

        }

    }

    private void addSizeToArrow(boolean b) {

        if ((thisArrows.size() >= 1 && thisArrows.get(thisArrows.size() - 1).isHorizontal() == b)) {
            thisArrows.get(thisArrows.size() - 1).setX_to(thisSimpleArrow.getX_to());
            thisArrows.get(thisArrows.size() - 1).setY_to(thisSimpleArrow.getY_to());
        } else
            thisArrows.add(thisSimpleArrow);

    }

    public void clear() {
        allBlocs.clear();
        allLinks.clear();
        id_counter = 0;
        IdCrossing = 0;
    }


    private void calcThreeArrows() {

        float newy = (thisSimpleArrow.getY_from() + thisBloc.getIn_Point().getY()) / 2;


        thisSimpleArrow = new SimpleArrow(thisBloc.getIn_Point().getX(), newy, thisBloc.getIn_Point().getX(), thisBloc.getIn_Point().getY());
        thisSimpleArrow.setHorizontal(false);
        thisArrows.add(thisSimpleArrow);


    }

    public void delete() {
        int id = -1;
        int idrhomb = -1;
        if (thisBloc != null) {
            Iterator<Map.Entry<Integer, Link>> linc = allLinks.entrySet().iterator();
            while (linc.hasNext()) {
                Map.Entry<Integer, Link> ilinc = linc.next();
                if (ilinc.getValue().getId_from() == thisBloc.getId() || ilinc.getValue().getId_to() == thisBloc.getId()) {
                    linc.remove();
                    if (ilinc.getValue().getId_from() == thisBloc.getId()) {
                        if (id == -1)
                            id = ilinc.getValue().getId_to();
                        else
                            idrhomb = ilinc.getValue().getId_to();
                    }
                    if (allBlocs.get(ilinc.getValue().getId_from()).getType() == BlockTypes.RHOMB) {

                        if (ilinc.getValue().isF_point()) {
                            int first = ((RhombBlock) allBlocs.get(ilinc.getValue().getId_from())).getFirst();
                            ((RhombBlock) allBlocs.get(ilinc.getValue().getId_from())).setFirst(RhombBlock.FREE);
                            ((RhombBlock) allBlocs.get(ilinc.getValue().getId_from())).getOutPoints().get(first).setUse(false);
                        } else {
                            int first = ((RhombBlock) allBlocs.get(ilinc.getValue().getId_from())).getSecond();
                            ((RhombBlock) allBlocs.get(ilinc.getValue().getId_from())).setSecond(RhombBlock.FREE);
                            ((RhombBlock) allBlocs.get(ilinc.getValue().getId_from())).getOutPoints().get(first).setUse(false);
                        }


                    } else
                        allBlocs.get(ilinc.getValue().getId_from()).getOut_Point().setUse(false);
                }


            }

            boolean useOutPoin = false;
            if (idrhomb != -1) {
                for (Link link : allLinks.values()) {
                    if (link.getId_to() == id)
                        useOutPoin = true;
                }

                allBlocs.get(id).getIn_Point().setUse(useOutPoin);
            }
            if (idrhomb != -1) {
                useOutPoin = false;
                for (Link link : allLinks.values()) {
                    if (link.getId_to() == idrhomb)
                        useOutPoin = true;
                }
                allBlocs.get(idrhomb).getIn_Point().setUse(useOutPoin);
            }
            allBlocs.remove(thisBloc.getId());

            if (!allBlocs.isEmpty()) {
                List<BlockObj> keys = new ArrayList<BlockObj>(allBlocs.values());
                thisBloc = keys.get(0);
                coloring();
            } else thisBloc = null;

        }
    }


    public void setText(String s) {
        if (thisBloc.getType() != BlockTypes.END && thisBloc.getType() != BlockTypes.BEGIN)
            thisBloc.setText(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void deleteLink() {
        int id = 0;
        if (thisBloc != null) {

            if (thisBloc.getType() != BlockTypes.RHOMB) {
                Iterator<Map.Entry<Integer, Link>> linc = allLinks.entrySet().iterator();
                while (linc.hasNext()) {
                    Map.Entry<Integer, Link> ilinc = linc.next();
                    if (ilinc.getValue().getId_from() == thisBloc.getId()) {

                        linc.remove();
                        id = ilinc.getValue().getId_to();
                        if (thisBloc.getType() != BlockTypes.END)
                            thisBloc.getOut_Point().setUse(false);

                    }

                }
            } else
                new DialogSelPointRhombDelete(mainActivity).show(mainActivity.getFragmentManager(), "dhsaj");


            if (thisBloc.getType() != BlockTypes.RHOMB) {
                boolean useOutPoin = false;
                for (Link link : allLinks.values()) {
                    if (link.getId_to() == id)
                        useOutPoin = true;
                }

                allBlocs.get(id).getIn_Point().setUse(useOutPoin);
            }
        }
    }


    String saveToFile() {
        saveBlocks();
        saveLinks();

        return saveBlocks() + saveLinks();
    }

    String saveBlocks() {
        String res;
        res = "<blocs>\n";
        res += getTagBlocs();
        res += "</blocs>\n";

        return res;
    }

    String saveLinks() {
        String res;
        res = "<links>\n";
        res += getTagLinks();
        res += "</links>";

        return res;

    }

    String getTagLinks() {
        String res = "";
        for (Link link : allLinks.values()) {
            res += getTagLink(link);
        }
        return res + "\n";
    }

    String getTagLink(Link link) {
        String res = "<link>\n";
        res += "<id>" + link.getId() + "</id>\n";
        res += "<to>" + link.getId_to() + "</to>\n";
        res += "<from>" + link.getId_from() + "</from>\n";
        res += "<f_point>" + link.isF_point() + "</f_point>\n";
        res += getArrowsTag(link.getArrows()) + '\n';
        res += "</link>\n";

        return res;
    }


    String getArrowsTag(ArrayList<SimpleArrow> arrows) {
        String res = "<arrows>\n";
        for (SimpleArrow arrow : arrows) {
            res += getSimpleArrowTag(arrow) + '\n';
        }
        res += "</arrows>\n";
        return res;
    }

    String getSimpleArrowTag(SimpleArrow arrow) {
        String res = "<arrow>\n";
        res += " <x_from>" + arrow.getX_from() + "</x_from>\n";
        res += "<y_from>" + arrow.getY_from() + "</y_from>\n";
        res += "<x_to>" + arrow.getX_to() + "</x_to>\n";
        res += "<y_to>" + arrow.getY_to() + "</y_to>\n";
        res += "<horisontal>" + arrow.isHorizontal() + "</horisontal>\n";

        res += "</arrow>\n";
        return res;
    }

    String getTagBlocs() {
        String res = "";
        for (BlockObj o : allBlocs.values()) {
            res += "   " + getBlocTag(o);
        }
        return res + "\n";
    }

    String getBlocTag(BlockObj o) {
        String res = "<bloc>\n";
        res += "        <type>" + o.getType() + "</type>\n";
        res += "        <x>" + o.getX() + "</x>\n";
        res += "        <y>" + o.getY() + "</y>\n";
        res += "        <id>" + o.getId() + "</id>\n";
        res += "        <text>" + o.getText() + "</text>\n";
        if (o.getType() != BlockTypes.BEGIN) {
            res += "        <in>" + o.getIn_Point().isUse() + "</in>\n";
        }
        if (o.getType() != BlockTypes.END) {
            res += "        <out>" + getOutPoint(o) + "</out>\n";
        }

        res += "   </bloc>\n";


        return res;
    }

    String getOutPoint(BlockObj o) {
        String res = "\n";
        if (o.getType() == BlockTypes.RHOMB) {
            res += "<first>" + ((RhombBlock) o).getFirst() + "</first>\n";
            res += "<second>" + ((RhombBlock) o).getSecond() + "</second>\n";
            res += "<number>" + ((RhombBlock) o).getNuberPointLink() + "</number>\n";
            res += "<useR>" + ((RhombBlock) o).getPointR().isUse() + "</useR>\n";
            res += "<useL>" + ((RhombBlock) o).getPointL().isUse() + "</useL>\n";
            res += "<useB>" + ((RhombBlock) o).getPointB().isUse() + "</useB>\n";
            res += "<tfR>" + ((RhombBlock) o).getPointR().isType_for_rhomh() + "</tfR>\n";
            res += "<tfL>" + ((RhombBlock) o).getPointL().isType_for_rhomh() + "</tfL>\n";
            res += "<tfB>" + ((RhombBlock) o).getPointB().isType_for_rhomh() + "</tfB>\n";
        }
        return res += "<use>" + o.getOut_Point().isUse() + "</use>\n";

    }

    void parseFile(String text) {
        if (text.contains("<blocs>")) {
            parseBlocs(getTagString(text, "<blocs>"));
            parseLincs(getTagString(text, "<links>"));
        }
        ;

    }

    final String LOG_TAG = "myLogs";

    void parseBlocs(String s) {
        ArrayList<String> strings = getTagsArray(s, "<bloc>");
        for (String tmp : strings) {
            Log.d(LOG_TAG, tmp);
            parseBloc(tmp);

        }
    }


    void parseBloc(String bloc) {
        float x = Float.parseFloat(getTagString(bloc, "<x>"));
        float y = Float.parseFloat(getTagString(bloc, "<y>"));
        int id = Integer.parseInt(getTagString(bloc, "<id>"));
        BlockTypes type = getTypeBloc(getTagString(bloc, "<type>"));
        String text = getTagString(bloc, "<text>");


        int w = 100;
        int h = 100;
        if (thisBloc != null)
            thisBloc.setColor(Color.WHITE);
        switch (type) {
            case RECT: {
                thisBloc = new RectBlock(x, y, w, h, Color.GREEN, type);
                break;
            }
            case BEGIN: {
                thisBloc = new BeginBlock(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case END: {
                thisBloc = new EndBlock(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case RHOMB: {
                thisBloc = new RhombBlock(x, y, w, h, Color.GREEN, type);
                break;
            }
        }

        coloring();

        while (id_counter <= id)
            id_counter++;
        thisBloc.setId(id);
        thisBloc.setText(text);
        if (type != BlockTypes.BEGIN) {
            boolean in = Boolean.parseBoolean(getTagString(bloc, "<in>"));
            thisBloc.getIn_Point().setUse(in);
        }

        if (type != BlockTypes.END) {
            String out = getTagString(bloc, "<out>");
            boolean use = Boolean.parseBoolean(getTagString(out, "<use>"));
            thisBloc.getOut_Point().setUse(use);
            if (type == BlockTypes.RHOMB) {
                int second = Integer.parseInt(getTagString(out, "<second>"));
                int fisrs = Integer.parseInt(getTagString(out, "<first>"));
                int use_numper = Integer.parseInt(getTagString(out, "<number>"));
                boolean useR = Boolean.parseBoolean(getTagString(out, "<useR>"));
                boolean useL = Boolean.parseBoolean(getTagString(out, "<useL>"));
                boolean useB = Boolean.parseBoolean(getTagString(out, "<useB>"));

                boolean tfR = Boolean.parseBoolean(getTagString(out, "<tfR>"));
                boolean tfL = Boolean.parseBoolean(getTagString(out, "<tfL>"));
                boolean tfB = Boolean.parseBoolean(getTagString(out, "<tfB>"));
                ((RhombBlock) thisBloc).setFirst(fisrs);
                ((RhombBlock) thisBloc).setSecond(second);

                ((RhombBlock) thisBloc).setNuberPointLink(use_numper);

                ((RhombBlock) thisBloc).getPointR().setUse(useR);
                ((RhombBlock) thisBloc).getPointL().setUse(useL);
                ((RhombBlock) thisBloc).getPointB().setUse(useB);

                ((RhombBlock) thisBloc).getPointR().setType_for_rhomh(tfR);
                ((RhombBlock) thisBloc).getPointL().setType_for_rhomh(tfL);
                ((RhombBlock) thisBloc).getPointB().setType_for_rhomh(tfB);
            }
        }

        addNewBloc(thisBloc);

    }

    BlockTypes getTypeBloc(String s) {
        s.trim();
        if (s.equals(BlockTypes.BEGIN.toString()))
            return BlockTypes.BEGIN;
        if (s.equals(BlockTypes.END.toString()))
            return BlockTypes.END;
        if (s.equals(BlockTypes.RECT.toString()))
            return BlockTypes.RECT;
        if (s.equals(BlockTypes.RHOMB.toString()))
            return BlockTypes.RHOMB;
        return null;

    }

    void parseLincs(String s) {
        ArrayList<String> strings = getTagsArray(s, "<link>");
        for (String tmp : strings) {
            Log.d(LOG_TAG, tmp);
            parseLink(tmp);

        }
    }


    void parseLink(String bloc) {
        int id = Integer.parseInt(getTagString(bloc, "<id>"));
        int to = Integer.parseInt(getTagString(bloc, "<to>"));
        int from = Integer.parseInt(getTagString(bloc, "<from>"));
        boolean f_point = Boolean.parseBoolean(getTagString(bloc, "<f_point>"));
        ArrayList<SimpleArrow> simpleArrows = new ArrayList<>();
        ArrayList<String> strings = getTagsArray(getTagString(bloc, "<arrows>"), "<arrow>");
        for (String tmp : strings) {
            simpleArrows.add(parseArrow(tmp));
        }
        Link newLink = new Link(from, to, simpleArrows);
        newLink.setId(id);
        newLink.setF_point(f_point);
        while (id_counter <= id)
            id_counter++;
        addNewLinc(newLink);

    }

    SimpleArrow parseArrow(String link) {
        float x_to = Float.parseFloat(getTagString(link, "<x_to>"));
        float y_to = Float.parseFloat(getTagString(link, "<y_to>"));
        float x_from = Float.parseFloat(getTagString(link, "<x_from>"));
        float y_from = Float.parseFloat(getTagString(link, "<y_from>"));
        boolean horisontal = Boolean.parseBoolean(getTagString(link, "<horisontal>"));
        SimpleArrow sa = new SimpleArrow(x_from, y_from, x_to, y_to);
        sa.setHorizontal(horisontal);
        return sa;
    }

    String getTagString(String text, String tag) {
        String newText;

        String openTag = tag;
        String closeTag = new StringBuffer(tag).insert(1, '/').toString();
        int start = text.indexOf(openTag) + openTag.length();
        int end = text.indexOf(closeTag);
        newText = new String(text.substring(start, end));
        return newText;

    }

    ArrayList<String> getTagsArray(String text, String tag) {
        ArrayList<String> newText = new ArrayList<>();
        String bufer = new String(text);
        String closeTag = new StringBuffer(tag).insert(1, '/').toString();
        int end = 0;
        while (bufer.contains(closeTag)) {
            end = bufer.indexOf(closeTag);
            newText.add(new String(bufer.substring(tag.length(), end)));
            bufer = new String(bufer.substring(end + closeTag.length()));
            if (bufer.length() <= closeTag.length())
                break;
        }

        return newText;

    }

    boolean contentBloc(BlockTypes type) {
        for (BlockObj bloc : allBlocs.values()
                ) {
            if (bloc.getType() == type)
                return true;

        }
        return false;
    }

    ArrayList<String> searchError() {
        errors = true;
        ArrayList<String> arrayList = new ArrayList<>();
        if (!contentBloc(BlockTypes.BEGIN))
            arrayList.add("Відсутній блок \"Початок\"");
        if (!contentBloc(BlockTypes.END))
            arrayList.add("Відсутній блок \"Кінець\"");
        for (BlockObj bloc : allBlocs.values()) {
            switch (bloc.getType()) {
                case BEGIN: {
                    if (!bloc.getOut_Point().isUse()) {
                        arrayList.add("Блок " + BlockTypes.BEGIN + "(" + bloc.getText() + ") висячий");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }
                case END: {
                    if (!bloc.getIn_Point().isUse()) {
                        arrayList.add("Блок " + BlockTypes.END + "(" + bloc.getText() + ") недосяжний");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }
                case RECT: {
                    if (!bloc.getOut_Point().isUse()) {
                        arrayList.add("Блок " + BlockTypes.RECT + "(" + bloc.getText() + ") висячий");
                        bloc.setColor(Color.RED);
                    }

                    if (!bloc.getIn_Point().isUse()) {
                        arrayList.add("Блок " + BlockTypes.RECT + "(" + bloc.getText() + ") недосяжний");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }
                case RHOMB: {
                    int use = 0;
                    for (PointConnect point : ((RhombBlock) bloc).getOutPoints()
                            ) {
                        if (point.isUse())
                            use++;

                    }
                    if (use < 1) {
                        arrayList.add("Блок " + BlockTypes.RHOMB + "(" + bloc.getText() + ") не містить жодного виходу");
                        bloc.setColor(Color.RED);
                    }
                    if (use == 1) {
                        arrayList.add("Блок " + BlockTypes.RHOMB + "(" + bloc.getText() + ")  містить тільки 1  вихід");
                        bloc.setColor(Color.RED);
                    }
                    if (!bloc.getIn_Point().isUse()) {
                        arrayList.add("Блок " + BlockTypes.RHOMB + "(" + bloc.getText() + ") недосяжний");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }


            }
        }


        if (arrayList.size() == 0) {
            errors = false;
            arrayList.add("Помилки відсутні");
        }
        return arrayList;
    }


    void searchBlocksForGraph() {
        createMatrixSumig();
        createMatrixLink();
        int size = blocksMatrixL.length;
        List<BlockObj> listGraphBlocks = new ArrayList<>();

        for (BlockObj obj : blocksMatrixL) {
            if (obj.getType() != BlockTypes.END) {
                listGraphBlocks.add(obj);

            }
        }

        blocksMatrixLGraph = new BlockObj[size - 1];
        listGraphBlocks.toArray(blocksMatrixLGraph);

        int indexBegin = 0;
        for (int i = 0; i < size; i++) {
            if (blocksMatrixL[i].getType() == BlockTypes.BEGIN)
                indexBegin = i;
        }

        int indexEnd = 0;
        for (int i = 0; i < size; i++) {
            if (blocksMatrixL[i].getType() == BlockTypes.END)
                indexEnd = i;
        }

        String[][] tmpMatrixLink = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmpMatrixLink[i][j] = matrixL[i][j];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == indexEnd && !tmpMatrixLink[i][j].equals(NULL)) {
                    tmpMatrixLink[i][indexBegin] = tmpMatrixLink[i][j];
                    tmpMatrixLink[i][j] = NULL;
                }

            }
        }

        matrixLGraph = new String[size - 1][size - 1];

        int IndexI = 0;
        int IndexJ = 0;
        for (int i = 0; i < size; i++) {
            if (i != indexEnd) {
                IndexJ = 0;
                for (int j = 0; j < size; j++) {
                    if (j != indexEnd) {
                        matrixLGraph[IndexI][IndexJ] = tmpMatrixLink[i][j];
                        IndexJ++;
                    }
                }

                IndexI++;
            }
        }


    }


    void createGraph() {
        searchBlocksForGraph();

        int idgraph = 1;
        int radius = 300;
        float x;
        float y;
        int count = blocksMatrixLGraph.length;

        graphObjs = new GraphObj[blocksMatrixLGraph.length];
        for (int i = 0; i < blocksMatrixLGraph.length; i++) {
            if (blocksMatrixLGraph[i].getType() == BlockTypes.RECT) {
                graphObjs[i] = new GraphObj(blocksMatrixLGraph[i].getId(), "Z" + idgraph, blocksMatrixLGraph[i].getText());
                blocksMatrixLGraph[i].setText_state("Z" + idgraph);
                idgraph++;
            } else {
                graphObjs[i] = new GraphObj(blocksMatrixLGraph[i].getId(), "Z0", "0");
                blocksMatrixLGraph[i].setText_state("Z0");
            }
            y = ((float) (centers + radius * Math.sin((2 * Math.PI / count) * i)));
            x = ((float) (centers + radius * Math.cos((2 * Math.PI / count) * i)));
            graphObjs[i].setCenter_x(x);
            graphObjs[i].setCenter_y(y);
            graphObjs[i].setRadius(50);
            graphObjs[i].setAngle((float) ((2 * Math.PI / count) * i));

        }


    }

    HashSet<String> allcode = new HashSet<>();

    /* void coddingGraph(int size) {
         //int cellSize =
         int cellSize = size;
         String tmpCode = "";
         for (int i = 0; i < cellSize; i++) {
             tmpCode += '0';
         }

         for (GraphObj obj : allGraphsObjs.values()) {
             if (allcode.size() == 0) {
                 obj.setCode(tmpCode);
                 allcode.add(tmpCode);
             }
             codiingObj(obj, size);
         }


     }

     void codiingObj(GraphObj obj, int size) {
         ArrayList<String> allsusidcode = new ArrayList<>();
         ArrayList<String> allNotUsecode = new ArrayList<>();
         for (int i = 0; i < size; i++) {
             allsusidcode.add(replaceChar(obj.code, i));
         }
         for (int i = 0; i < size; i++) {
             if (!allcode.contains(allsusidcode.get(i)))
                 allNotUsecode.add(allsusidcode.get(i));
         }


         int i = 0;
         for (GraphLink link : linkMatrixL.values()) {
             if (link.getId_to() != link.getId_from()) {
                 if (link.id_from == obj.getId()) {
                     allGraphsObjs.get(link.getId_to()).setCode(allNotUsecode.get(i));
                     i++;
                     if (i == allNotUsecode.size())
                         break;
                 }

                 if (link.getId_to() == obj.getId()) {
                     allGraphsObjs.get(link.getId_from()).setCode(allNotUsecode.get(i));
                     i++;
                     if (i == allNotUsecode.size())
                         break;
                 }

             }


         }
         for (int j = 0; j < i; j++) {
             allcode.add(allNotUsecode.get(j));
         }
         for (GraphLink link : linkMatrixL.values()) {
             if (link.getId_to() != link.getId_from()) {
                 if (link.id_from == obj.getId()) {
                     codiingObj(allGraphsObjs.get(link.id_to), size);
                 }

                 if (link.getId_to() == obj.getId()) {
                     codiingObj(allGraphsObjs.get(link.id_from), size);
                 }

             }

         }
         for (GraphLink link : linkMatrixL.values()) {
             if (link.getId_to() != link.getId_from()) {
                 if (link.id_from == obj.getId()) {
                     if (allGraphsObjs.get(link.id_to).code == null)

                         size++;
                     coddingGraph(size);
                 }

                 if (link.getId_to() == obj.getId()) {
                     if (allGraphsObjs.get(link.id_from).code == null)
                         size++;
                     coddingGraph(size);
                 }
             }

         }

     }

 */
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


    public String[] createMatrixSumig() {

        int size = allBlocs.size();

        blocksMatrixS = new BlockObj[size];
        allBlocs.values().toArray(blocksMatrixS);

        matrixS = new String[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(matrixS[i], NULL);
        }
        for (int i = 0; i < size; i++) {
            for (Link link : allLinks.values()) {
                if (link.getId_from() == blocksMatrixS[i].getId()) {
                    for (int j = 0; j < size; j++) {
                        if (blocksMatrixS[j].getId() == link.getId_to())
                            if (blocksMatrixS[i].getType() == BlockTypes.RHOMB) {
                                if (!link.isF_point())
                                    matrixS[i][j] = ONE_TRUE;
                                else
                                    matrixS[i][j] = ONE_FALSE;
                            } else
                                matrixS[i][j] = ONE;
                    }
                }
            }
        }

        return getStringMatrix(matrixS, blocksMatrixS);
    }


    public String[] createMatrixLink() {
        int size = allBlocs.size();

        matrixL = new String[size][size];

        for (int i = 0; i < size; i++) {
            Arrays.fill(matrixL[i], NULL);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!matrixS[i][j].equals(NULL))
                    if (blocksMatrixS[i].getType() == BlockTypes.RECT || blocksMatrixS[i].getType() == BlockTypes.BEGIN) {
                        if (blocksMatrixS[j].getType() != BlockTypes.RHOMB) {
                            matrixL[i][j] = ONE;
                        } else {
                            for (int k = 0; k < size; k++) {
                                if (matrixS[j][k].equals(ONE_TRUE))
                                    searchNotRhombBlock(i, k, j, " ", true); //true гілка умовного блоку
                                if (matrixS[j][k].equals(ONE_FALSE))
                                    searchNotRhombBlock(i, k, j, " ", false);//false гілка умовного блоку
                            }
                        }

                    }
            }

        }


        //визначення блоків, що використовуються в матриці звязків
        int countBlocksMatrixL = 0;
        List<BlockObj> listblocksMatrixL = new ArrayList<>();

        for (BlockObj obj : allBlocs.values()) {
            if (obj.getType() != BlockTypes.RHOMB) {
                listblocksMatrixL.add(obj);
                countBlocksMatrixL++;
            }
        }
        blocksMatrixL = new BlockObj[countBlocksMatrixL];
        listblocksMatrixL.toArray(blocksMatrixL);

        //конвертування матриці звязків до коректного вигляду
        int IndexI = 0;
        int IndexJ = 0;
        String[][] tmpMatrixL = matrixL.clone();
        matrixL = new String[countBlocksMatrixL][countBlocksMatrixL];
        for (int i = 0; i < size; i++) {
            if (blocksMatrixS[i].getType() != BlockTypes.RHOMB) {
                IndexJ = 0;
                for (int j = 0; j < size; j++) {
                    if (blocksMatrixS[j].getType() != BlockTypes.RHOMB) {
                        if (!tmpMatrixL[i][j].equals(NULL)) {
                            matrixL[IndexI][IndexJ] = tmpMatrixL[i][j];
                        } else
                            matrixL[IndexI][IndexJ] = NULL;
                        IndexJ++;
                    }
                }
                IndexI++;
            }
        }

        //отримання масиву для використяння в адаптері
        return getStringMatrix(matrixL, blocksMatrixL);
    }

    private String searchNotRhombBlock(int from, int curent, int indexRhomb, String mTmp, boolean flag) {

        String tmp = mTmp;
        if (flag)
            tmp += blocksMatrixS[indexRhomb].getText();
        else
            tmp += "(not" + blocksMatrixS[indexRhomb].getText() + ")";

        if (blocksMatrixS[curent].getType() != BlockTypes.RHOMB) {
            matrixL[from][curent] = tmp;
        } else

            for (int k = 0; k < matrixS.length; k++) {
                if (matrixS[curent][k].equals(ONE_TRUE))
                    searchNotRhombBlock(from, k, curent, tmp, true);
                if (matrixS[curent][k].equals(ONE_FALSE))
                    searchNotRhombBlock(from, k, curent, tmp, false);
            }

        return null;
    }


    public String[] getStringMatrix(String[][] input, BlockObj[] blocs) {

        List<String> list = new ArrayList<>();


        String[][] res = input;
        BlockObj[] myblocs = blocs;

        list.add(" ");
        for (int i = 0; i < myblocs.length; i++) {
            list.add(myblocs[i].getText());
        }


        for (int i = 0; i < res.length; i++) {

            list.add(myblocs[i].getText());
            for (int j = 0; j < res.length; j++) {
                list.add(res[i][j]);
            }

        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }


}