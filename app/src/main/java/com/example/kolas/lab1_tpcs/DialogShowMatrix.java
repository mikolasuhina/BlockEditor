package com.example.kolas.lab1_tpcs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mikola on 27.09.2016.
 */

public class DialogShowMatrix extends DialogFragment {


    String[] matrixSum;
    String[] matrixLink;



    public DialogShowMatrix(String[]matrixSum,String [] matrixLink) {
        this.matrixSum = matrixSum;
        this.matrixLink = matrixLink;


    }


    GridView gvMain;
    ArrayAdapter<String> adapter;

    final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_show_matrix, null);

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.grid_item, R.id.tvText, matrixSum);
        gvMain = (GridView) v.findViewById(R.id.gvMain);
        gvMain.setAdapter(adapter);
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);

        gvMain.setNumColumns((int) Math.sqrt(matrixSum.length+1));
        final Switch sw = (Switch) v.findViewById(R.id.switch1);
        sw.setText("Матриця суміжності");
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    gvMain.setNumColumns((int) Math.sqrt(matrixSum.length+1));
                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.grid_item, R.id.tvText, matrixSum);
                    gvMain.setAdapter(adapter);
                    sw.setText("Матриця суміжності");
                }
                else{
                    gvMain.setNumColumns((int) Math.sqrt(matrixLink.length+1));
                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.grid_item, R.id.tvText, matrixLink);
                    gvMain.setAdapter(adapter);
                    sw.setText("Матриця звязків");
                }
            }
        });

        return v;
    }


}