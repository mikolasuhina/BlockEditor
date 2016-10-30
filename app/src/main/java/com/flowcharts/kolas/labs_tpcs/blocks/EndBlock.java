package com.flowcharts.kolas.labs_tpcs.blocks;

import com.flowcharts.kolas.labs_tpcs.PointConnect;

/**
 * Created by kolas on 10.09.16.
 */
public class EndBlock extends BlockObj {


    public EndBlock(float x, float y, int width, int height, int color, BlockTypes type) {
        super(x, y, width, height, color, type);
        setText("Кінець");
    }


    @Override
    public PointConnect getOut_Point() {

        return null;
    }
}
