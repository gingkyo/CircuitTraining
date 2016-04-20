package com.eaton.chris.circuittraining;

public interface DropTarget {

    boolean allowDrop (DragSource source);

    void onDrop (DragSource source);

    boolean onDragEnter (DragSource source);

    boolean onDragExit (DragSource source);

    void setGate(CircuitComponent gate);
}