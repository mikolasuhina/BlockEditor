package com.example.kolas.lab1_tpcs;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kolas.lab1_tpcs.blocs.BlocTypes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;


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
                new DialogSave(model.saveToFile(),1).show(getFragmentManager(), "DialogSaveDiagram");
                break;

            }
            case R.id.saveGraph: {
                new DialogSave(model.getGraph(),2).show(getFragmentManager(), "DialogSaveGraph");
                break;

            }
            case R.id.show_matrix: {
                model.createGraph();
                new DialogShowMatrix(model.createMatrixSumig(),model.createMatrixLink()).show(getFragmentManager(), "DialogSaveGraph");
                break;

            }
            case R.id.error: {
                new DialogError(model.searchError()).show(getFragmentManager(), "dialogError");
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;
            }
            case R.id.graph: {
                model.createGraph();
          //      model.coddingGraph((int) (Math.log10(model.allGraphsObjs.size())/Math.log10(2)));
                fsurface.draw(MySurfaceView.DRAW_GRAPH);
                break;
            }
            case R.id.open: {
                WHAT_OPEN = 1;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, 1);

                break;

            }
            case R.id.openGraph: {
                WHAT_OPEN = 2;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, 1);
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
            case R.id.state: {
                model.setShowGraph(true);
                model.createGraph();
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                model.setShowGraph(false);
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
            case R.id.update: {
                if (WHAT_OPEN == 1)
                    fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                if (WHAT_OPEN == 2)
                    fsurface.draw(MySurfaceView.DRAW_GRAPH);
                break;

            }
        }
    }


    public void showDialogRhomb() {
        DialogSelPointRhomb dialogSelPointRhomb = new DialogSelPointRhomb(this);
        dialogSelPointRhomb.show(getFragmentManager(), "dialog");
    }

    static int WHAT_OPEN;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            String spath;
            Cursor c = getContentResolver().query(Uri.parse(data.getData().toString()), null, null, null, null);
            if (c != null) {
                c.moveToNext();

                spath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                c.close();
            } else spath = data.getData().getPath();
            Toast.makeText(context, spath, Toast.LENGTH_SHORT).show();
            if (WHAT_OPEN == 1)
                new Parsing().execute(readFileSD(spath));
            if (WHAT_OPEN == 2)
                new Parsing().execute(spath);


        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "EROR", Toast.LENGTH_LONG).show();
        }

    }

    String readFileSD(String path) {
        String str = "";
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return null;
        }

        File sdFile = new File(path);
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

    public class Parsing extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            if (WHAT_OPEN == 1)
                model.parseFile(params[0]);
            if (WHAT_OPEN == 2)
                read(params[0]);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (WHAT_OPEN == 1)
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
            if (WHAT_OPEN == 2)
                fsurface.draw(MySurfaceView.DRAW_GRAPH);
        }
    }


    void read(String path) {
        ObjectInputStream is = null;
        MyGraph myGraph = null;
        try {
            is = new ObjectInputStream(new FileInputStream(path));
            myGraph = (MyGraph) is.readObject();
            model.blocsUseInGraph = myGraph.mGraphObjs;
            model.matrixL = myGraph.allGraphLinks;

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}


