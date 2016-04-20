package com.eaton.chris.circuittraining;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class WireSurface extends View {
    Paint paint;
    boolean isNewWire;
    Bitmap savedBitmap;
    float startX,startY,endX,endY;

    public WireSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        startX=0.0f;
        startY=0.0f;
        endX=0.0f;
        endY=0.0f;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10.0f);
        isNewWire = false;
    }
    public void setPaint(int color){
        paint.setColor(color);
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if (savedBitmap == null) {
            savedBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565);
        }
        if(isNewWire){
            Canvas canvasToSave = new Canvas(savedBitmap);
            canvasToSave.drawLine(startX, startY, endX, endY, paint);
            resetLineCoords();
            isNewWire=false;
        }
        canvas.drawBitmap(savedBitmap, 0, 0, new Paint());
    }
    private void resetLineCoords(){
        startX=0.0f;
        startY=100.0f;
        endX = 100.0f;
        endY = 1000.0f;
    }
    public void setLineCoords(float [] wireCoords){//float sX,float sY,float eX,float eY){
        startX=wireCoords[0];
        startY=wireCoords[1];
        endX=wireCoords[2];
        endY=wireCoords[3];
    }
}

