package com.eaton.chris.circuittraining;


import android.graphics.Color;
import android.view.View;

public class Wire
{
    CircuitComponent startPoint;
    CircuitComponent endPoint;
    float [] wireCoords;
    boolean isLive;
    WireSurface wireSurface;

    public Wire(WireSurface wireSurface,CircuitComponent startPoint,CircuitComponent endPoint){
        this.wireSurface=wireSurface;
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        isLive=false;
        wireCoords=new float[4];
    }
    public void setIsLive(boolean isLive){
        this.isLive=isLive;
        drawWire();
        endPoint.updateSignal();

    }
    public int setLiveColor(){
        if(isLive)
            return Color.RED;
        return Color.WHITE;
    }
    public void drawWire(){
        wireSurface.setPaint(setLiveColor());
        wireSurface.start();
    }
    public boolean isLive() {
        return isLive;
    }

    public void buildWireCoords() {
        View start =(View) startPoint;
        View end =(View) endPoint;
        float startX = start.getX();
        float endX;
        float endY;
        if (!startPoint.isPowerButton())
            startX += start.getWidth();
        if (endPoint.isLightBulb()) {
            View parent = (View) end.getParent();
            endX = parent.getX();
            endY = end.getY() + (end.getHeight() / 2);
        } else {
            endX = end.getX();
            endY = (end.getHeight() / 2) + end.getY();
        }
        float startY = (start.getHeight() / 2) + start.getY();
        wireCoords[0]=startX;
        wireCoords[1]=startY;
        wireCoords[2]=endX;
        wireCoords[3]=endY;
    }
}