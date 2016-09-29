package com.example.kolas.lab1_tpcs;

import android.graphics.Color;
import android.text.Editable;
import android.util.Log;

import com.example.kolas.lab1_tpcs.blocs.BeginBlock;
import com.example.kolas.lab1_tpcs.blocs.BlocObj;
import com.example.kolas.lab1_tpcs.blocs.BlocTypes;
import com.example.kolas.lab1_tpcs.blocs.EndBloc;
import com.example.kolas.lab1_tpcs.blocs.ReckBloc;
import com.example.kolas.lab1_tpcs.blocs.RhombBloc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by kolas on 10.09.16.
 */
public class Model {
    int id_counter;
    int IdCrossing;
    HashMap<Integer, BlocObj> allBlocs;
    HashMap<Integer, Link> allLinks;
    BlocObj thisBloc;
    Link thisLinc;
    public static final String TAG = "Mylogs";
    ArrayList<SimpleArrow> thisArrows;
    SimpleArrow thisSimpleArrow;
    MySurfaceView mySurfaceView;
    MainActivity mainActivity;
    boolean flag;

    float centers;

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

    float centerL;
    float centerR;

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

    private void addNewBloc(BlocObj newBloc) {
        allBlocs.put(newBloc.getId(), newBloc);
    }

    private void addNewLinc(Link newLinc) {
        allLinks.put(newLinc.getId(), newLinc);
    }

