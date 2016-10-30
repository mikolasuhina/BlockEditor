package com.flowcharts.kolas.labs_tpcs.blocks;

import com.flowcharts.kolas.labs_tpcs.PointConnect;

/**
 * Created by kolas on 10.09.16.
 */
public class BeginBlock extends BlockObj {

    public BeginBlock(float x, float y, int width, int height, int color, BlockTypes type) {
        super(x, y, width, height, color, type);
        setText("Початок");
    }

    @Override
    public PointConnect getIn_Point() {
        return null;


    }


}
