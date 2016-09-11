package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

import java.util.ArrayList;

/**
 * Created by kolas on 10.09.16.
 */
public class ReckBloc extends BlocObj {

   

    PointLink in_point;
    PointLink out_point;

    public ReckBloc(float x, float y, int width, int height) {
        super(x, y, width, height);
        in_point =new PointLink(getX()+getWidth()/2,getY(),true);
        out_point=new PointLink(getX()+getWidth()/2,getY()+getHeight(),false);
    }

    @Override
    public PointLink getIn_Point() {
     
        in_point.setX(getX()+getWidth()/2);
        in_point.setY(getY()-getHeight()/4);

        return in_point;
    }

    @Override
    public PointLink getOut_Point() {
        out_point.setX(getX()+getWidth()/2);
        out_point.setY(getY()+getHeight()+getHeight()/4);
        return out_point;
    }


}
