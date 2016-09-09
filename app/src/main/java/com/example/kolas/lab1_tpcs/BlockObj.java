package com.example.kolas.lab1_tpcs;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.widget.Button;

/**
 * Created by kolas on 09.09.16.
 */
public class BlockObj extends Button{
    float x_center,x_center_r,x_center_reft,y_center_top,y_center_bottom,y_center;



    public BlockObj(Context context,int height,int width) {
        super(context);
        setLayoutParams(new ActionBar.LayoutParams(width,height));
        setHeight(height);
        setWidth(width);
    }



    public float getX_center() {
        setX_center();
        return x_center;
    }


    public void setX_center() {
       x_center =getX()+getWidth()/2;
    }




    public float getX_center_r() {
        setX_center_r();
        return x_center_r;
    }

    public void setX_center_r() {

        this.x_center_r = getX()+getWidth();
    }

    public float getX_center_reft() {
        setX_center_reft();
        return x_center_reft;
    }

    public void setX_center_reft() {

        this.x_center_reft = getX();
    }

    public float getY_center_top() {
        setY_center_top();
        return y_center_top;
    }

    public void setY_center_top() {

        this.y_center_top = getY();
    }

    public float getY_center_bottom() {
        setY_center_bottom();
        return y_center_bottom;
    }

    public void setY_center_bottom() {

        this.y_center_bottom = getY()+getHeight();
    }

    public float getY_center() {
        setY_center();
        return y_center;
    }

    public void setY_center() {
        this.y_center = getY()+getHeight()/2;
    }
}
