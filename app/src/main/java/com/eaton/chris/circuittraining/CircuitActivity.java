package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class CircuitActivity extends Activity
implements  View.OnTouchListener,
            View.OnClickListener,
            View.OnLongClickListener{
    private DragController dragController;
    private ImageCell lastNewCell=null;
    private ImageView powerButton_1,powerButton_2,powerButton_3;
    private boolean addWireMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_screen);
        dragController = new DragController();

        powerButton_1 =(ImageView)findViewById(R.id.imageView_power_1);
        powerButton_1.setOnClickListener(this);

        powerButton_2 =(ImageView)findViewById(R.id.imageView_power_2);
        powerButton_2.setOnClickListener(this);

        powerButton_3 =(ImageView)findViewById(R.id.imageView_power_3);
        powerButton_3.setOnClickListener(this);

        GridView gridView = (GridView) findViewById(R.id.image_grid_view);

        if (gridView == null)
            toast("Unable to find GridView");
        else {
            gridView.setAdapter(new ImageCellAdapter(this, dragController));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_wire:
                addWireMode=true;
                break;
            case R.id.button_select_gate:
                addNewImageToScreen(R.drawable.or_gate);
                break;
            case R.id.button_undo_gate:
                break;
            case R.id.imageView_power_1 :
                setPowerButton(powerButton_1);
                break;
            case R.id.imageView_power_2 :
                setPowerButton(powerButton_2);
                break;
            case R.id.imageView_power_3 :
                setPowerButton(powerButton_3);
                break;
            default:
                toast("Button Event Error");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return startDrag(v);
        }
        return false;
    }
    public boolean startDrag(View v) {

        if(!dragController.setDragSource(v)){
            return false;
        }
        ClipData dragData = ClipData.newPlainText("","");
        View.DragShadowBuilder shadowView = new View.DragShadowBuilder (v);
        v.startDrag(dragData, shadowView, v, 0);
        return true;
    }
    public void setPowerButton(ImageView powerButton){
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

        }
    }



    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
