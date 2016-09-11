package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

import java.util.ArrayList;

/**
 * Created by kolas on 10.09.16.
 */
public class RhombBloc extends BlocObj {



    PointLink in_point;
    PointLink out_point;

    public RhombBloc(float x, float y, int width, int height) {
        super(x, y, width, height);
        in_point =new PointLink(getX()+getWidth()/2,getY(),true);
        out_point=new PointLink(getX()+getWidth()/2,getY()+getHeight()/2,false);

    }

    @Override
    public PointLink getIn_Point() {
        in_point.setX(getX()+getWidth()/2);
        in_point.setY(getY());
        return in_point;
    }

    @Override
    public PointLink getOut_Point() {
       out_point.setX(getX()+getWidth()/2);
        out_point.setY(getY()+getHeight()/2);
        return out_point;
    }
}
