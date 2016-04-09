package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class CircuitActivity extends Activity
        implements View.OnDragListener,
        View.OnClickListener,
        View.OnLongClickListener,
        View.OnTouchListener
{
    private DragSource dragSource;
    private ImageCell lastNewCell=null;
    private boolean addWireMode=false;

    private ImageView powerButton_1,powerButton_2,powerButton_3;
    private Button addWire,selectGate,undoLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_screen);

        powerButton_1 =(ImageView)findViewById(R.id.imageView_power_1);
        powerButton_1.setOnClickListener(this);

        powerButton_2 =(ImageView)findViewById(R.id.imageView_power_2);
        powerButton_2.setOnClickListener(this);

        powerButton_3 =(ImageView)findViewById(R.id.imageView_power_3);
        powerButton_3.setOnClickListener(this);

        addWire=(Button)findViewById(R.id.button_add_wire);
        addWire.setOnClickListener(this);

        selectGate=(Button)findViewById(R.id.button_select_gate);
        selectGate.setOnClickListener(this);

        undoLast=(Button)findViewById(R.id.button_undo_gate);
        undoLast.setOnClickListener(this);

        GridView gridView = (GridView) findViewById(R.id.image_grid_view);

        if (gridView == null)
            toast("Unable to find GridView");
        else {
            gridView.setAdapter(new ImageCellAdapter(this));
        }
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
    public void setPowerButton(View view){
        ImageView powerButton=(ImageView)findViewById(view.getId());
        String tag=powerButton.getTag().toString();
        if(tag.equals("power_off")){
            powerButton.setImageResource(R.drawable.power_on);
            powerButton.setTag("power_on");
        }
        if(tag.equals("power_on")){
            powerButton.setImageResource(R.drawable.power_off);
            powerButton.setTag("power_off");
        }
        toast(tag);
    }
    public void addNewImageToScreen(int resourceId) {
        if (lastNewCell != null) lastNewCell.setVisibility(View.GONE);

        FrameLayout imageHolder = (FrameLayout) findViewById(R.id.image_source_frame);
        if (imageHolder != null) {

            FrameLayout.LayoutParams lp = new FrameLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

            ImageCell newView = new ImageCell(this);
            newView.setGate(Gate.logicGateFactory(resourceId));
            newView.setImageResource(resourceId);
            imageHolder.addView(newView, lp);
            newView.isEmpty = false;
            newView.cellNumber = -1;
            lastNewCell = newView;

            newView.setOnClickListener(this);
            newView.setOnTouchListener(this);
            toast(newView.toString());

        }
    }



    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_wire:
                addWireMode=true;
                break;
            case R.id.button_select_gate:
                toast("click");
                addNewImageToScreen(R.drawable.or_gate);
                break;
            default:
                setPowerButton(view);
                break;
        }
    }
    @Override
    public boolean onLongClick(View view) {
        if(addWireMode){
            /*need to reference whether this is the start of the wire or the end of the wire.
            * if it is the start of the wire then a refernce should be saved (perhaps in Wire?),
            * and a marker set so that the next longClick can represent the end of the wire.
            * if it is the end of the wire, then might need to check this is a valid cell to connect
            * a wire to (ie. isEmpty =false or hasGate?) and then add the wire. this selection should take the
            * user out of wire mode also.*/
        }

        return false;
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return startDrag(view);
        }
        return false;
    }
    @Override public boolean onDrag (View view, DragEvent event) {

        boolean isDragSource = false, isDropTarget = false;
        DragSource source = null;
        DropTarget target = null;
        try {
            source = (DragSource) view;
            isDragSource = true;
        } catch (ClassCastException ex) {}
        try {
            target = (DropTarget) view;
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
}
