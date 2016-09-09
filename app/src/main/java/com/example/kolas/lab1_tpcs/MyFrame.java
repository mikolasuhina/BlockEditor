package com.example.kolas.lab1_tpcs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.FrameLayout;

/**
 * Created by kolas on 08.09.16.
 */
public class MyFrame extends FrameLayout {
    public MyFrame(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }
}
