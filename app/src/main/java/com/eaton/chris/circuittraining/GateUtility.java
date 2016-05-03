package com.eaton.chris.circuittraining;

import java.util.ArrayList;

public abstract class GateUtility {

    public static ArrayList<String> buildFullGateList() {
        ArrayList<String>gates=new ArrayList<>();
        gates.add("andGate");
        gates.add("nandGate");
        gates.add("norGate");
        gates.add("notGate");
        gates.add("orGate");
        gates.add("xnorGate");
        gates.add("xorGate");

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
