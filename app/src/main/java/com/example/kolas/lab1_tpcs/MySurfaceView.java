package com.example.kolas.lab1_tpcs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {

    private MySurfaceThread thread;
    public static ArrayList<Link> allArrows;
    int x=0;
    float x1,x2,y1,y2;

   void drawline(Canvas canvas) {

       Paint p =new Paint();
       p.setColor(Color.WHITE);

if(MainActivity.arrow){
    for (SimpleArrow s:MainActivity.lines
         ) {
        canvas.drawLine(s.getX_from(),s.getY_from(),s.getX_to(),s.getY_to(),p);

    }
    canvas.drawLine(MainActivity.sa.getX_from(),MainActivity.sa.getY_from(),MainActivity.nx,MainActivity.ny,p);
}


          for (Link sarrow:allArrows) {
              canvas.drawLine(MainActivity.allobj.get(sarrow.getId_from()).getX_center(),MainActivity.allobj.get(sarrow.getId_from()).getY_center_bottom(),sarrow.arrows.get(0).getX_from(),sarrow.arrows.get(0).getY_from(),p);
               for (SimpleArrow sa:sarrow.getArrows()){
                canvas.drawLine(sa.getX_from(),sa.getY_from(),sa.getX_to(),sa.getY_to(),p);
               }
              // canvas.drawLine(sarrow.getX_from(),sarrow.getX_to(),sarrow.getY_from(),sarrow.getY_to(),p);
              canvas.drawLine(sarrow.arrows.get(sarrow.arrows.size()-1).getX_to(),sarrow.arrows.get(sarrow.arrows.size()-1).getY_to(),MainActivity.allobj.get(sarrow.getId_to()).getX_center(),MainActivity.allobj.get(sarrow.getId_to()).getY_center_top(),p);

           }



    }


    public MySurfaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        getHolder().addCallback(this);
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
        thread.start();
        allArrows=new ArrayList<>();
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


    public  void from(int id){


    }
}



