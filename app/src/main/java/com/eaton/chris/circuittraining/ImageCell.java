package com.eaton.chris.circuittraining;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageCell extends ImageView
        implements DragSource, DropTarget
{
    public boolean isEmpty = true;
    public int cellNumber = -1;
    private Gate cellValue;


    public ImageCell (Context context) {
        super (context);
    }
    public ImageCell (Context context, AttributeSet attrs) {
        super (context, attrs);
    }
    public ImageCell (Context context, AttributeSet attrs, int style) {
        super (context, attrs, style);
    }
    public boolean allowDrag () {
        //drag is limited to only the frame where the image is added and not within the grid
        return cellNumber<0;
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
            this.setGate(source.getGate());
            this.setImageDrawable(d);
            this.invalidate();
            this.setOnLongClickListener((View.OnLongClickListener)getContext());
        }
    }
    public boolean onDragEnter (DragSource source) {
        if (cellNumber < 0) return false;
        int bg = isEmpty ? R.color.cell_empty_hover : R.color.cell_filled_hover;
        setBackgroundResource (bg);
        return true;
    }
    public boolean onDragExit (DragSource source) {
        if (cellNumber < 0) return false;
        int bg = isEmpty ? R.color.transparent : R.color.transparent;
        setBackgroundResource (bg);
        return true;
    }
    public void setGate(Gate gate){
        cellValue=gate;
    }
    public Gate getGate()
    {
        return cellValue;
    }

}
