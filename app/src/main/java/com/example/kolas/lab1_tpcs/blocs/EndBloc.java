package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

/**
 * Created by kolas on 10.09.16.
 */
public class EndBloc extends BlocObj {

    PointLink in_point;

    public PointLink getIn_point() {
        return in_point;
    }

    public void setIn_point(PointLink in_point) {
        this.in_point = in_point;
    }

    public EndBloc(float x, float y, int width, int height) {
        super(x, y, width, height);

        in_point =new PointLink(getX()+getWidth()/2,getY(),true);


    }

    @Override
    public PointLink getIn_Point() {
        in_point.setX(getX()+getWidth()/2);
        in_point.setY(getY());

        return in_point;

    }

    @Override
    public PointLink getOut_Point() {
        return null;
    }
}
