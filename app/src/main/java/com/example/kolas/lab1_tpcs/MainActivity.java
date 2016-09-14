package com.example.kolas.lab1_tpcs;


import android.content.Context;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;
import com.example.kolas.lab1_tpcs.blocs.BlocTypes;
import com.example.kolas.lab1_tpcs.blocs.ReckBloc;
import com.example.kolas.lab1_tpcs.blocs.RhombBloc;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    FrameLayout frame;
    Button b;
    Button b1;
    Button button;
    public static boolean isblocfrom;
    public static final String TAG = "Mylogs";
    EditText text;
    public static float nx, ny = 0;
LinearLayout bar;
    Context context;

    public static boolean arrow = false;
    MySurfaceView fsurface;

    Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = (FrameLayout) findViewById(R.id.frame);
        model = new Model(this);
        text = (EditText) findViewById(R.id.editText);
        fsurface = new MySurfaceView(this, model);
        fsurface.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frame.addView(fsurface);
        fsurface.setOnTouchListener(this);
        context = this;

         bar = (LinearLayout) findViewById(R.id.bar);



    }

    public static boolean isArrow() {
        return arrow;
    }

    public static void setArrow(boolean arrow) {
        MainActivity.arrow = arrow;
    }

    public boolean isblocfrom() {
        return isblocfrom;
    }

    public void setIsblocfrom(boolean isblocfrom) {
        this.isblocfrom = isblocfrom;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        nx = x;
        ny = y;
        if (arrow) {
            if (model.setBlocFrom(x, y)) {
                if (model.flag)

                    model.addingNewLinc();

            }
            if (isblocfrom) {
                model.searchBtocTo(x, y);

            }


        } else {
            if(model.thisBloc!=null){
            model.checkThisBloc(x, y);
            model.setPosForThisBloc(x, y);
            }

        }
        fsurface.draw();
        return true;

    }

    @Override
    public void onClick(View v) {
        model.setCenters(frame.getWidth() / 2);
        model.setCenterL(model.getCenters()-150);
        model.setCenterR(model.getCenters()+150);
        switch (v.getId()) {
            case R.id.line: {

                model.setLinkParam();
                arrow = true;

                break;
            }
            case R.id.addrect: {
                model.addingNewBloc(BlocTypes.RECT, 0, 0, 100, 100);

                break;

            }
            case R.id.rhomb: {
                model.addingNewBloc(BlocTypes.RHOMB, 0, 0, 100, 100);

                break;

            }
            case R.id.end: {
                model.addingNewBloc(BlocTypes.END, 0, 0, 100, 50);
                break;

            }
            case R.id.begin: {
                model.addingNewBloc(BlocTypes.BEGIN, 0, 0, 100, 50);
                break;

            }

            case R.id.clear: {
                model.clear();

                fsurface.draw();
                break;

            }
            case R.id.delete: {
                model.delete();

                fsurface.draw();
                break;

            }
            case R.id.d_linc: {
                model.deleteLink();

                fsurface.draw();
                break;

            }
            case R.id.settext: {
                model.setText(String.valueOf(text.getText()));

                fsurface.draw();
                break;

            }
            case R.id.menu: {
                if(bar.isShown()){
                    bar.setVisibility(View.GONE);

                }
                else bar.setVisibility(View.VISIBLE);
                break;

            }
        }
    }


    public void showDialogRhomb() {
        DialogSelPointRhomb dialogSelPointRhomb = new DialogSelPointRhomb(this);
        dialogSelPointRhomb.show(getFragmentManager(), "dialog");
    }




}


