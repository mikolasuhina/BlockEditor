package com.flowcharts.kolas.labs_tpcs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flowcharts.kolas.labs_tpcs.blocks.RhombBlock;

import java.util.Iterator;
import java.util.Map;


/**
 * Created  on 23.03.2016.
 */
public class DialogSelPointRhombDelete extends DialogFragment {

    RhombView myView;

    MainActivity mainActivity;
    public static final String TAG = "Mylogs";

    @Override


    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog = new Dialog(getActivity(), R.style.MyCustomThemeEat);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.setContentView(R.layout.dialog_rhomb_view);

        myView = new RhombView(this.mainActivity, mainActivity.model.thisBloc,true);
        FrameLayout frameLayout = (FrameLayout) dialog.findViewById(R.id.cava_for_rhomb);
        frameLayout.addView(myView);
        if (myView.obj.getSecond() == RhombBlock.FREE && myView.obj.getFirst() == RhombBlock.FREE) {
            dismiss();
            Toast.makeText(mainActivity, "В даного блоку всі вихідні з'єднання вільні", Toast.LENGTH_LONG).show();
        }
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                myView.setThisPointNumber(myView.getThisPointNumber() + 1);

                if (myView.getThisPointNumber() > 2)
                    myView.setThisPointNumber(0);


                while (!myView.obj.getOutPoints().get(myView.getThisPointNumber()).isUse()) {

                    myView.setThisPointNumber(myView.getThisPointNumber() + 1);


                    if (myView.getThisPointNumber() > 2)
                        myView.setThisPointNumber(0);
                }

                myView.invalidate();

                return false;
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.findViewById(R.id.eatexitbtn).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {


                myView.obj.setNuberPointLink(myView.thisPointNumber);
                myView.obj.getOutPoints().get(myView.thisPointNumber).setUse(false);
                int id = 0;
                Iterator<Map.Entry<Integer, Link>> linc = mainActivity.model.allLinks.entrySet().iterator();
                while (linc.hasNext()) {
                    Map.Entry<Integer, Link> ilinc = linc.next();
                    if (ilinc.getValue().getId_from() == mainActivity.model.thisBloc.getId()) {
                        if (myView.obj.getFirst() == myView.getThisPointNumber())
                            if (ilinc.getValue().isF_point()) {
                                linc.remove();
                                id = ilinc.getValue().getId_to();
                                myView.obj.setFirst(RhombBlock.FREE);

                            }
                        if (myView.obj.getSecond() == myView.getThisPointNumber())
                            if (!ilinc.getValue().isF_point()) {
                                linc.remove();
                                id = ilinc.getValue().getId_to();
                                myView.obj.setSecond(RhombBlock.FREE);
                            }

                    }

                }
                boolean useOutPoin = false;
                for (Link link : mainActivity.model.allLinks.values()) {
                    if (link.getId_to() == id)
                        useOutPoin = true;
                }

                mainActivity.model.allBlocs.get(id).getIn_Point().setUse(useOutPoin);
                mainActivity.fsurface.draw(MySurfaceView.DRAW_DIAGRAM);
                dismiss();


            }

        });


        return dialog;

    }


    public DialogSelPointRhombDelete(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}