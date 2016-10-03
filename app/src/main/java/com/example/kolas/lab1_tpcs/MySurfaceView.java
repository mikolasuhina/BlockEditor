package com.example.kolas.lab1_tpcs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Switch;

import com.example.kolas.lab1_tpcs.blocs.BlocObj;
import com.example.kolas.lab1_tpcs.blocs.BlocTypes;
import com.example.kolas.lab1_tpcs.blocs.GraphLink;
import com.example.kolas.lab1_tpcs.blocs.ReckBloc;
import com.example.kolas.lab1_tpcs.blocs.RhombBloc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {


    public static final int DRAW_DIAGRAM = 1;
    public static final int DRAW_GRAPH = 2;
    Model model;

    void drawline(Canvas canvas) {

        Paint p = new Paint();
        p.setColor(Color.WHITE);


        for (BlocObj blocObj : model.allBlocs.values()) {


            switch (blocObj.getType()) {
                case RECT: {
                    p.setColor(blocObj.getColor());
                    canvas.drawRect(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight(), p);
                    if (blocObj.getIn_Point() != null)
                        canvas.drawLine(blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY(), blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY() + blocObj.getHeight() / 4, p);
                    if (blocObj.getOut_Point() != null)
                        canvas.drawLine(blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY(), blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY() - blocObj.getHeight() / 4, p);
                    break;
                }
                case RHOMB: {
                    p.setColor(blocObj.getColor());
                    canvas.rotate(45, blocObj.getX() + blocObj.getWidth() / 2, blocObj.getY() + blocObj.getHeight() / 2);
                    canvas.drawRect(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight(), p);
                    canvas.rotate(-45, blocObj.getX() + blocObj.getWidth() / 2, blocObj.getY() + blocObj.getHeight() / 2);
                    if (blocObj.getIn_Point() != null)
                        canvas.drawLine(blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY(), blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY() + blocObj.getHeight() / 4, p);


                    RhombBloc rb = (RhombBloc) blocObj;

                    // canvas.drawLine(blocObj.getOut_Point().getX(),blocObj.getOut_Point().getY(),blocObj.getOut_Point().getX(),blocObj.getOut_Point().getY()-blocObj.getHeight()/4,p);
                    canvas.drawLine(rb.getOutPoints().get(0).getX(), rb.getOutPoints().get(0).getY(), rb.getOutPoints().get(0).getX(), rb.getOutPoints().get(0).getY() - blocObj.getHeight() / 4, p);
                    canvas.drawLine(rb.getOutPoints().get(1).getX(), rb.getOutPoints().get(1).getY(), rb.getOutPoints().get(1).getX() - blocObj.getHeight() / 4, rb.getOutPoints().get(1).getY(), p);
                    canvas.drawLine(rb.getOutPoints().get(2).getX(), rb.getOutPoints().get(2).getY(), rb.getOutPoints().get(2).getX() + blocObj.getHeight() / 4, rb.getOutPoints().get(2).getY(), p);
                    p.setColor(Color.RED);
                    p.setTextSize(14);
                    if (rb.getPointL().isUse()) {
                        p.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(String.valueOf(rb.getPointL().isType_for_rhomh()), rb.getPointL().getX(), rb.getPointL().getY(), p);
                    }
                    if (rb.getPointR().isUse()) {
                        p.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(rb.getPointR().isType_for_rhomh()), rb.getPointR().getX(), rb.getPointR().getY(), p);
                    }
                    if (rb.getPointB().isUse())
                        canvas.drawText(String.valueOf(rb.getPointB().isType_for_rhomh()), rb.getPointB().getX(), rb.getPointB().getY(), p);
                    p.setTextAlign(Paint.Align.CENTER);
                    p.setTextSize(20);
                    break;

                }
                default: {
                    p.setColor(blocObj.getColor());
                    final RectF rect = new RectF();
                    rect.set(blocObj.getX(), blocObj.getY(), blocObj.getX() + blocObj.getWidth(), blocObj.getY() + blocObj.getHeight());
                    canvas.drawRoundRect(rect, 20, 20, p);
                    if (blocObj.getIn_Point() != null)
                        canvas.drawLine(blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY(), blocObj.getIn_Point().getX(), blocObj.getIn_Point().getY() + blocObj.getHeight() / 4, p);
                    if (blocObj.getOut_Point() != null)
                        canvas.drawLine(blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY(), blocObj.getOut_Point().getX(), blocObj.getOut_Point().getY() - blocObj.getHeight() / 4, p);
                    break;
                }


            }
            p.setColor(Color.RED);
            p.setTextAlign(Paint.Align.CENTER);
            p.setTextSize(20);
            canvas.drawText(blocObj.getText(), blocObj.getX() + blocObj.getWidth() / 2, blocObj.getY() + blocObj.getHeight() / 2, p);
        }

        p.setColor(Color.YELLOW);

        if (MainActivity.arrow && MainActivity.isblocfrom) {
            for (SimpleArrow s : model.thisArrows) {
                canvas.drawLine(s.getX_from(), s.getY_from(), s.getX_to(), s.getY_to(), p);

            }
            canvas.drawLine(model.thisSimpleArrow.getX_from(), model.thisSimpleArrow.getY_from(), MainActivity.nx, MainActivity.ny, p);
        }


        for (Link sarrow : model.allLinks.values()) {


            if (model.allBlocs.get(sarrow.getId_from()).getType() == BlocTypes.RHOMB) {

                if ((sarrow.f_point)) {
                    ((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).setNuberPointLink(((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).getFirst());

                } else if (((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).getSecond() != 100) {
                    ((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).setNuberPointLink(((RhombBloc) (model.allBlocs.get(sarrow.getId_from()))).getSecond());
                }
            }

            model.drawnewLineIn(model.allBlocs.get(sarrow.getId_from()).getOut_Point().getX(), model.allBlocs.get(sarrow.getId_from()).getOut_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), sarrow.getArrows());
            canvas.drawLine(model.allBlocs.get(sarrow.getId_from()).getOut_Point().getX(), model.allBlocs.get(sarrow.getId_from()).getOut_Point().getY(), sarrow.arrows.get(0).getX_from(), sarrow.arrows.get(0).getY_from(), p);


            for (SimpleArrow sa : sarrow.getArrows()) {

                canvas.drawLine(sa.getX_from(), sa.getY_from(), sa.getX_to(), sa.getY_to(), p);
            }


            drawArrow(model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), 5, model.allBlocs.get(sarrow.getId_to()).getHeight() / 4, model.allBlocs.get(sarrow.getId_to()).getType(), canvas, p);
            canvas.drawLine(sarrow.arrows.get(sarrow.arrows.size() - 1).getX_to(), sarrow.arrows.get(sarrow.arrows.size() - 1).getY_to(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), p);
            // canvas.drawLine( model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX()-10, model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY()-10,p);
            //.drawLine( model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY(), model.allBlocs.get(sarrow.getId_to()).getIn_Point().getX()+10, model.allBlocs.get(sarrow.getId_to()).getIn_Point().getY()-10,p);
        }

    }


    public MySurfaceView(Context context, Model model) {
        super(context);
        // TODO Auto-generated constructor stub
        getHolder().addCallback(this);
        this.model = model;

    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                               int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }


    public void draw(int whatDraw) {
        Canvas c = null;
        try {
            c = getHolder().lockCanvas(null);
            synchronized (getHolder()) {


                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                switch (whatDraw) {
                    case DRAW_DIAGRAM: {
                        drawline(c);
                        break;
                    }
                    case DRAW_GRAPH: {
                        drawGraph(c);
                        break;
                    }
                }
            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (c != null) {
                getHolder().unlockCanvasAndPost(c);
            }
        }
    }


    private void drawGraph(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(20);
        p.setColor(Color.WHITE);
        for (GraphObj o : model.allGraphsObjs.values()) {
            p.setColor(Color.WHITE);
            c.drawCircle(o.center_x, o.center_y, o.radius, p);
            p.setColor(Color.BLACK);

            c.drawText(o.top_text, o.center_x, o.center_y - p.getTextSize(), p);
            c.drawText("―", o.center_x, o.center_y, p);
            c.drawText(o.getBottom_text(), o.center_x, o.center_y + p.getTextSize(), p);
        }
        p.setColor(Color.RED);
        for (GraphLink obj : model.allGraphLinks.values()) {
            Point p1 = new Point(model.allGraphsObjs.get(obj.getId_from()).center_x, model.allGraphsObjs.get(obj.getId_from()).center_y);
            Point p2 = new Point(model.allGraphsObjs.get(obj.getId_to()).center_x, model.allGraphsObjs.get(obj.getId_to()).center_y);
            Point p_from = getCircleLineIntersectionPoint(p1, p2, p1, 50).get(1);
            Point p_to = getCircleLineIntersectionPoint(p1, p2, p2, 50).get(0);

            c.drawLine(p_from.x, p_from.y, p_to.x, p_to.y, p);
            float center_x=ceneter(p_from.x,p_to.x);
            float center_y=ceneter(p_from.y,p_to.y);
            p.setColor(Color.WHITE);
            c.drawText(obj.getText(),center_x,center_y,p);
            p.setColor(Color.RED);
            fillArrow(p, c, p_from.x, p_from.y, p_to.x, p_to.y);
        }


    }

    float ceneter(float arg1 ,float arg2){
      if(arg1>arg2){
          return arg2+(arg1-arg2)/2;
      }
        else  return arg1+(arg2-arg1)/2;
    }

    private void fillArrow(Paint paint, Canvas canvas, float x0, float y0, float x1, float y1) {
        paint.setStyle(Paint.Style.STROKE);

        int arrowHeadLenght = 15;
        int arrowHeadAngle = 45;
        float[] linePts = new float[]{x1 - arrowHeadLenght, y1, x1, y1};
        float[] linePts2 = new float[]{x1, y1, x1, y1 + arrowHeadLenght};
        Matrix rotateMat = new Matrix();

        //get the center of the line
        float centerX = x1;
        float centerY = y1;

        //set the angle
        double angle = Math.atan2(y1 - y0, x1 - x0) * 180 / Math.PI + arrowHeadAngle;

        //rotate the matrix around the center
        rotateMat.setRotate((float) angle, centerX, centerY);
        rotateMat.mapPoints(linePts);
        rotateMat.mapPoints(linePts2);

        canvas.drawLine(linePts[0], linePts[1], linePts[2], linePts[3], paint);
        canvas.drawLine(linePts2[0], linePts2[1], linePts2[2], linePts2[3], paint);
    }


    private void drawArrow(float x, float y, float dx, float dy, BlocTypes type, Canvas c, Paint p) {
        switch (type) {
            case END:
                c.drawLine(x, y + dy, x - dx, y, p);
                c.drawLine(x, y + dy, x + dx, y, p);
                break;
            case RECT:
                c.drawLine(x, y + dy, x - dx, y + dy / 2, p);
                c.drawLine(x, y + dy, x + dx, y + dy / 2, p);
                break;
            case RHOMB:
                c.drawLine(x, y, x - dx, y - dy / 2, p);
                c.drawLine(x, y, x + dx, y - dy / 2, p);
                break;
        }
    }

    public static List<Point> getCircleLineIntersectionPoint(Point pointA,
                                                             Point pointB, Point center, double radius) {
        float baX = pointB.x - pointA.x;
        float baY = pointB.y - pointA.y;
        float caX = center.x - pointA.x;
        float caY = center.y - pointA.y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = (float) (caX * caX + caY * caY - radius * radius);

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        Point p1 = new Point(pointA.x - baX * abScalingFactor1, pointA.y
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Point p2 = new Point(pointA.x - baX * abScalingFactor2, pointA.y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    static class Point {
        float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
        }
    }

}
