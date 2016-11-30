package com.flowcharts.kolas.labs_tpcs.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.flowcharts.kolas.labs_tpcs.Minimization;
import com.flowcharts.kolas.labs_tpcs.TableActivity;

/**
 * Created by mikola on 29.11.2016.
 */

public class DialogMinimizationResult extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Minimization minimization = new Minimization();
        return new  AlertDialog.Builder(getActivity())
                .setTitle("Мінімізація")
                .setMessage(minimization.getResult())
                .setPositiveButton("OK",null)
                .setNegativeButton("Статистика", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Статистика")
                                .setMessage(minimization.getStaticticString())
                                .setPositiveButton("OK",null).create().show();

                    }
                })
                .setNeutralButton("Зберегти", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DialogSave(minimization.getStaticticString(), TableActivity.SAVE_STATISTICS).show(getActivity().getFragmentManager(), "DialogSaveStat");
                    }
                })
                .create()
                ;


    }
}
