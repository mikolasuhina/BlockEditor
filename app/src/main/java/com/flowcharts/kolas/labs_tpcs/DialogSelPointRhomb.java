package com.flowcharts.kolas.labs_tpcs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created  on 23.03.2016.
 */
public class DialogSelPointRhomb extends DialogFragment {

    RhombView myView;
    boolean select;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    MainActivity mainActivity;
    public static final String TAG = "Mylogs";

    @Override


    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog = new Dialog(getActivity(), R.style.MyCustomThemeEat);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.setContentView(R.layout.dialog_rhomb_view);

        myView = new RhombView(this.mainActivity, mainActivity.model.thisBloc,false);

        if (getTrueFalse().size() == 0)
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new String[]{"false", "true"});
        else if (getTrueFalse().size() == 1)
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new String[]{String.valueOf(getTrueFalse().get(0))});
        else
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new String[]{""});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) dialog.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                select = Boolean.parseBoolean(spinner.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        FrameLayout frameLayout = (FrameLayout) dialog.findViewById(R.id.cava_for_rhomb);
        frameLayout.addView(myView);
        if (myView.obj.getSecond() != 100 && myView.obj.getFirst() != 100) {
            Toast.makeText(mainActivity, "Всі вихідні з'єднання використовуються. \n  Видаліть непотрібне", Toast.LENGTH_SHORT).show();
            mainActivity.arrow = false;
            mainActivity.isblocfrom = false;
            mainActivity.model.flag = false;
            dismiss();
        }
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                myView.setThisPointNumber(myView.getThisPointNumber() + 1);

                if (myView.getThisPointNumber() > 2)
                    myView.setThisPointNumber(0);


                while (myView.obj.getOutPoints().get(myView.getThisPointNumber()).isUse()) {

                    myView.setThisPointNumber(myView.getThisPointNumber() + 1);

                    if (myView.getThisPointNumber() > 2)
                        myView.setThisPointNumber(0);
                }


                Log.d(TAG, "numberlink=" + myView.getThisPointNumber());
                myView.invalidate();

                return false;
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.findViewById(R.id.eatexitbtn).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                myView.obj.setNuberPointLink(myView.thisPointNumber);
                if (myView.obj.getFirst() == 100) {
                    myView.obj.setFirst(myView.thisPointNumber);
                    mainActivity.model.thisLinc = new Link(myView.obj.getId(), true);
                } else if (myView.obj.getSecond() == 100) {
                    myView.obj.setSecond(myView.thisPointNumber);
                    mainActivity.model.thisLinc = new Link(myView.obj.getId(), false);
                } else mainActivity.setArrow(false);

                myView.obj.getOutPoints().get(myView.thisPointNumber).setUse(true);
                myView.obj.getOutPoints().get(myView.thisPointNumber).setType_for_rhomh(select);

                if (myView.obj.getSecond() != 100 && myView.obj.getFirst() != 100) {
                    dismiss();
                }

                mainActivity.isblocfrom = true;
                mainActivity.model.flag = true;

                dismiss();

            }

        });


        return dialog;

    }


    public DialogSelPointRhomb(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    ArrayList<Boolean> getTrueFalse() {
        ArrayList<Boolean> true_false = new ArrayList<>();
        for (PointConnect p : myView.obj.getOutPoints()) {
            if (p.isUse()) {
                true_false.add((!p.type_for_rhomh));
            }

        }
        return true_false;
    }
}