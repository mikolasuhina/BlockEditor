package com.example.kolas.lab1_tpcs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{
FrameLayout frame;
    Button b;  Button b1;
    BlockObj thisObj;
    int posOfList;
  public static   ArrayList<BlockObj> allobj;
    Context context;
    Canvas canvas;
    boolean arrow = false;
    MySurfaceView fsurface;
    Paint p;
    BlockObj lincObj;

    private boolean drawing = false;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float initX, initY, radius;
    int idObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame= (FrameLayout) findViewById(R.id.frame);
        fsurface=new MySurfaceView(this);
        fsurface.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frame.addView(fsurface);
        fsurface.setOnTouchListener(this);
       // thisObj=b1;
        allobj=new ArrayList<>();
        context=this;


        p=new Paint();





    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

                thisObj.setX(x);
                thisObj.setY(y);



        return true;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line:{

               arrow=true;
                Toast.makeText(context,"виберіть блок для підключення",Toast.LENGTH_LONG).show();

              //fsurface.setCo(allobj.get(0).getX(),allobj.get(0).getY(),allobj.get(1).getX(),allobj.get(1).getY());
                break;
            }
            case R.id.button2: {
                for (BlockObj bloc:allobj
                        ) {
                    bloc.setBackgroundColor(Color.GRAY);

                }
               BlockObj newobj = new BlockObj(this,100,100);

                newobj.setId(idObj);
                newobj.setBackgroundColor(Color.GREEN);


                idObj++;
                newobj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(arrow){
                            lincObj=allobj.get(v.getId());

                            MySurfaceView.allArrows.add(new Link(thisObj.getId(),lincObj.getId()));
                        arrow=!arrow;}
                        else
                            for (BlockObj bloc:allobj
                                    ) {
                                bloc.setBackgroundColor(Color.GRAY);

                            }

                        thisObj=allobj.get(v.getId());
                        thisObj.setBackgroundColor(Color.GREEN);
                       posOfList=v.getId();


                    }

                });
                newobj.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(context,"longPress on button:"+v.getId(),Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                frame.addView(newobj);
                allobj.add(newobj);
                posOfList=allobj.size()-1;
                thisObj=allobj.get(posOfList);


            }
    }
    }

}