    void addingNewBloc(BlocTypes type, float x, float y) {
        int w = 100;
        int h = 100;
        if (thisBloc != null)
            thisBloc.setColor(Color.WHITE);
        switch (type) {
            case RECT: {
                thisBloc = new ReckBloc(x, y, w, h, Color.GREEN, type);
                break;
            }
            case BEGIN: {
                thisBloc = new BeginBlock(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case END: {
                thisBloc = new EndBloc(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case RHOMB: {
                thisBloc = new RhombBloc(x, y, w, h, Color.GREEN, type);
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
        for (BlocObj obj : allBlocs.values()) {
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
        for (BlocObj obj : allBlocs.values()) {
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

    private void searchThisBlocCrossing() {

        thisBloc = allBlocs.get(IdCrossing);

        coloring();
        if (thisBloc.getType() == BlocTypes.RHOMB && flag)
            mainActivity.showDialogRhomb();


    }

    BlocObj getCollBloc(int id) {
        for (BlocObj blok : allBlocs.values()) {
            if (blok.getId() == id)
                return blok;
        }
        return null;
    }


    void addingNewLinc() {
        PointLink pl = thisBloc.getOut_Point();
        thisSimpleArrow = new SimpleArrow(pl.getX(), pl.getY());
        thisArrows = new ArrayList<>();
        if (thisBloc.getType() != BlocTypes.RHOMB)
            thisLinc = new Link(thisBloc.getId());

        flag = false;

    }

    boolean setBlocFrom(float x, float y) {

        if (thisBloc != null && thisBloc.getType() != BlocTypes.END) {
            if (thisBloc.getType() != BlocTypes.RHOMB) {
                mainActivity.isblocfrom = true;
                if (!thisBloc.getOut_Point().isUse())
                    thisBloc.getOut_Point().setUse(true);
            }

            //  flag = true;
            return true;
        } else
            checkThisBloc(x, y);
        if (thisBloc != null)
            if (thisBloc.getOut_Point().isUse() && thisBloc.getType() != BlocTypes.RHOMB)
                thisBloc = null;
        return false;
    }

    void searshBlocForConnect() {
        for (BlocObj block : allBlocs.values()) {
            if (block.getType() != BlocTypes.END) {
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
                    if (allBlocs.get(ilinc.getValue().getId_from()).getType() == BlocTypes.RHOMB) {

                        if (ilinc.getValue().isF_point()) {
                            int first = ((RhombBloc) allBlocs.get(ilinc.getValue().getId_from())).getFirst();
                            ((RhombBloc) allBlocs.get(ilinc.getValue().getId_from())).setFirst(RhombBloc.FREE);
                            ((RhombBloc) allBlocs.get(ilinc.getValue().getId_from())).getOutPoints().get(first).setUse(false);
                        } else {
                            int first = ((RhombBloc) allBlocs.get(ilinc.getValue().getId_from())).getSecond();
                            ((RhombBloc) allBlocs.get(ilinc.getValue().getId_from())).setSecond(RhombBloc.FREE);
                            ((RhombBloc) allBlocs.get(ilinc.getValue().getId_from())).getOutPoints().get(first).setUse(false);
                        }


                    } else
                        allBlocs.get(ilinc.getValue().getId_from()).getOut_Point().setUse(false);
                }


            }

            boolean useOutPoin = false;
            if (idrhomb != -1){
                for (Link link : allLinks.values()) {
                if (link.getId_to() == id)
                    useOutPoin = true;
            }

            allBlocs.get(id).getIn_Point().setUse(useOutPoin);}
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
                List<BlocObj> keys = new ArrayList<BlocObj>(allBlocs.values());
                thisBloc = keys.get(0);
                coloring();
            } else thisBloc = null;

        }
    }


    public void setText(String s) {
        if (thisBloc.getType() != BlocTypes.END && thisBloc.getType() != BlocTypes.BEGIN)
            thisBloc.setText(s);
    }

    public void deleteLink() {
        int id = 0;
        if (thisBloc != null) {

            if (thisBloc.getType() != BlocTypes.RHOMB) {
                Iterator<Map.Entry<Integer, Link>> linc = allLinks.entrySet().iterator();
                while (linc.hasNext()) {
                    Map.Entry<Integer, Link> ilinc = linc.next();
                    if (ilinc.getValue().getId_from() == thisBloc.getId()) {

                        linc.remove();
                        id = ilinc.getValue().getId_to();
                        if (thisBloc.getType() != BlocTypes.END)
                            thisBloc.getOut_Point().setUse(false);

                    }

                }
            } else
                new DialogSelPointRhombDelete(mainActivity).show(mainActivity.getFragmentManager(), "dhsaj");


            if (thisBloc.getType() != BlocTypes.RHOMB) {
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
        for (BlocObj o : allBlocs.values()) {
            res += "   " + getBlocTag(o);
        }
        return res + "\n";
    }

    String getBlocTag(BlocObj o) {
        String res = "<bloc>\n";
        res += "        <type>" + o.getType() + "</type>\n";
        res += "        <x>" + o.getX() + "</x>\n";
        res += "        <y>" + o.getY() + "</y>\n";
        res += "        <id>" + o.getId() + "</id>\n";
        res += "        <text>" + o.getText() + "</text>\n";
        if (o.getType() != BlocTypes.BEGIN) {
            res += "        <in>" + o.getIn_Point().isUse() + "</in>\n";
        }
        if (o.getType() != BlocTypes.END) {
            res += "        <out>" + getOutPoint(o) + "</out>\n";
        }

        res += "   </bloc>\n";


        return res;
    }

    String getOutPoint(BlocObj o) {
        String res = "\n";
        if (o.getType() == BlocTypes.RHOMB) {
            res += "<first>" + ((RhombBloc) o).getFirst() + "</first>\n";
            res += "<second>" + ((RhombBloc) o).getSecond() + "</second>\n";
            res += "<number>" + ((RhombBloc) o).getNuberPointLink() + "</number>\n";
            res += "<useR>" + ((RhombBloc) o).getPointR().isUse() + "</useR>\n";
            res += "<useL>" + ((RhombBloc) o).getPointL().isUse() + "</useL>\n";
            res += "<useB>" + ((RhombBloc) o).getPointB().isUse() + "</useB>\n";
            res += "<tfR>" + ((RhombBloc) o).getPointR().isType_for_rhomh() + "</tfR>\n";
            res += "<tfL>" + ((RhombBloc) o).getPointL().isType_for_rhomh() + "</tfL>\n";
            res += "<tfB>" + ((RhombBloc) o).getPointB().isType_for_rhomh() + "</tfB>\n";
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

    void parseBlocs(String s) {
        ArrayList<String> strings = getTagsArray(s, "<bloc>");
        for (String tmp : strings) {
            parseBloc(tmp);

        }
    }


    void parseBloc(String bloc) {
        float x = Float.parseFloat(getTagString(bloc, "<x>"));
        float y = Float.parseFloat(getTagString(bloc, "<y>"));
        int id = Integer.parseInt(getTagString(bloc, "<id>"));
        BlocTypes type = getTypeBloc(getTagString(bloc, "<type>"));
        String text = getTagString(bloc, "<text>");


        int w = 100;
        int h = 100;
        if (thisBloc != null)
            thisBloc.setColor(Color.WHITE);
        switch (type) {
            case RECT: {
                thisBloc = new ReckBloc(x, y, w, h, Color.GREEN, type);
                break;
            }
            case BEGIN: {
                thisBloc = new BeginBlock(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case END: {
                thisBloc = new EndBloc(x, y, w, h / 2, Color.GREEN, type);
                break;
            }
            case RHOMB: {
                thisBloc = new RhombBloc(x, y, w, h, Color.GREEN, type);
                break;
            }
        }

        coloring();

        while (id_counter <= id)
            id_counter++;
        thisBloc.setId(id);
        thisBloc.setText(text);
        if (type != BlocTypes.BEGIN) {
            boolean in = Boolean.parseBoolean(getTagString(bloc, "<in>"));
            thisBloc.getIn_Point().setUse(in);
        }

        if (type != BlocTypes.END) {
            String out = getTagString(bloc, "<out>");
            boolean use = Boolean.parseBoolean(getTagString(out, "<use>"));
            thisBloc.getOut_Point().setUse(use);
            if (type == BlocTypes.RHOMB) {
                int second = Integer.parseInt(getTagString(out, "<second>"));
                int fisrs = Integer.parseInt(getTagString(out, "<first>"));
                int use_numper = Integer.parseInt(getTagString(out, "<number>"));
                boolean useR = Boolean.parseBoolean(getTagString(out, "<useR>"));
                boolean useL = Boolean.parseBoolean(getTagString(out, "<useL>"));
                boolean useB = Boolean.parseBoolean(getTagString(out, "<useB>"));

                boolean tfR = Boolean.parseBoolean(getTagString(out, "<tfR>"));
                boolean tfL = Boolean.parseBoolean(getTagString(out, "<tfL>"));
                boolean tfB = Boolean.parseBoolean(getTagString(out, "<tfB>"));
                ((RhombBloc) thisBloc).setFirst(fisrs);
                ((RhombBloc) thisBloc).setSecond(second);

                ((RhombBloc) thisBloc).setNuberPointLink(use_numper);

                ((RhombBloc) thisBloc).getPointR().setUse(useR);
                ((RhombBloc) thisBloc).getPointL().setUse(useL);
                ((RhombBloc) thisBloc).getPointB().setUse(useB);

                ((RhombBloc) thisBloc).getPointR().setType_for_rhomh(tfR);
                ((RhombBloc) thisBloc).getPointL().setType_for_rhomh(tfL);
                ((RhombBloc) thisBloc).getPointB().setType_for_rhomh(tfB);
            }
        }

        addNewBloc(thisBloc);

    }

    BlocTypes getTypeBloc(String s) {
        s.trim();
        if (s.equals(BlocTypes.BEGIN.toString()))
            return BlocTypes.BEGIN;
        if (s.equals(BlocTypes.END.toString()))
            return BlocTypes.END;
        if (s.equals(BlocTypes.RECT.toString()))
            return BlocTypes.RECT;
        if (s.equals(BlocTypes.RHOMB.toString()))
            return BlocTypes.RHOMB;
        return null;

    }

    void parseLincs(String s) {
        ArrayList<String> strings = getTagsArray(s, "<link>");
        for (String tmp : strings) {
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
        while (id_counter <= id) ;
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
            newText.add(new String(bufer.substring(closeTag.length(), end)));
            bufer = new String(bufer.substring(end + closeTag.length()));
            if (bufer.length() <= closeTag.length())
                break;
        }

        return newText;

    }

    boolean contentBloc(BlocTypes type) {
        for (BlocObj bloc : allBlocs.values()
                ) {
            if (bloc.getType() == type)
                return true;

        }
        return false;
    }

    ArrayList<String> searchError() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (!contentBloc(BlocTypes.BEGIN))
            arrayList.add("Відсутній блок \"Початок\"");
        if (!contentBloc(BlocTypes.END))
            arrayList.add("Відсутній блок \"Кінець\"");
        for (BlocObj bloc : allBlocs.values()) {
            switch (bloc.getType()) {
                case BEGIN: {
                    if (!bloc.getOut_Point().isUse()) {
                        arrayList.add("Блок " + BlocTypes.BEGIN + "(" + bloc.getText() + ") висячий");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }
                case END: {
                    if (!bloc.getIn_Point().isUse()) {
                        arrayList.add("Блок " + BlocTypes.END + "(" + bloc.getText() + ") недосяжний");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }
                case RECT: {
                    if (!bloc.getOut_Point().isUse()) {
                        arrayList.add("Блок " + BlocTypes.RECT + "(" + bloc.getText() + ") висячий");
                        bloc.setColor(Color.RED);
                    }

                    if (!bloc.getIn_Point().isUse()) {
                        arrayList.add("Блок " + BlocTypes.RECT + "(" + bloc.getText() + ") недосяжний");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }
                case RHOMB: {
                    int use = 0;
                    for (PointLink point : ((RhombBloc) bloc).getOutPoints()
                            ) {
                        if (point.isUse())
                            use++;

                    }
                    if (use < 1) {
                        arrayList.add("Блок " + BlocTypes.RHOMB + "(" + bloc.getText() + ") не містить жодного виходу");
                        bloc.setColor(Color.RED);
                    }
                    if (use == 1) {
                        arrayList.add("Блок " + BlocTypes.RHOMB + "(" + bloc.getText() + ")  містить тільки 1  вихід");
                        bloc.setColor(Color.RED);
                    }
                    if (!bloc.getIn_Point().isUse()) {
                        arrayList.add("Блок " + BlocTypes.RHOMB + "(" + bloc.getText() + ") недосяжний");
                        bloc.setColor(Color.RED);
                    }
                    break;
                }


            }
        }


        if (arrayList.size() == 0)
            arrayList.add("Помилки відсутні");
        return arrayList;
    }

}

