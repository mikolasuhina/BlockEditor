package com.example.kolas.lab1_tpcs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Switch;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;
import com.example.kolas.lab1_tpcs.blocs.BlocTypes;
import com.example.kolas.lab1_tpcs.blocs.ReckBloc;
import com.example.kolas.lab1_tpcs.blocs.RhombBloc;

import java.util.ArrayList;

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {



    Model model;

    void drawline(Canvas canvas) {

        Paint p = new Paint();
        p.setColor(Color.WHITE);


        for (BlocObj blocObj : model.allBlocs.values()) {


                switch (blocObj.getType()) {
                    case RECT: {
                        p.setColor(blocObj.getColor());
                        canvas.drawRect(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight(), p);
                        if (blocObj.getIn_Point() != null)
                            canvas.drawLine(blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY(), blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY() + blocObj.getHeight() / 4, p);
                        if (blocObj.getOut_Point() != null)
                            canvas.drawLine(blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY(), blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY() - blocObj.getHeight() / 4, p);
                        break;
                    }
                    case RHOMB: {
                        p.setColor(blocObj.getColor());
                        canvas.rotate(45, blocObj.getX() + blocObj.getWidth() / 2, blocObj.getY() + blocObj.getHeight() / 2);
                        canvas.drawRect(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight(), p);
                        canvas.rotate(-45, blocObj.getX() + blocObj.getWidth() / 2, blocObj.getY() + blocObj.getHeight() / 2);
                        if (blocObj.getIn_Point() != null)
                            canvas.drawLine(blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY(), blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY() + blocObj.getHeight() / 4, p);


                        RhombBloc rb = (RhombBloc) blocObj;
                        // canvas.drawLine(blocObj.getOut_Point().getX(),blocObj.getOut_Point().getY(),blocObj.getOut_Point().getX(),blocObj.getOut_Point().getY()-blocObj.getHeight()/4,p);
                        canvas.drawLine(rb.getOutPoints().get(0).getX(), rb.getOutPoints().get(0).getY(), rb.getOutPoints().get(0).getX(), rb.getOutPoints().get(0).getY() - blocObj.getHeight() / 4, p);
                        canvas.drawLine(rb.getOutPoints().get(1).getX(), rb.getOutPoints().get(1).getY(), rb.getOutPoints().get(1).getX() - blocObj.getHeight() / 4, rb.getOutPoints().get(1).getY(), p);
                        canvas.drawLine(rb.getOutPoints().get(2).getX(), rb.getOutPoints().get(2).getY(), rb.getOutPoints().get(2).getX() + blocObj.getHeight() / 4, rb.getOutPoints().get(2).getY(), p);
                        break;
                    }
                    default: {
                        p.setColor(blocObj.getColor());
                        final RectF rect = new RectF();
                        rect.set(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight());
                        canvas.drawRoundRect(rect, 20, 20, p);
                        if (blocObj.getIn_Point() != null)
                            canvas.drawLine(blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY(), blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY() + blocObj.getHeight() / 4, p);
                        if (blocObj.getOut_Point() != null)
                            canvas.drawLine(blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY(), blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY() - blocObj.getHeight() / 4, p);
                        break;
                    }


                }
                p.setColor(Color.RED);
                p.setTextAlign(Paint.Align.CENTER);
                p.setTextSize(20);
                canvas.drawText(blocObj.getText(), blocObj.getX() + blocObj.getWidth() / 2, blocObj.getY() + blocObj.getHeight() / 2, p);
            }

        p.setColor(Color.YELLOW);

        if (MainActivity.arrow && MainActivity.isblocfrom) {
            for (SimpleArrow s : model.thisArrows
                    ) {
                canvas.drawLine(s.getX_from(), s.getY_from(), s.getX_to(), s.getY_to(), p);

            }
            canvas.drawLine(model.thisSimpleArrow.getX_from(), model.thisSimpleArrow.getY_from(), MainActivity.nx, MainActivity.ny, p);
        }


        for (Link sarrow : model.allLinks.values()) {


            if (model.allBlocs.get(sarrow.getId_from()).getType() == BlocTypes.RHOMB) {

                if ((sarrow.f_point)) {
                    ((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).setNuberPointLink(((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).getFirst());

                } else if (((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).getSecond() != 100) {
                    ((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).setNuberPointLink(((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).getSecond());
                }
            }

            model.drawnewLineIn(model.allBlocs.get(sarrow.getId_from()).getOut_Point().getX(), model.allBlocs.get(sarrow.getId_from()).getOut_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), sarrow.getArrows());
            canvas.drawLine(model.allBlocs.get(sarrow.getId_from()).getOut_Point().getX(), model.allBlocs.get(sarrow.getId_from()).getOut_Point().getY(), sarrow.arrows.get(0).getX_from(), sarrow.arrows.get(0).getY_from(), p);


            for (SimpleArrow sa : sarrow.getArrows()) {

                canvas.drawLine(sa.getX_from(), sa.getY_from(), sa.getX_to(), sa.getY_to(), p);
            }


            drawArrow(model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(),5,model.allBlocs.get(sarrow.getId_to()).getHeight()/4,model.allBlocs.get(sarrow.getId_to()).getType(),canvas,p);
            canvas.drawLine(sarrow.arrows.get(sarrow.arrows.size() - 1).getX_to(), sarrow.arrows.get(sarrow.arrows.size() - 1).getY_to(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), p);
            // canvas.drawLine( model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX()-10, model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY()-10,p);
            //.drawLine( model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX()+10, model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY()-10,p);
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

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }



public void draw(){
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

private void drawArrow(float x ,float y, float dx, float dy, BlocTypes type ,Canvas c,Paint p){
    switch(type) {
        case END:
            c.drawLine(x, y + dy, x - dx, y, p);
            c.drawLine(x, y + dy, x + dx, y, p);
            break;
        case RECT:
            c.drawLine(x, y + dy, x - dx, y + dy / 2, p);
            c.drawLine(x, y + dy, x + dx, y + dy / 2, p);
            break;
        case RHOMB:
            c.drawLine(x, y , x - dx, y - dy / 2, p);
            c.drawLine(x, y , x + dx, y - dy / 2, p);
            break;
    }
}

}
