package com.eaton.chris.circuittraining;


import android.content.ClipData;
import android.view.DragEvent;
import android.view.View;

public class DragController
        implements View.OnDragListener
{
    private DragSource dragSource;

    public DragController(){

    }
    @Override public boolean onDrag (View v, DragEvent event) {

        boolean isDragSource = false, isDropTarget = false;
        DragSource source = null;
        DropTarget target = null;
        try {
            source = (DragSource) v;
            isDragSource = true;
        } catch (ClassCastException ex) {}
        try {
            target = (DropTarget) v;
            isDropTarget = true;
        } catch (ClassCastException ex) {}

        final int action = event.getAction();

        switch(action) {

            case DragEvent.ACTION_DRAG_STARTED:

                if (isDragSource) {
                    if (source == dragSource) {
                        if (source.allowDrag ()) {
                            return true;
                        }
                    } else {
                        return isDropTarget && target.allowDrop (dragSource);
                    }
                } else {
                    return isDropTarget && target.allowDrop(dragSource);
                }
            case DragEvent.ACTION_DRAG_ENTERED:
                if (isDropTarget)
                    return isDropTarget && target.onDragEnter (dragSource);

            case DragEvent.ACTION_DRAG_EXITED:
                return isDropTarget && target.onDragExit (dragSource);

            case DragEvent.ACTION_DROP:
                if (isDropTarget) {
                    if (target.allowDrop(dragSource)) {
                        target.onDrop(dragSource);
                    }
                }
                return isDropTarget;

            case DragEvent.ACTION_DRAG_ENDED:

                break;
        }
        return false;
    }
    public boolean setDragSource(View v){
        dragSource=(DragSource) v;
        return dragSource.allowDrag ();
    }
    public boolean startDrag (View v) {

        boolean isDragSource = false;
        DragSource ds = null;
        try {
            ds = (DragSource) v;
            isDragSource = true;
        } catch (ClassCastException ex) {
        }
        if (!isDragSource) return false;
        if (!ds.allowDrag ()) return false;

        dragSource = ds;

        ClipData dragData = ClipData.newPlainText("","");
        View.DragShadowBuilder shadowView = new View.DragShadowBuilder (v);
        v.startDrag (dragData, shadowView, ds, 0);
        return true;
    }
}
