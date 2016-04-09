package com.eaton.chris.circuittraining;

public interface DragSource {

    boolean allowDrag ();

    void setGate(Gate gate);
    Gate getGate();

}
