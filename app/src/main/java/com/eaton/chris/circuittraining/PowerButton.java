package com.eaton.chris.circuittraining;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class PowerButton extends ImageView {
    private Wire wire;
    private boolean isLive;
    private static final int power_on = R.drawable.power_on;
    private static final int power_off = R.drawable.power_off;

    public PowerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLive=false;
        wire=null;
    }
    public void setPowerButton(){
        if(isLive){
            setImageResource(power_off);
            setTag("power_off");
            isLive=false;
        } else{
            setImageResource(power_on);
            setTag("power_on");
            isLive=true;
        }
        if(wire!=null){
           if(wire.getStatus()!=isLive){
               wire.setIsLive(isLive);
               wire.reDrawWire();
           }
        }
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Wire getWire() {
        return wire;
    }

    public void addConnection(Wire wire) {
        this.wire = wire;
    }
}
