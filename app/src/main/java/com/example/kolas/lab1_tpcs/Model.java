package com.example.kolas.lab1_tpcs;

import android.graphics.Color;

import com.example.kolas.lab1_tpcs.blocs.BeginBlock;
import com.example.kolas.lab1_tpcs.blocs.BlocObj;
import com.example.kolas.lab1_tpcs.blocs.BlocTypes;
import com.example.kolas.lab1_tpcs.blocs.EndBloc;
import com.example.kolas.lab1_tpcs.blocs.ReckBloc;
import com.example.kolas.lab1_tpcs.blocs.RhombBloc;

import java.util.ArrayList;

/**
 * Created by kolas on 10.09.16.
 */
public class Model {
    int id_counter;
    int IdCrossing;
    ArrayList<BlocObj> allBlocs;
    ArrayList<Link> allLinks;
    BlocObj thisBloc;
    Link thisLinc;
    ArrayList<SimpleArrow> thisArrows;
    SimpleArrow thisSimpleArrow;
    MySurfaceView mySurfaceView;
    MainActivity mainActivity;
    boolean flag;

  float centers;

    public float getCenters() {
        return centers;
    }

    public void setCenters(float centers) {
        this.centers = centers;
    }

    public Model(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        allBlocs=new ArrayList<>();
        allLinks=new ArrayList<>();
        thisArrows=new ArrayList<>();
        mySurfaceView=mainActivity.fsurface;


    }

   private void addNewBloc(BlocObj newBloc){
        allBlocs.add(newBloc);
    }

    private void addNewLinc(Link newLinc){
        allLinks.add(newLinc);
    }

    void addingNewBloc(BlocTypes type, float x, float y, int w, int h){
      if(thisBloc!=null)
          thisBloc.setColor(Color.WHITE);
        switch (type){
            case RECT:{thisBloc = new ReckBloc(x,y,w,h,Color.GREEN,type);
                break;}
            case BEGIN:{thisBloc = new BeginBlock(x,y,w,h,Color.GREEN,type);
                break;}
            case END:{thisBloc = new EndBloc(x,y,w,h,Color.GREEN,type);
                break;}
            case RHOMB:{thisBloc = new RhombBloc(x,y,w,h,Color.GREEN,type);
                break;}
        }
        setIdforBloc();
        coloring();
        addNewBloc(thisBloc);

    }

    private void setIdforBloc(){
        thisBloc.setId(id_counter);
        id_counter++;


    }


    private void coloring(){
        for (BlocObj obj:allBlocs) {
            if(!obj.equals(thisBloc))
                obj.setColor(Color.WHITE);

        }
    }


    void setPosForThisBloc(float x,float y){
        if(Math.abs(centers-x)<100)
        thisBloc.setX(centers-thisBloc.getWidth()/2);
        else  thisBloc.setX(x-thisBloc.getWidth()/2);
        thisBloc.setY(y-thisBloc.getHeight()/2);
    }

   private boolean col(float x,float y){
        for (BlocObj obj:allBlocs) {
            if(x>obj.getX()  &&  x<obj.getX()+obj.getWidth()  &&  y>obj.getY()  &&  y<obj.getY()+obj.getHeight()){
               IdCrossing=obj.getId();
                return true;
            }
        }
        return  false;
    }

    void checkThisBloc(float x, float y){
        if(col(x,y)){
//            setColorForBloc(Color.WHITE);
            searchThisBlocCrossing();
        }

    }

    private  void  searchThisBlocCrossing(){
      thisBloc=allBlocs.get(IdCrossing);
       coloring();
    }
    
    void  addingNewLinc(){
        PointLink pl =thisBloc.getOut_Point();
        thisSimpleArrow = new SimpleArrow(pl.getX(),pl.getY());
        thisArrows=new ArrayList<>();
        thisLinc =new Link(thisBloc.getId())   ;

        flag=false;
        
    }
    
    boolean setBlocFrom(float x, float y){
        if(thisBloc!=null){
            mainActivity.isblocfrom=true;
        return true;}
        else 
           checkThisBloc(x,y);
        return false;
    }
    
    
    void setLinkParam(){
        thisBloc=null;
        flag=true;
    }

