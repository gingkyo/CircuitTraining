package com.eaton.chris.circuittraining;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class WireSurface extends SurfaceView implements Runnable {
    SurfaceHolder myHolder;
    Thread myThread;
    boolean isRunning=true;
    ArrayList<Wire> wireArray;

    Paint paint;

    public WireSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        wireArray =new ArrayList<>();
        paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5.0f);
        myHolder = getHolder();
        myThread = new Thread(this);
    }
    public void setPaint (int color){
        paint.setColor(color);
    }
    public void start()
    {
        isRunning=true;
        myThread = new Thread(this);
        myThread.start();
    }

    public void stop()
    {
        isRunning=false;
        while(true)
        {
            try {
                myThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // block until thread dies
            break;
        }
    }
    @Override
    public void run() {
        while(isRunning)
        {
            if(!myHolder.getSurface().isValid())
                continue;

            Canvas canvas = myHolder.lockCanvas();
            for(Wire wire:wireArray){
                paint.setColor(wire.setLiveColor());
                canvas.drawLine(wire.wireCoords[0], wire.wireCoords[1], wire.wireCoords[2], wire.wireCoords[3], paint);
            }
            myHolder.unlockCanvasAndPost(canvas);
            stop();
        }
    }
    public void resetCanvas(){
        Canvas canvas=myHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        myHolder.unlockCanvasAndPost(canvas);

    }
}

