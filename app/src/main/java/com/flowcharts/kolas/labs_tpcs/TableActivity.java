package com.flowcharts.kolas.labs_tpcs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
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
        gvMain = (GridView) findViewById(R.id.gvMain);
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);
        adapter = new ArrayAdapter<String>(this, R.layout.grid_item, R.id.tvText, GenerateTable.tableViewData);
        gvMain.setNumColumns(GenerateTable.columnCount);
        gvMain.setAdapter(adapter);

    }


    GridView gvMain;
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
            new DialogSave(GenerateTable.tableViewData,SAVE_TABLE).show(getFragmentManager(), "Dialog Save Table");;

        }
        return super.onOptionsItemSelected(item);
    }


}