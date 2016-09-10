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
import android.util.Log;
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
    public static float nx,ny=0;
  public static   ArrayList<BlockObj> allobj;
    Context context;
    Canvas canvas;
 public  static    boolean arrow = false;
    MySurfaceView fsurface;
    Paint p;
    BlockObj lincObj;

    public  static ArrayList<SimpleArrow> lines=new ArrayList<>();
    public  static ArrayList<SimpleArrow> points;
   public static SimpleArrow sa;
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
        points=new ArrayList<>();

sa=new SimpleArrow(0,0);
        p=new Paint();





    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(arrow){
          //  points.add(new SimpleArrow(x,y));
           // sa=new SimpleArrow(x,y);
           // Log.d("------------","size=-----------------------------------------------------------;"+x);
            nx=x;ny=y;
           if (!col(x,y)){

                calcway(x,y);
                        }else {arrow=false;

           }
        }else {
                thisObj.setX(x);
                thisObj.setY(y);}


        return true;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line:{

                int index=allobj.indexOf(thisObj);
                sa = new SimpleArrow(allobj.get(index).getX_center(),allobj.get(index).getY_center_bottom());
                arrow=true;
               // Toast.makeText(context,"виберіть блок для підключення",Toast.LENGTH_LONG).show();

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

                           // arrow=!arrow;
                        }
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
    private boolean col(float x,float y){

        for (BlockObj obj:allobj
             ) {
            if(x>obj.getX_center_reft()&&x<obj.getX_center_r()&&y>obj.getY_center_top()&&y<obj.getY_center_bottom()){
             lincObj=obj;
                lines.get(lines.size()-1).setX_to(obj.getX_center());
                lines.get(lines.size()-1).setY_to(obj.getY_center());
                MySurfaceView.allArrows.add(new Link(thisObj.getId(),lincObj.getId(),lines));
                thisObj=obj;
                lines= new ArrayList<>();
            return true;}

        }
        return  false;
    }


private  void calcway(float x, float y){
    if(Math.abs((double) (sa.getX_from()-x))>50) {
        sa.setX_to(x);
        sa.setY_to(sa.getY_from());
        sa.setHorizontal(true);
        lines.add(sa);
        sa = new SimpleArrow(x,sa.getY_from());


    }else if(Math.abs((double) (sa.getY_from()-y))>50){
        sa.setX_to(sa.getX_from());
        sa.setY_to(y);
        sa.setHorizontal(false);
        lines.add(sa);
        sa = new SimpleArrow(sa.getX_from(),y);


    }
}

}


