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

    }

    @Override
    public void setIsLive(boolean isLive) {
        this.isLive=isLive;
    }

    @Override
    public boolean isLive() {
        return isLive;
    }
}
