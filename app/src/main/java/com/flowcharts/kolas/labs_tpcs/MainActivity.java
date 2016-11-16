package com.flowcharts.kolas.labs_tpcs;


import android.app.Activity;
import android.app.ProgressDialog;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flowcharts.kolas.labs_tpcs.blocks.BlockTypes;
import com.flowcharts.kolas.labs_tpcs.dialogs.DialogEditName;
import com.flowcharts.kolas.labs_tpcs.dialogs.DialogError;
import com.flowcharts.kolas.labs_tpcs.dialogs.DialogSave;
import com.flowcharts.kolas.labs_tpcs.dialogs.DialogShowMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    FrameLayout frame;
    DialogEditName editName;
    final String LOG_TAG = "myLogs";
    public static boolean isblocfrom;
    public static float nx, ny = 0;
    LinearLayout bar;
    Context context;
    public static boolean arrow = false;
    public MySurfaceView fsurface;


    public Model model;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = (FrameLayout) findViewById(R.id.frame);
        model = new Model(this);
        context = this;
        fsurface = new MySurfaceView(this, model);
        fsurface.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frame.addView(fsurface);
        fsurface.setOnTouchListener(this);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        model.setH(dm.widthPixels / 9);
        model.setW(dm.widthPixels / 9);
        model.setCenters(dm.widthPixels / 2);
        model.radius = dm.widthPixels / 3;
        model.setCenterL((float) (model.getCenters() - 2 * model.getH()));
        model.setCenterR((float) (model.getCenters() + 2 * model.getH()));

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

                    model.addingNewLink();

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

        switch (v.getId()) {
            case R.id.line: {
                model.searshBlocForConnect();
                model.setLinkParam();
                arrow = true;
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;
            }
            case R.id.addrect: {
                model.addingNewBloc(BlockTypes.RECT, 0, 0);

                break;

            }
            case R.id.rhomb: {
                model.addingNewBloc(BlockTypes.RHOMB, 0, 0);

                break;

            }
            case R.id.end: {
                if (model.contentBloc(BlockTypes.END))
                    Toast.makeText(this, "Блок такого типу вже додано", Toast.LENGTH_LONG).show();
                else
                    model.addingNewBloc(BlockTypes.END, 0, 0);
                break;

            }
            case R.id.begin: {
                if (model.contentBloc(BlockTypes.BEGIN))
                    Toast.makeText(this, "Блок такого типу вже додано", Toast.LENGTH_LONG).show();
                else
                    model.addingNewBloc(BlockTypes.BEGIN, 0, 0);
                break;

            }
            case R.id.save: {
                new DialogSave(model.saveToFile(), 1).show(getFragmentManager(), "DialogSaveDiagram");
                break;

            }
            case R.id.saveGraph: {
                model.searchError();
                if (!model.errors)
                    new DialogSave(model.getGraph(), 2).show(getFragmentManager(), "DialogSaveGraph");
                else Toast.makeText(context, "Виправте помилки", Toast.LENGTH_SHORT).show();

                break;

            }
            case R.id.show_matrix: {
                new DialogShowMatrix(model.createMatrixSumig(), model.createMatrixLink()).show(getFragmentManager(), "DialogSaveGraph");
                break;

            }
            case R.id.error: {
                new DialogError(model.searchError()).show(getFragmentManager(), "dialogError");
                fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                break;
            }
            case R.id.open_table: {
                WHAT_OPEN = 3;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, 3);
                break;
            }
            case R.id.graph: {
                model.searchError();
                if (!model.errors) {
                    model.createGraph();
                    fsurface.draw(MySurfaceView.DRAW_GRAPH);
                } else Toast.makeText(context, "Виправте помилки", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.show_table: {
                if (GenerateTable.tableViewData.size() != 0)
                    startActivity(new Intent(this, TableActivity.class));
                else {
                    model.searchError();
                    if (!model.errors) {
                        model.createGraph();
                        startActivity(new Intent(this, TableActivity.class));
                    } else Toast.makeText(context, "Виправте помилки", Toast.LENGTH_SHORT).show();
                }
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
                model.searchError();
                if (!model.errors)
                    fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                else Toast.makeText(context, "Виправте помилки", Toast.LENGTH_SHORT).show();
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
                    bar.animate().translationY(-bar.getHeight()).start();
                    bar.animate().alpha(0).start();
                    bar.setVisibility(View.GONE);

                } else {
                    bar.animate().alpha(1).start();
                    bar.animate().translationY(0).start();
                    bar.setVisibility(View.VISIBLE);
                }
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
            if (WHAT_OPEN == 3)
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
            if (WHAT_OPEN == 3)
                openTable(params[0]);

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
        Graph myGraph = null;
        try {
            is = new ObjectInputStream(new FileInputStream(path));
            myGraph = (Graph) is.readObject();
            model.graphObjs = myGraph.mGraphObjs;
            model.matrixLGraph = myGraph.allGraphLinks;
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    void openTable(String path) {
        ObjectInputStream is = null;
        Graph myGraph = null;
        try {
            is = new ObjectInputStream(new FileInputStream(path));
            GenerateTable.tableViewData = (ArrayList<String>) is.readObject();
            startActivity(new Intent(this, TableActivity.class));
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}


