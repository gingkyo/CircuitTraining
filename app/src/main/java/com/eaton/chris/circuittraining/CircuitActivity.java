package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CircuitActivity extends Activity
        implements View.OnDragListener,
        View.OnClickListener,
        View.OnLongClickListener,
        View.OnTouchListener
{
    WireSurface wireSurface;
    private DragSource dragSource;
    private ImageCell lastNewCell=null;
    private ImageView startOfWire=null;
    private boolean addWireMode=false;
    private TextView addWireLabel;

    private ImageView powerButton_1,powerButton_2,powerButton_3;
    private Button addWire,selectGate,undoLast;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_screen);

        wireSurface= (WireSurface)findViewById(R.id.surfaceView_wireCanvas);
        powerButton_1 =(ImageView)findViewById(R.id.imageView_power_1);
        powerButton_1.setOnClickListener(this);
        powerButton_1.setOnLongClickListener(this);

        powerButton_2 =(ImageView)findViewById(R.id.imageView_power_2);
        powerButton_2.setOnClickListener(this);
        powerButton_2.setOnLongClickListener(this);

        powerButton_3 =(ImageView)findViewById(R.id.imageView_power_3);
        powerButton_3.setOnClickListener(this);
        powerButton_3.setOnLongClickListener(this);

        addWire=(Button)findViewById(R.id.button_add_wire);
        addWire.setOnClickListener(this);

        selectGate=(Button)findViewById(R.id.button_select_gate);
        selectGate.setOnClickListener(this);

        undoLast=(Button)findViewById(R.id.button_undo_gate);
        undoLast.setOnClickListener(this);

        addWireLabel=(TextView)findViewById(R.id.textView_addWireLabel);
        addWireLabel.setVisibility(View.INVISIBLE);

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
    v.startDrag(dragData, shadowView, ds, 0);
    return true;
}
    public void setPowerButton(View view){
        ImageView powerButton=(ImageView)findViewById(view.getId());
        if(powerButtonIsLive(view)){
            powerButton.setImageResource(R.drawable.power_off);
            powerButton.setTag("power_off");
        } else{
            powerButton.setImageResource(R.drawable.power_on);
            powerButton.setTag("power_on");
        }

    }
    public void addNewImageToScreen(int resourceId) {
        if (lastNewCell != null) lastNewCell.setVisibility(View.GONE);

        FrameLayout imageHolder = (FrameLayout) findViewById(R.id.image_source_frame);
        if (imageHolder != null) {

            FrameLayout.LayoutParams lp = new FrameLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

            ImageCell newView = new ImageCell(this);
            //newView.setGate(Gate.logicGateFactory(resourceId));
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==CircuitActivity.RESULT_OK){
                String gateType=data.getStringExtra("gateName");
                addNewImageToScreen(Gate.getGateImageByName(gateType));
            }
            if(resultCode==CircuitActivity.RESULT_CANCELED){
                toast("Cancelled");
                //lblQuestionText.setText("ask a question..");
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(!addWireMode){
            int id= (view.getId());
            if(id== R.id.button_select_gate) {
                Intent selectGate =new Intent(CircuitActivity.this,SelectGateActivity.class);
                startActivityForResult(selectGate, 1);
                //addNewImageToScreen(R.drawable.or_gate);
            } else if(id==R.id.button_add_wire){
                setAddWireMode(true);
            } else if(id==R.id.button_undo_gate){
                toast("TODO UNDO LAST BUTTON");
            } else{
                setPowerButton(view);
            }
        }
    }
    @Override
    public boolean onLongClick(View view) {
        if(addWireMode){
            if(startOfWire==null){
                startOfWire=(ImageView) view;
                return true;
            }
            if(view.equals(startOfWire)){
                toast("line cannot start and end at same place");
                setAddWireMode(false);
                startOfWire=null;
                return addWireMode;//break?
            }
            if(viewIsPowerButton(view)){
                toast("cannot end a wire at a power button");
                setAddWireMode(false);
                startOfWire=null;
                return addWireMode;//break??
            }

            boolean wireIsLive;
            if(viewIsPowerButton(startOfWire)){
                wireIsLive=powerButtonIsLive(startOfWire);
            }//TODO check this logic - this should be else as the above only covers powerbuttons
            ImageCell gateIcon=(ImageCell)view;
            Gate gate=gateIcon.getGate();
            wireIsLive=gate.getOutput(gate.getGateType());
            Wire wire =new Wire(wireIsLive);
            gate.addConnection(wire);
            float startX=startOfWire.getX();
            if(!viewIsPowerButton(startOfWire))
                startX+=startOfWire.getWidth();
            float startY=(startOfWire.getHeight()/2)+startOfWire.getY();
            float endY=(gateIcon.getHeight()/2)+gateIcon.getY();

            wireSurface.setLineCoords(startX,startY,gateIcon.getX(),endY);
            wireSurface.isNewWire=true;
            wireSurface.invalidate();
            startOfWire=null;
            setAddWireMode(false);
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
    public boolean powerButtonIsLive(View view){
        String buttonTag=view.getTag().toString();
        return buttonTag.equals("power_on");
    }
    public boolean viewIsPowerButton(View view){
        return view.equals(powerButton_1)|view.equals(powerButton_2)
                |view.equals(powerButton_3);
    }
    public void setAddWireMode(boolean isAddWireMode){
        addWireMode=isAddWireMode;
        startOfWire=null;
        if(addWireMode){
            addWireLabel.setVisibility(View.VISIBLE);
        } else {
            addWireLabel.setVisibility(View.INVISIBLE);
        }

    }
}
