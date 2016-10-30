package com.flowcharts.kolas.labs_tpcs.dialogs;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flowcharts.kolas.labs_tpcs.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mikola on 27.09.2016.
 */

public class DialogError extends DialogFragment {
    ArrayList<String> file;

    public DialogError(ArrayList<String> file) {
this.file=file;
    }


    final String LOG_TAG = "myLogs";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("ПОМИЛКИ");
        View v = inflater.inflate(R.layout.dialog_eror, null);
        String [] errors = new String[file.size()];
        file.toArray(errors);
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP);
        final ListView editText = (ListView) v.findViewById(R.id.list_error);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, errors);
        editText.setAdapter(adapter);
        v.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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