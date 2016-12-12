package com.flowcharts.kolas.labs_tpcs.dialogs;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.flowcharts.kolas.labs_tpcs.Graph;
import com.flowcharts.kolas.labs_tpcs.R;
import com.flowcharts.kolas.labs_tpcs.TableActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mikola on 27.09.2016.
 */

public class DialogSave extends DialogFragment {
    Object file;
    int what;

    public DialogSave(Object file, int what) {
        this.file = file;
        this.what=what;
    }



    final String LOG_TAG = "myLogs";



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Введіть iм'я файлу");
        View v = inflater.inflate(R.layout.dialog_input_edit_text, null);
        final EditText editText = (EditText) v.findViewById(R.id.newNameFile);
        v.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editText.getText());
                if (name.equals(""))
                    name = "newFile";
                name.trim();
                if(what==1)
                writeFileSD( (String) file, String.valueOf(name) + ".txt");
                if(what==2)
                    try {
                        saveGraph((Graph)file,String.valueOf(name) + ".graph");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if(what== TableActivity.SAVE_TABLE)
                    try {
                        saveTable((ArrayList<String>)file,String.valueOf(name) + ".table");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                if(what ==TableActivity.SAVE_MIN){
                    writeFileSD( (String) file, "MIN_"+String.valueOf(name) + ".txt");
                }
                dismiss();
            }
        });

        return v;
    }


    void saveGraph(Graph graph, String name) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/sdcard/"+name)); //Select where you wish to save the file...
        oos.writeObject(graph);// write the class as an 'object'
        oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
        oos.close();// close the stream;
    }
    void saveTable(ArrayList<String> table, String name) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/sdcard/" + name)); //Select where you wish to save the file...
        oos.writeObject(table);// write the class as an 'object'
        oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
        oos.close();// close the stream;
        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
    }
    void writeFileSD(String file, String name) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
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
    }
}