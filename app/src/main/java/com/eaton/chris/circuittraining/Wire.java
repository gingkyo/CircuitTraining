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

    public Wire(WireSurface wireSurface){
        this.wireSurface=wireSurface;
        wireCoords=new float[4];
    }
    public boolean getStatus(){
        return isLive;
    }
    public void setWireCoords(float [] wireCoords){
        this.wireCoords=wireCoords;
    }
    public void setIsLive(boolean isLive){
        this.isLive=isLive;
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

}