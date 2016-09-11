package com.example.kolas.lab1_tpcs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.example.kolas.lab1_tpcs.MainActivity;

public class MySurfaceThread extends Thread {

    private SurfaceHolder myThreadSurfaceHolder;
    private MySurfaceView myThreadSurfaceView;
    private boolean myThreadRun = false;
    boolean pause;

    public MySurfaceThread(SurfaceHolder surfaceHolder,
                           MySurfaceView surfaceView) {
        myThreadSurfaceHolder = surfaceHolder;
        myThreadSurfaceView = surfaceView;
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // super.run();
        while (myThreadRun) {
            while (pause){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Canvas c = null;
            try {
                c = myThreadSurfaceHolder.lockCanvas(null);
                synchronized (myThreadSurfaceHolder) {
                    c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    myThreadSurfaceView.drawline(c);

                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    myThreadSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}