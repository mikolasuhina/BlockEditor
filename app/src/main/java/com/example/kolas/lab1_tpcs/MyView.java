package com.example.kolas.lab1_tpcs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;
import com.example.kolas.lab1_tpcs.blocs.RhombBloc;

/**
 * Created by kolas on 12.09.16.
 */
public class MyView extends View {
    RhombBloc obj;
    float w_h;
    float a = 400;
    float x_y;
    int thisPointNumber;

    public MyView(Context context, BlocObj obj) {
        super(context);
        this.obj = (RhombBloc) obj;
        w_h = (float) (a / Math.sqrt(2));
        x_y = (float) (a * Math.sqrt(2) - a) / 2;
        thisPointNumber = getNotUsePointBloc();
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
        }

        canvas.drawRect(x_y, x_y, w_h, w_h, p);
        p.setColor(Color.GREEN);

        canvas.drawCircle(x_y, x_y, 10, p);


        // canvas.rotate(45,a/2,a/2);

    }

    private int getNotUsePointBloc() {
        for (int i = 0; i < obj.getOutPoints().size(); i++) {
            if (!obj.getOutPoints().get(i).isUse())
                return i;

        }
        return 0;
    }
}
