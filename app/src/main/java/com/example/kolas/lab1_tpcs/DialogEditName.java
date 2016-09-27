package com.example.kolas.lab1_tpcs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mikola on 27.09.2016.
 */

public class DialogEditName extends DialogFragment {



BlocObj obj;

    public DialogEditName(BlocObj obj) {
        this.obj = obj;
    }

    final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Введіть текст блоку");
        View v = inflater.inflate(R.layout.dialog1, null);
        final EditText editText = (EditText) v.findViewById(R.id.newNameFile);
        editText.setText(obj.getText());
        v.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 obj.setText(String.valueOf(editText.getText()));
                ((MainActivity)getActivity()).fsurface.draw();
                dismiss();

            }
        });

        return v;
    }



    void writeFileSD(String file,String name) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути


        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, name);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(file);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}