    private  void calcway(float x, float y){
        if(Math.abs((double) (thisSimpleArrow.getX_from()-x))>100) {

           thisSimpleArrow.setX_to(x);
           thisSimpleArrow.setY_to(thisSimpleArrow.getY_from());
           thisSimpleArrow.setHorizontal(true);

             addSizeToArrow(true);

            thisSimpleArrow = new SimpleArrow(x,thisSimpleArrow.getY_from());


        }else if(Math.abs((double) (thisSimpleArrow.getY_from()-y))>100){
           thisSimpleArrow.setX_to(thisSimpleArrow.getX_from());
           thisSimpleArrow.setY_to(y);
           thisSimpleArrow.setHorizontal(false);
           addSizeToArrow(false);
           thisSimpleArrow = new SimpleArrow(thisSimpleArrow.getX_from(),y);


        }
    }


    void searchBtocTo(float x ,float y){
        if(!col(x,y)||IdCrossing==thisBloc.getId()){
            calcway(x,y);
        }else {
            searchThisBlocCrossing();
           if(thisArrows.size()>=1)
           if(thisArrows.get(thisArrows.size()-1).isHorizontal())
                thisArrows.get(thisArrows.size()-1).setX_to(thisBloc.getIn_Point().getX());
                else
                thisArrows.get(thisArrows.size()-1).setY_to(thisBloc.getIn_Point().getY());

            else calcThreeArrows();

            thisLinc.setId_to(thisBloc.getId());


            thisLinc.setArrows(thisArrows);
            addNewLinc(thisLinc);

            mainActivity.setArrow(false);
            mainActivity.setIsblocfrom(false);

        }

    }


    void drawnewLineIn(float begin_x, float begin_y,float end_x, float end_y,ArrayList<SimpleArrow> sa){
     if(sa.size()>=1){

      if(sa.get(sa.size()-1).isHorizontal())
          corectHorizontalEnd(end_x,sa);
                  else
          corectVerticalEnd(end_y,sa);
        
        
        if(sa.get(0).isHorizontal()){
         corectHorizontalBegin(begin_x,sa);
        }else corectVerticalBegin(begin_y,sa);

    }}
    private void corectHorizontalEnd(float end_x,ArrayList<SimpleArrow> sa){

        if(sa.size()>=1) {
            sa.get(sa.size() - 1).setX_to(end_x);
         
        }


    }

    private void corectVerticalEnd(float end_y,ArrayList<SimpleArrow> sa){
        if(sa.size()>=1) {

            sa.get(sa.size() - 1).setY_to(end_y);
        }

        }
    private void corectHorizontalBegin(float begin_x,ArrayList<SimpleArrow> sa){

        if(sa.size()>=1){
            sa.get(0).setX_from(begin_x);

        }

    }

    private void corectVerticalBegin( float begin_y,ArrayList<SimpleArrow> sa){
        if(sa.size()>=1) {
            sa.get(0).setY_from(begin_y);
        }

    }
   private void addSizeToArrow(boolean b){

       if((thisArrows.size()>=1&&thisArrows.get(thisArrows.size()-1).isHorizontal()==b)){
           thisArrows.get(thisArrows.size()-1).setX_to(thisSimpleArrow.getX_to());
           thisArrows.get(thisArrows.size()-1).setY_to(thisSimpleArrow.getY_to()) ;
       }
       else
           thisArrows.add(thisSimpleArrow);

   }
public  void  clear(){
    allBlocs.clear();
    allLinks.clear();
    id_counter=0;
    IdCrossing=0;
}


    private void calcThreeArrows(){

        float  newy=(thisSimpleArrow.getY_from()+thisBloc.getIn_Point().getY())/2;


            thisSimpleArrow =new SimpleArrow(thisBloc.getIn_Point().getX(),newy,thisBloc.getIn_Point().getX(),thisBloc.getIn_Point().getY());
            thisSimpleArrow.setHorizontal(false);
            thisArrows.add(thisSimpleArrow);


    }





}
