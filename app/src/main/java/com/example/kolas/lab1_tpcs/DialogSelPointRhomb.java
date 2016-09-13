package com.example.kolas.lab1_tpcs;


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
import android.widget.FrameLayout;

import com.example.kolas.lab1_tpcs.blocs.RhombBloc;


/**
 * Created  on 23.03.2016.
 */
public class DialogSelPointRhomb extends DialogFragment {

    MyView myView;

    MainActivity mainActivity;
    public static final String TAG = "Mylogs";

    @Override


    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog = new Dialog(getActivity(), R.style.MyCustomThemeEat);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.setContentView(R.layout.gialog_sel_point_rhomb);

        myView = new MyView(this.mainActivity, mainActivity.model.thisBloc);
        FrameLayout frameLayout = (FrameLayout) dialog.findViewById(R.id.cava_for_rhomb);
        frameLayout.addView(myView);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                myView.setThisPointNumber(myView.getThisPointNumber() + 1);

                if (myView.getThisPointNumber() > 2)
                    myView.setThisPointNumber(0);

                if (myView.obj.getSecond() != 100l && myView.obj.getFirst() != 100) {
                    dismiss();
                }

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

                if (myView.obj.getSecond() != 100l && myView.obj.getFirst() != 100) {
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
}