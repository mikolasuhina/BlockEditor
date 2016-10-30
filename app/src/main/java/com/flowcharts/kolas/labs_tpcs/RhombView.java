package com.flowcharts.kolas.labs_tpcs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;

import com.flowcharts.kolas.labs_tpcs.blocks.BlockObj;
import com.flowcharts.kolas.labs_tpcs.blocks.RhombBlock;

/**
 * Created by kolas on 12.09.16.
 */
public class RhombView extends View {
    RhombBlock obj;
    float w_h;
    float a = 400;
    float x_y;
    int thisPointNumber;

    public RhombView(Context context, BlockObj obj, boolean use_type) {
        super(context);
        this.obj = (RhombBlock) obj;
        w_h = (float) (a / Math.sqrt(2));
        x_y = (float) (a * Math.sqrt(2) - a) / 2;
        thisPointNumber = getNotUsePointBloc(use_type);
        setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public int getThisPointNumber() {
        return thisPointNumber;
    }

    public void setThisPointNumber(int thisPointNumber) {
        this.thisPointNumber = thisPointNumber;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();


        switch (thisPointNumber) {
            case 0: {
                canvas.rotate(225, a / 2, a / 2);

                break;
            }
            case 1: {
                canvas.rotate(135, a / 2, a / 2);

                break;
            }
            case 2: {
                canvas.rotate(315, a / 2, a / 2);

                break;
            }
            default:
                canvas.rotate(225, a / 2, a / 2);


        }

        canvas.drawRect(x_y, x_y, w_h, w_h, p);
        p.setColor(Color.GREEN);
        canvas.drawCircle(x_y, x_y, 10, p);

    }

    private int getNotUsePointBloc(boolean use_type) {
        for (int i = 0; i < obj.getOutPoints().size(); i++) {
            if (obj.getOutPoints().get(i).isUse() == use_type)
                return i;

        }
        return 0;
    }
}
