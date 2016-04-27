package com.eaton.chris.circuittraining;


public interface CircuitComponent {
    boolean setOutput(Wire wire);
    boolean addInput(Wire wire);
    void updateSignal();
    //void setIsLive(boolean isLive);
    boolean isLive();
    void resetComponent();

}
