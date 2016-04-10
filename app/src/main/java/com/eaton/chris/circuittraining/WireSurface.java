package com.eaton.chris.circuittraining;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.view.View;

public class WireSurface extends View {
    Paint paint;
    boolean canDraw;
    Picture picture;

    public WireSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10.0f);
        canDraw=false;
        picture=new Picture();
    }

    @Override
    public void draw(Canvas canvas) {
        if(canDraw){
            picture.beginRecording(100,100);
            super.draw(canvas);
            canvas.drawLine(0.0f, 0.0f, 50.0f, 50.0f, paint);
            picture.endRecording();
        }
    }
}
