package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
    private View startOfWire = null;
    private boolean addWireMode = false;
    private TextView addWireLabel;
    private LightBulb bulb_0;
    private LightBulb bulb_1;
    private GameManager gameManager;
    public ArrayList<Gate> gateList;
    private ArrayList<CircuitComponent> mainComponents;
    private int currentLevel;
    private TextView question;
    String [] questionText;

    private PowerButton powerButton_1, powerButton_2, powerButton_3;
    private static boolean[] level0 = {true, true, true, true, true};
    private static boolean[] level1 = {true, true, true, false, false};
    private static boolean[] level2 = {false, false, false, true, true};
    private static boolean[] level3 = {true, true, false, false, false};
    private static boolean[] level4 = {false, false, false, true, false};

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_screen);
        currentLevel = 0;
        gateList = new ArrayList<>();
        mainComponents = new ArrayList<>();
        gameManager = new GameManager();
        wireSurface = (WireSurface) findViewById(R.id.surfaceView_wireCanvas);
        powerButton_1 = (PowerButton) findViewById(R.id.imageView_power_1);
        powerButton_1.setOnClickListener(this);
        powerButton_1.setOnLongClickListener(this);
        powerButton_1.setGameManager(gameManager);
        mainComponents.add(powerButton_1);

        powerButton_2 = (PowerButton) findViewById(R.id.imageView_power_2);
        powerButton_2.setOnClickListener(this);
        powerButton_2.setOnLongClickListener(this);
        powerButton_2.setGameManager(gameManager);
        mainComponents.add(powerButton_2);


        powerButton_3 = (PowerButton) findViewById(R.id.imageView_power_3);
        powerButton_3.setOnClickListener(this);
        powerButton_3.setOnLongClickListener(this);
        powerButton_3.setGameManager(gameManager);
        mainComponents.add(powerButton_3);


        bulb_0 = (LightBulb) findViewById(R.id.imageView_bulb_0);
        bulb_0.setOnLongClickListener(this);
        mainComponents.add(bulb_0);


        bulb_1 = (LightBulb) findViewById(R.id.imageView_bulb_1);
        bulb_1.setOnLongClickListener(this);
        mainComponents.add(bulb_1);

        Button addWire = (Button) findViewById(R.id.button_add_wire);
        addWire.setOnClickListener(this);

        Button selectGate = (Button) findViewById(R.id.button_select_gate);
        selectGate.setOnClickListener(this);

        Button undoLast = (Button) findViewById(R.id.button_undo_gate);
        undoLast.setOnClickListener(this);

        addWireLabel = (TextView) findViewById(R.id.textView_addWireLabel);
        addWireLabel.setVisibility(View.INVISIBLE);

        setNewGridView();

        questionText=getResources().getStringArray(R.array.questions);
        question = (TextView) findViewById(R.id.question_textView);
        question.setText(questionText[currentLevel]);
        addAlertBox(true, "Welcome", "Read Instructions?");
    }

    public void addAlertBox(boolean isIntructions, String title, String message) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setCancelable(isIntructions);
        if (!isIntructions) {
            if (currentLevel == 4) {
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
                                currentLevel++;
                                question.setText(questionText[currentLevel]);
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
                    if (checkIsCircuit()) {
                        if (checkWinCondition()) {
                            if (currentLevel < 4) {
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
                if (viewIsLightBulb(view)) {
                    toast("wire cannot start from a bulb");
                    setAddWireMode(false);
                    return false;
                }
                startOfWire = view;
                return true;
            }
            if (view.equals(startOfWire)) {
                toast("line cannot start and end at the same place");
                setAddWireMode(false);
                return false;
            }
            if (viewIsPowerButton(view)) {
                toast("cannot end a wire on a power button");
                setAddWireMode(false);
                return false;
            }
            CircuitComponent startPoint;
            if (viewIsPowerButton(startOfWire)) {
                startPoint = (CircuitComponent) startOfWire;
            } else {
                ImageCell startCell = (ImageCell) startOfWire;
                startPoint = startCell.getGate();
            }
            CircuitComponent endPoint;
            if (viewIsLightBulb(view)) {
                endPoint = (CircuitComponent) view;

            } else {
                ImageCell temp = (ImageCell) view;
                endPoint = temp.getGate();
            }
            Wire wire = new Wire(wireSurface, startPoint, endPoint);
            if (!startPoint.setOutput(wire)) {
                toast("Cannot add a second output wire");
                setAddWireMode(false);
                return false;
            }
            if (!endPoint.addInput(wire)) {
                toast("No input slots left");
                setAddWireMode(false);
                return false;
            }
            wire.setWireCoords(buildWireCoords(startOfWire, view));
            wireSurface.wireArray.add(wire);
            wire.setIsLive(startPoint.isLive());
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
                Gate newGate = GateUtility.buildGate(currentDraggableGate);
                target.setGate(newGate);
                gateList.add(newGate);
                return isDropTarget;

            case DragEvent.ACTION_DRAG_ENDED:
                break;
        }
        return false;
    }

    public boolean checkIsCircuit() {
        if (gateList.size() > 0) {
            for (Gate gate : gateList) {
                if (!gate.isConnected()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean viewIsPowerButton(View view) {
        return view.equals(powerButton_1) | view.equals(powerButton_2)
                | view.equals(powerButton_3);
    }

    public boolean viewIsLightBulb(View view) {
        return view.equals(bulb_0) || view.equals(bulb_1);
    }

    public void setAddWireMode(boolean isAddWireMode) {
        addWireMode = isAddWireMode;
        startOfWire = null;
        if (addWireMode) {
            addWireLabel.setVisibility(View.VISIBLE);
        } else {
            addWireLabel.setVisibility(View.INVISIBLE);
        }
    }

    public float[] buildWireCoords(View start, View end) {
        float startX = start.getX();
        float endX;
        float endY;
        if (!viewIsPowerButton(start))
            startX += start.getWidth();
        if (viewIsLightBulb(end)) {
            View parent = (View) end.getParent();
            endX = parent.getX();
            endY = end.getY() + (end.getHeight() / 2);
        } else {
            endX = end.getX();
            endY = (end.getHeight() / 2) + end.getY();
        }
        float startY = (start.getHeight() / 2) + start.getY();
        float[] wireCoords = {startX, startY, endX, endY};
        return wireCoords;
    }

    public boolean checkWinCondition() {
        boolean[] solution = getWinCondition(currentLevel);
        for (int i = 0; i < mainComponents.size(); i++) {
            if (!solution[i] == mainComponents.get(i).isLive()) {
                return false;
            }
        }
        return true;
    }
    public void setNewGridView(){
        GridView gridView = (GridView) findViewById(R.id.image_grid_view);

        if (gridView == null)
            toast("Unable to find GridView");
        else {
            gridView.setAdapter(new ImageCellAdapter(this));
        }
    }

    public static boolean[] getWinCondition(int level) {
        if (level == 0) {
            return level0;
        } else if (level == 1) {
            return level1;
        } else if (level == 2) {
            return level2;
        } else if (level == 3) {
            return level3;
        }
        return level4;
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
        for(CircuitComponent c :mainComponents){
            c.resetComponent();
        }
        wireSurface.wireArray=new ArrayList<>();
        wireSurface.resetCanvas();
    }
}
