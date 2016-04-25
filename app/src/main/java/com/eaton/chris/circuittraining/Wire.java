package com.eaton.chris.circuittraining;


import android.graphics.Color;

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
        wireSurface.start();
    }
    public boolean isLive() {
        return isLive;
    }

    public void setWireCoords(float[] wireCoords){
        this.wireCoords=wireCoords;
    }
}