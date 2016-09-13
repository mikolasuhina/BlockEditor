package com.example.kolas.lab1_tpcs.blocs;

import com.example.kolas.lab1_tpcs.PointLink;

/**
 * Created by kolas on 10.09.16.
 */
public class EndBloc extends BlocObj {


    public EndBloc(float x, float y, int width, int height, int color, BlocTypes type) {
        super(x, y, width, height, color, type);
        setText("Кінець");
    }


    @Override
    public PointLink getOut_Point() {

        return null;
    }
}
