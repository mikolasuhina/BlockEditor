package com.flowcharts.kolas.labs_tpcs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.flowcharts.kolas.labs_tpcs.dialogs.DialogSave;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    public static final int SAVE_TABLE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        gvMain = (TableLayout) findViewById(R.id.table_layout);


        TableLayout.LayoutParams layoutRow = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < GenerateTable.tableViewData.size() / GenerateTable.columnCount; i++) {
            TableRow tableRow = new TableRow(this);
            ;
            tableRow.setLayoutParams(layoutRow);
            tableRow.setGravity(Gravity.CENTER);
            TableRow.LayoutParams layoutHistory = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            for (int j = 0; j < GenerateTable.columnCount; j++) {
                TextView textView = new TextView(this);
                textView.setTextSize(18);
                textView.setTypeface(Typeface.MONOSPACE);
                textView.setLayoutParams(layoutHistory);
                textView.setBackgroundResource(R.drawable.bg_for_grid_item);
                textView.setText(GenerateTable.tableViewData.get(i * GenerateTable.columnCount + j));
                // TableRow is the parent view
                //textView.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10,10,10,10);

                tableRow.addView(textView);

            }
            gvMain.addView(tableRow);
        }


    }


    TableLayout gvMain;
    ArrayAdapter<String> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_table) {
            new DialogSave(GenerateTable.tableViewData, SAVE_TABLE).show(getFragmentManager(), "Dialog Save Table");
            ;

        }
        return super.onOptionsItemSelected(item);
    }


}