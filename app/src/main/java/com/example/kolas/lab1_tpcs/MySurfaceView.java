package com.example.kolas.lab1_tpcs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;

import java.util.ArrayList;

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {

    private MySurfaceThread thread;

    Model model;

    void drawline(Canvas canvas) {

        Paint p = new Paint();
        p.setColor(Color.WHITE);


    for (BlocObj blocObj : model.allBlocs) {
        canvas.drawRect(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight(), p);
        canvas.drawLine(blocObj.getIn_Point().getX(),blocObj.getIn_Point().getY(),blocObj.getIn_Point().getX(),blocObj.getIn_Point().getY()+blocObj.getHeight()/4,p);
        canvas.drawLine(blocObj.getOut_Point().getX(),blocObj.getOut_Point().getY(),blocObj.getOut_Point().getX(),blocObj.getOut_Point().getY()-blocObj.getHeight()/4,p);
    }

    if (MainActivity.arrow && MainActivity.isblocfrom) {
        for (SimpleArrow s : model.thisArrows
                ) {
            canvas.drawLine(s.getX_from(), s.getY_from(), s.getX_to(), s.getY_to(), p);

        }
        canvas.drawLine(model.thisSimpleArrow.getX_from(), model.thisSimpleArrow.getY_from(), MainActivity.nx, MainActivity.ny, p);
    }


    for (Link sarrow : model.allLinks) {
        model.drawnewLineIn(model.allBlocs.get(sarrow.getId_from()).getOut_Point().getX(), model.allBlocs.get(sarrow.getId_from()).getOut_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(),sarrow.getArrows());
       canvas.drawLine(model.allBlocs.get(sarrow.getId_from()).getOut_Point().getX(), model.allBlocs.get(sarrow.getId_from()).getOut_Point().getY(), sarrow.arrows.get(0).getX_from(), sarrow.arrows.get(0).getY_from(), p);


        for (SimpleArrow sa : sarrow.getArrows()) {

            canvas.drawLine(sa.getX_from(), sa.getY_from(), sa.getX_to(), sa.getY_to(), p);
        }

      canvas.drawLine(sarrow.arrows.get(sarrow.arrows.size() - 1).getX_to(), sarrow.arrows.get(sarrow.arrows.size() - 1).getY_to(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), p);

    }


    }


    public MySurfaceView(Context context, Model model) {
        super(context);
        // TODO Auto-generated constructor stub
        getHolder().addCallback(this);
        this.model = model;

    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                               int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        thread = new MySurfaceThread(getHolder(), this);
        thread.setRunning(true);
     //   thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }



public void drawajjej(){
    Canvas c = null;
    try {
        c = getHolder().lockCanvas(null);
        synchronized (getHolder()) {
            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
           drawline(c);

        }
    } finally {
        // do this in a finally so that if an exception is thrown
        // during the above, we don't leave the Surface in an
        // inconsistent state
        if (c != null) {
         getHolder().unlockCanvasAndPost(c);
        }
    }
}



}
