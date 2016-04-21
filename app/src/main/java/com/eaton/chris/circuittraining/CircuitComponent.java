package com.eaton.chris.circuittraining;

import android.view.View;

/**
 * Created by chris on 20/04/2016.
 */
public interface CircuitComponent {
    void setOutput(Wire wire);
    boolean getOutput();
    boolean addInput(Wire newConn);
}
