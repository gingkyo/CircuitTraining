package com.eaton.chris.circuittraining;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class PowerButton extends ImageView implements CircuitComponent{
    private Wire output;
    private boolean isLive;
    private static final int power_on = R.drawable.power_on;
    private static final int power_off = R.drawable.power_off;
    private GameManager gameManager;

    public PowerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        output=null;
        isLive=false;
    }
    public void updateSignal(){
        if(!isLive){
            setImageResource(power_on);
            isLive=true;
        } else{
            setImageResource(power_off);
            isLive=false;
        }
        if(output!=null){
            output.setIsLive(isLive);

        }
    }
    public boolean setOutput(Wire wire){
        if(output != null)
            return false;
        output=wire;
        return true;
    }
    public void setGameManager(GameManager gm){
        gameManager=gm;
    }
    public void setIsLive(boolean isLive){
        this.isLive=isLive;
    }
    public boolean isLive() {
        return isLive;
    }
    public boolean addInput(Wire wire){
        return false;
    }

}
