package com.example.kolas.lab1_tpcs;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    FrameLayout frame;
    Button b;
    DialogEditName editName;
    final String LOG_TAG = "myLogs";
    public static boolean isblocfrom;
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
            if (model.thisBloc != null) {
                model.checkThisBloc(x, y);
                model.setPosForThisBloc(x, y);
            }

        }
        fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
        return true;

    }

    @Override
    public void onClick(View v) {
        model.setCenters(frame.getWidth() / 2);
        model.setCenterL(model.getCenters() - 150);
        model.setCenterR(model.getCenters() + 150);
        switch (v.getId()) {
            case R.id.line: {
                model.searshBlocForConnect();
                model.setLinkParam();
                arrow = true;
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;
            }
            case R.id.addrect: {
                model.addingNewBloc(BlocTypes.RECT, 0, 0);

                break;

            }
            case R.id.rhomb: {
                model.addingNewBloc(BlocTypes.RHOMB, 0, 0);

                break;

            }
            case R.id.end: {
                if (model.contentBloc(BlocTypes.END))
                    Toast.makeText(this, "Блок такого типу вже додано", Toast.LENGTH_LONG).show();
                else
                    model.addingNewBloc(BlocTypes.END, 0, 0);
                break;

            }
            case R.id.begin: {
                if (model.contentBloc(BlocTypes.BEGIN))
                    Toast.makeText(this, "Блок такого типу вже додано", Toast.LENGTH_LONG).show();
                else
                    model.addingNewBloc(BlocTypes.BEGIN, 0, 0);
                break;

            }
            case R.id.save: {
                new DialogSave(model.saveToFile()).show(getFragmentManager(), "DialogSave");
                break;

            }
            case R.id.error: {
                new DialogError(model.searchError()).show(getFragmentManager(), "dialog");
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;
            }
            case R.id.graph: {
                model.createGraph();
                fsurface.draw(MySurfaceView.DRAW_GRAPH);
                break;
            }
            case R.id.open: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, 5);
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                ;
                break;

            }

            case R.id.clear: {
                model.clear();
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;

            }
            case R.id.delete: {
                model.delete();

                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;

            }
            case R.id.d_linc: {
                model.deleteLink();
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;

            }
            case R.id.settext: {
                editName = new DialogEditName(model.thisBloc);
                editName.show(getFragmentManager(), "Dialognew Name");
                break;

            }
            case R.id.menu: {
                if (bar.isShown()) {
                    bar.setVisibility(View.GONE);

                } else bar.setVisibility(View.VISIBLE);
                break;

            }
        }
    }


    public void showDialogRhomb() {
        DialogSelPointRhomb dialogSelPointRhomb = new DialogSelPointRhomb(this);
        dialogSelPointRhomb.show(getFragmentManager(), "dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            final Intent d = data;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    model.parseFile(readFileSD(d.getData()));
                    fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                }
            }).start();


        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "EROR", Toast.LENGTH_LONG).show();
        }

    }

    String readFileSD(Uri path) {
        String str = "";
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return null;
        }
        // получаем путь к SD

        // добавляем свой каталог к пути

        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(path.getPath());
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));

            // читаем содержимое
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                str += tmp;
                Log.d("tag", tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


}


