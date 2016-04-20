package com.eaton.chris.circuittraining;

public interface DragSource {

    boolean allowDrag ();

    void setGate(CircuitComponent gate);
    CircuitComponent getGate();

}
