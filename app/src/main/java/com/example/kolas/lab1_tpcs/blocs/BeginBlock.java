package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

/**
 * Created by kolas on 10.09.16.
 */
public class BeginBlock extends BlocObj {

    public PointLink getOut_point() {
        return out_point;
    }

    public void setOut_point(PointLink out_point) {
        this.out_point = out_point;
    }

    PointLink out_point;

    public BeginBlock(float x, float y, int width, int height) {
        super(x, y, width, height);

        out_point=new PointLink(getX()+getWidth()/2,getY()+getHeight(),false);




    }


    @Override
    public PointLink getIn_Point() {
        return null;


    }

    @Override
    public PointLink getOut_Point() {
        out_point.setX(getX()+getWidth()/2);
        out_point.setY(getY()+getHeight());

        return out_point;
    }
}
