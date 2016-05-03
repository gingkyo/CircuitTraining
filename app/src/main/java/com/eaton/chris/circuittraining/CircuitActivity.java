package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CircuitActivity extends Activity
        implements View.OnDragListener,
        View.OnClickListener,
        View.OnLongClickListener,
        View.OnTouchListener {
    WireSurface wireSurface;
    private DragSource dragSource;
    private String currentDraggableGate;
    private ImageCell lastNewCell = null;
    private CircuitComponent startOfWire = null;
    private boolean addWireMode = false;
    private TextView question;
    GameManager gameManager;

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if(mAccel>12){
            toast("shake it!");}
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_screen);

        gameManager=new GameManager(this);
        wireSurface = (WireSurface) findViewById(R.id.surfaceView_wireCanvas);
        PowerButton powerButton_1 = (PowerButton) findViewById(R.id.imageView_power_1);
        powerButton_1.setOnClickListener(this);
        powerButton_1.setOnLongClickListener(this);
        gameManager.addMainComponent(powerButton_1);

        mSensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;


        PowerButton powerButton_2 = (PowerButton) findViewById(R.id.imageView_power_2);
        powerButton_2.setOnClickListener(this);
        powerButton_2.setOnLongClickListener(this);
        gameManager.addMainComponent(powerButton_2);

        PowerButton powerButton_3 = (PowerButton) findViewById(R.id.imageView_power_3);
        powerButton_3.setOnClickListener(this);
        powerButton_3.setOnLongClickListener(this);
        gameManager.addMainComponent(powerButton_3);

        LightBulb bulb_0 = (LightBulb) findViewById(R.id.imageView_bulb_0);
        bulb_0.setOnLongClickListener(this);
        gameManager.addMainComponent(bulb_0);

        LightBulb bulb_1 = (LightBulb) findViewById(R.id.imageView_bulb_1);
        bulb_1.setOnLongClickListener(this);
        gameManager.addMainComponent(bulb_1);

        Button addWire = (Button) findViewById(R.id.button_add_wire);
        addWire.setOnClickListener(this);

        Button selectGate = (Button) findViewById(R.id.button_select_gate);
        selectGate.setOnClickListener(this);

        Button undoLast = (Button) findViewById(R.id.button_undo_gate);
        undoLast.setOnClickListener(this);

        TextView addWireLabel = (TextView) findViewById(R.id.textView_addWireLabel);
        addWireLabel.setVisibility(View.INVISIBLE);

        setNewGridView();

        question = (TextView) findViewById(R.id.question_textView);
        question.setText(gameManager.getCurrentQuestion());
        addAlertBox(true, "Welcome", "Read Instructions?");
    }

    public void addAlertBox(boolean isIntructions, String title, String message) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setCancelable(isIntructions);
        if (!isIntructions) {
            if (gameManager.getLevel() == 4) {
                ad.setPositiveButton(
                        "Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
            } else {
                ad.setPositiveButton(
                        "Next",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                gameManager.advanceLevel();
                                question.setText(gameManager.getCurrentQuestion());
                                resetGameBoard();
                            }
                        });
            }
        } else {
            ad.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent instructionIntent = new Intent(CircuitActivity.this, InstructionsActivity.class);
                            startActivity(instructionIntent);
                        }
                    });
            ad.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }
        AlertDialog alert = ad.create();
        alert.show();
    }
    public boolean startDrag(View v) {

        boolean isDragSource = false;
        DragSource ds = null;
        try {
            ds = (DragSource) v;
            isDragSource = true;
        } catch (ClassCastException ex) {

        }
        if (!isDragSource) return false;
        if (!ds.allowDrag()) return false;
        dragSource = ds;
        ClipData dragData = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowView = new View.DragShadowBuilder(v);
        v.startDrag(dragData, shadowView, ds, 0);
        return true;
    }

    public void addNewImageToScreen() {
        if (lastNewCell != null) lastNewCell.setVisibility(View.GONE);

        FrameLayout imageHolder = (FrameLayout) findViewById(R.id.image_source_frame);
        if (imageHolder != null) {

            FrameLayout.LayoutParams lp = new FrameLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

            ImageCell newView = new ImageCell(this);
            newView.setImageResource(GateUtility.getGateImageByName(currentDraggableGate));
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
        if (requestCode == 1) {
            if (resultCode == CircuitActivity.RESULT_OK) {
                currentDraggableGate = (String) data.getExtras().get("gateName");
                addNewImageToScreen();
            }
            if (resultCode == CircuitActivity.RESULT_CANCELED) {
                toast("Cancelled");
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (!addWireMode) {
            int id = (view.getId());
            if (id == R.id.button_select_gate) {
                Intent selectGate = new Intent(CircuitActivity.this, SelectGateActivity.class);
                startActivityForResult(selectGate, 1);
            } else if (id == R.id.button_add_wire) {
                setAddWireMode(true);
            } else if (id == R.id.button_undo_gate) {
                toast("TODO UNDO LAST BUTTON");
            } else {
                try {
                    PowerButton powerButton = (PowerButton) view;
                    powerButton.updateSignal();
                    if (gameManager.checkIsCircuit()) {
                        if (gameManager.checkWinCondition()) {
                            if (gameManager.getLevel() < 4) {
                                addAlertBox(false, "Well Done", "load next puzzle?");
                            } else {
                                addAlertBox(false, "Well Done", "You are the Circuit Master!!");
                            }
                        }
                    }
                } catch (ClassCastException e) {
                    toast("button failed");
                }
            }
        }
    }
    @Override
    public boolean onLongClick(View view) {
        if (addWireMode) {
            if (startOfWire == null) {
                startOfWire=(CircuitComponent)view;
                if (startOfWire.isLightBulb()) {
                    toast("wire cannot start from a bulb");
                    setAddWireMode(false);
                    return false;
                }
                return true;
            }
            CircuitComponent endOfWire=(CircuitComponent)view;
            if (endOfWire.equals(startOfWire)) {
                toast("line cannot start and end at the same place");
                setAddWireMode(false);
                return false;
            }
            if (endOfWire.isPowerButton()) {
                toast("cannot end a wire on a power button");
                setAddWireMode(false);
                return false;
            }
            Wire wire = new Wire(wireSurface, startOfWire, endOfWire);
            if (!startOfWire.setOutput(wire)) {
                toast("Cannot add a second output wire");
                setAddWireMode(false);
                return false;
            }
            if (!endOfWire.addInput(wire)) {
                toast("No input slots left");
                setAddWireMode(false);
                return false;
            }
            wire.buildWireCoords();
            wireSurface.wireArray.add(wire);
            wire.setIsLive(startOfWire.isLive());
        }
        setAddWireMode(false);
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
    @Override
    public boolean onDrag(View view, DragEvent event) {

        boolean isDragSource = false, isDropTarget = false;
        DragSource source = null;
        DropTarget target = null;
        try {
            source = (DragSource) view;
            isDragSource = true;
        } catch (ClassCastException ex) {
        }
        try {
            target = (DropTarget) view;
            isDropTarget = true;
        } catch (ClassCastException ex) {
        }

        final int action = event.getAction();

        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:

                if (isDragSource) {
                    if (source == dragSource) {
                        if (source.allowDrag()) {
                            return true;
                        }
                    } else {
                        return isDropTarget && target.allowDrop(dragSource);
                    }
                } else {
                    return isDropTarget && target.allowDrop(dragSource);
                }
            case DragEvent.ACTION_DRAG_ENTERED:
                if (isDropTarget)
                    return isDropTarget && target.onDragEnter(dragSource);

            case DragEvent.ACTION_DRAG_EXITED:
                return isDropTarget && target.onDragExit(dragSource);

            case DragEvent.ACTION_DROP:
                if (isDropTarget) {
                    if (target.allowDrop(dragSource)) {
                        target.onDrop(dragSource);
                    }
                }
                target.setGate(currentDraggableGate);
                gameManager.addGate((ImageCell)target);
                return isDropTarget;

            case DragEvent.ACTION_DRAG_ENDED:
                break;
        }
        return false;
    }
    public void setAddWireMode(boolean isAddWireMode) {
        addWireMode = isAddWireMode;
        TextView addWireLabel=(TextView)findViewById(R.id.textView_addWireLabel);
        startOfWire = null;
        if (addWireMode) {
            addWireLabel.setVisibility(View.VISIBLE);
        } else {
            addWireLabel.setVisibility(View.INVISIBLE);
        }
    }
    public void setNewGridView(){
        GridView gridView = (GridView) findViewById(R.id.image_grid_view);

        if (gridView == null)
            toast("Unable to find GridView");
        else {
            gridView.setAdapter(new ImageCellAdapter(this));
        }
    }
    public void resetCurrentGate(){
        lastNewCell.isEmpty=true;
        lastNewCell.setVisibility(View.GONE);
        currentDraggableGate=null;
        lastNewCell.setOnClickListener(this);
        lastNewCell.setOnTouchListener(this);
    }
    public void resetGameBoard(){
        setNewGridView();
        resetCurrentGate();
        gameManager.resetComponentValues();
        wireSurface.wireArray=new ArrayList<>();
        wireSurface.resetCanvas();
    }
}
