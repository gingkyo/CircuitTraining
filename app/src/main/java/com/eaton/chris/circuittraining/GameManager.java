package com.eaton.chris.circuittraining;


import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by chris on 27/04/2016.
 */
public class GameManager {
    private int gameBoard;
    private ArrayList <Gate> gateList;


    public GameManager(){
        gateList=new ArrayList<>();
        gameBoard=0;
    }
    public boolean checkCircuitIsConnected(){
        for(Gate gate :gateList){
            if(!gate.isConnected()){
                return false;
            }
        }
        return true;
    }
    public void addGateToList(Gate gate){
        gateList.add(gate);
    }
}
