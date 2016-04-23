package com.eaton.chris.circuittraining;

import java.util.ArrayList;

public abstract class GateUtility {
    public static Gate buildGate(String gateType){
        switch(gateType){
            case "andGate":
                return new Gate("andGate");
            case "nandGate":
                return new Gate("nandGate");
            case "norGate":
                return new Gate("norGate");
            case "notGate":
                return new Gate("notGate");
            case "orGate":
                return new Gate("orGate");
            case "xnorGate":
                return new Gate("xnorGate");
            case "xorGate":
                return new Gate("xorGate");
        }
        return null;
    }
    public static ArrayList<Gate> buildFullGateList() {
        ArrayList<Gate>gates=new ArrayList<>();
        gates.add(buildGate("andGate"));
        gates.add(buildGate("nandGate"));
        gates.add(buildGate("norGate"));
        gates.add(buildGate("notGate"));
        gates.add(buildGate("orGate"));
        gates.add(buildGate("xnorGate"));
        gates.add(buildGate("xorGate"));

        return gates;
    }
    public static int getGateDescByName(String gateName){
        switch (gateName) {
            case "andGate":
                return R.string.and_gate;
            case "nandGate":
                return R.string.nand_gate;
            case "norGate":
                return R.string.nor_gate;
            case "notGate":
                return R.string.not_gate;
            case "orGate":
                return R.string.or_gate;
            case "xnorGate":
                return R.string.xnor_gate;
            case "xorGate":
                return R.string.xor_gate;
        }
        return 0;
    }

    public static int getGateImageByName(String gateName) {
        switch (gateName) {
            case "andGate":
                return R.drawable.and_gate;
            case "nandGate":
                return R.drawable.nand_gate;
            case "norGate":
                return R.drawable.nor_gate;
            case "notGate":
                return R.drawable.not_gate;
            case "orGate":
                return R.drawable.or_gate;
            case "xnorGate":
                return R.drawable.xnor_gate;
            case "xorGate":
                return R.drawable.xor_gate;
        }
        return 0;
    }
}
