package com.eaton.chris.circuittraining;

public interface DragSource {

    boolean allowDrag ();

    void setGate(String gateType);
    String getGate();

}
