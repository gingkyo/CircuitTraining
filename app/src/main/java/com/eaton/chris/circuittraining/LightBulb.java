package com.eaton.chris.circuittraining;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by chris on 24/04/2016.
 */
public class LightBulb extends ImageView implements CircuitComponent {
    private Wire wire;
    private boolean isLive;
    private static final int bulb_on = R.drawable.bulb_on;
    private static final int bulb_off = R.drawable.bulb_off;

    public LightBulb(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean setOutput(Wire wire) {
        return false;
    }

    @Override
    public boolean addInput(Wire wire) {
        if(this.wire==null){
            this.wire=wire;
            return true;
        }
        return false;
    }

    @Override
    public void updateSignal() {
        if(wire.isLive()){
            setImageResource(bulb_on);
            isLive=true;
        } else {
            setImageResource(bulb_off);
            isLive=false;
        }
    }
    public void resetComponent(){
        wire=null;
        isLive=false;
        setImageResource(bulb_off);
    }
    @Override
    public boolean isLive() {
        return isLive;
    }
}
