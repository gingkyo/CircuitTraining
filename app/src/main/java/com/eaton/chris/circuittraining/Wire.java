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
        //wireSurface.setLineCoords(wireCoords);
        //wireSurface.isNewWire = true;
        wireSurface.start();
    }
    public boolean isLive() {
        return isLive;
    }
    public void buildWireCoords(View start, View end, boolean startsAtPowerButton) {
        float startX = start.getX();
        if (!startsAtPowerButton)
            startX += start.getWidth();
        float startY = (start.getHeight() / 2) + start.getY();
        float endY = (end.getHeight() / 2) + end.getY();
        float[] wireCoords = {startX, startY, end.getX(), endY};
        this.wireCoords=wireCoords;
    }
}