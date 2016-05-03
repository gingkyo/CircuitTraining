package com.eaton.chris.circuittraining;


public interface CircuitComponent {
    boolean setOutput(Wire wire);
    boolean addInput(Wire wire);
    void updateSignal();
    boolean isLive();
    void resetComponent();
    boolean isPowerButton();
    boolean isLightBulb();

}
