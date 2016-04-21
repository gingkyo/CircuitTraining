package com.eaton.chris.circuittraining;

import android.graphics.Color;
import android.view.View;

public class Wire
{
    private boolean isLive;
    WireSurface wireSurface;
    CircuitComponent startPoint;
    CircuitComponent endPoint;
    float [] wireCoords;

    public Wire(WireSurface wireSurface,CircuitComponent startPoint,CircuitComponent endPoint){
        this.wireSurface=wireSurface;
        wireCoords=new float[4];
        this.startPoint=startPoint;
        this.endPoint=endPoint;
    }
    public boolean getStatus(){
        return isLive;
    }
    public void setIsLive(boolean isLive){
        this.isLive=isLive;
        endPoint.getOutput();
    }
    public int setLiveColor(){
        if(isLive)
            return Color.RED;
        return Color.WHITE;
    }
    public void drawWire(){
        wireSurface.setPaint(setLiveColor());
        wireSurface.isNewWire = true;
        wireSurface.setLineCoords(wireCoords);
        wireSurface.invalidate();
    }
    public void buildWireCoords(View start,View end,boolean startsAtPowerButton) {
        float startX = start.getX();
        if (!startsAtPowerButton)
            startX += start.getWidth();
        float startY = (start.getHeight() / 2) + start.getY();
        float endY = (end.getHeight() / 2) + end.getY();
        float[] wireCoords = {startX, startY, end.getX(), endY};
        this.wireCoords=wireCoords;
    }

}