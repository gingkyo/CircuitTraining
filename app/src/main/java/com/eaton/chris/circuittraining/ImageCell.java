package com.eaton.chris.circuittraining;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageCell extends ImageView
        implements DragSource, DropTarget,CircuitComponent

{
    public boolean isEmpty = true;
    public int cellNumber = -1;
    private String gateType;
    private Wire conn1;
    private Wire conn2;
    private Wire output;
    private boolean isLive;

    public ImageCell (Context context) {
        super (context);
        this.gateType="";
        conn1=null;
        conn2=null;
        output=null;
        isLive=false;
    }
    public ImageCell (Context context, AttributeSet attrs) {
        super (context, attrs);
    }
    public ImageCell (Context context, AttributeSet attrs, int style) {
        super (context, attrs, style);
    }
    public boolean allowDrag () {
        //drag is limited to only the frame where the image is added and not within the grid
        return cellNumber<0 && !isEmpty;
    }
    public boolean allowDrop (DragSource source) {
        return source!=this && isEmpty  && cellNumber >= 0;
    }
    public void onDrop (DragSource source) {

        isEmpty = false;
        int bg = isEmpty ? R.color.transparent : R.color.transparent;
        setBackgroundResource (bg);

        ImageView sourceView = (ImageView) source;
        Drawable d = sourceView.getDrawable ();
        if (d != null) {
            this.setImageDrawable(d);
            this.invalidate();
            this.setOnLongClickListener((View.OnLongClickListener)getContext());
        }
    }
    public boolean onDragEnter (DragSource source) {
        if (cellNumber < 0) return false;
        int bg = isEmpty ? R.color.green : R.color.cell_filled_hover;
        setBackgroundResource (bg);
        return true;
    }
    public boolean onDragExit (DragSource source) {
        if (cellNumber < 0) return false;
        int bg = isEmpty ? R.color.transparent : R.color.transparent;
        setBackgroundResource (bg);
        return true;
    }
    public void setGate(String gateType){
        this.gateType=gateType;
    }
    public String getGate()
    {
        return gateType;
    }
    @Override
    public boolean setOutput(Wire wire) {
        if(output!=null)
            return false;
        output=wire;
        return true;
    }
    @Override
    public boolean addInput(Wire wire) {
        if(isConnected()){
            return false;
        }
        if(conn1==null){
            conn1=wire;
            if(gateType.equals("notGate")) return true;
        }
        else{
            conn2=wire;
        }
        return true;
    }
    @Override
    public void updateSignal() {
        if (isConnected()) {
            switch (gateType) {
                case "notGate":
                    isLive = !conn1.isLive();
                    break;
                case "andGate":
                    isLive = conn1.isLive() && conn2.isLive();
                    break;
                case "orGate":
                    isLive = conn2.isLive() || conn1.isLive();
                    break;
                case "xorGate":
                    if (conn1.isLive() && conn2.isLive()) {
                        isLive = false;
                        break;
                    }
                    isLive = conn1.isLive() || conn2.isLive();
                    break;
                case "nandGate":
                    if(conn1.isLive() && conn2.isLive()){
                        isLive=false;
                        break;
                    }
                    isLive=true;
                    break;
                case "norGate":
                    isLive = !conn1.isLive() && !conn2.isLive();
                    break;
                case "xnorGate":
                    boolean dbleNegative = !conn1.isLive() && !conn2.isLive();
                    isLive = conn1.isLive() && conn2.isLive() || dbleNegative;
                    break;
            }
            if(output!=null){
                output.setIsLive(isLive);
            }
        }
    }
    public String getGateType(){
        return gateType;
    }

    @Override
    public boolean isLive() {
        return isLive;
    }
    @Override
    public void resetComponent() {
    }
    @Override
    public boolean isPowerButton() {
        return false;
    }
    @Override
    public boolean isLightBulb() {
        return false;
    }
    public boolean isConnected(){
        if(gateType.equals("notGate")) return conn1!=null;
        return conn1!=null && conn2!=null;
    }
}
