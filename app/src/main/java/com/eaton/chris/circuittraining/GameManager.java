package com.eaton.chris.circuittraining;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by chris on 03/05/2016.
 */
public class GameManager {
    private static boolean[] level0 = {true, true, true, true, true};
    private static boolean[] level1 = {true, true, true, false, false};
    private static boolean[] level2 = {false, false, false, true, true};
    private static boolean[] level3 = {true, true, false, false, false};
    private static boolean[] level4 = {false, false, false, true, false};
    private int currentLevel;
    String [] questionText;
    ArrayList <ImageCell> gateList;
    ArrayList <CircuitComponent> mainComponents;


    public GameManager(Context context){
        currentLevel=0;
        questionText= context.getResources().getStringArray(R.array.questions);
        gateList=new ArrayList<>();
        mainComponents=new ArrayList<>();

    }
        public boolean checkWinCondition() {
        boolean[] solution = getWinCondition();
        for (int i = 0; i < mainComponents.size(); i++) {
            if (!solution[i] == mainComponents.get(i).isLive()) {
                return false;
            }
        }
        return true;
    }

    public boolean[] getWinCondition() {
        if (currentLevel == 0) {
            return level0;
        } else if (currentLevel == 1) {
            return level1;
        } else if (currentLevel == 2) {
            return level2;
        } else if (currentLevel == 3) {
            return level3;
        }
        return level4;
    }
    public int getLevel(){
        return currentLevel;
    }
    public void advanceLevel(){
        currentLevel++;
    }
    public void addGate(ImageCell newGate){
        gateList.add(newGate);
    }
    public void addMainComponent(CircuitComponent component){
        mainComponents.add(component);
    }
    public String getCurrentQuestion(){
        return questionText[currentLevel];
    }
    public boolean checkIsCircuit() {
        if (gateList.size() > 0) {
            for (ImageCell gate : gateList) {
                if (!gate.isConnected()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public void resetComponentValues(){
        for(CircuitComponent c :mainComponents){
            c.resetComponent();
        }
    }
}
