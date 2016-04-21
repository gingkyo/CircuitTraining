package com.eaton.chris.circuittraining;

import java.util.ArrayList;

public class Gate implements CircuitComponent{

    private String gateType;
    private Wire conn1;
    private Wire conn2;
    private Wire output;
    private boolean isLive;
    protected int gateInfoResID;
    protected int gateDrawableResID;

    public Gate(String gateType,int gateInfoResID,int gateDrawableResID){
        this.gateType=gateType;
        this.gateInfoResID=gateInfoResID;
        this.gateDrawableResID=gateDrawableResID;
        conn1=null;
        conn2=null;
        output=null;
        isLive=false;
    }

    public boolean setOutput(Wire wire){
        if(output!=null)
            return false;
        output=wire;
        return true;
    }
    public void setIsLive(boolean isLive){
        this.isLive=isLive;
    }
    public boolean isLive() {
        return isLive;
    }
    public boolean addInput(Wire wire){
        if(isConnected()){
            return false;
        }
        if(conn1==null){
            conn1=wire;
            if(gateType.equals("notGate")){
            }
        }
        else{
            conn2=wire;
        }
        return true;
    }
    public boolean isConnected(){
        if(!gateType.equals("notGate"))
        {
            return conn1!=null && conn2!=null;
        }
        return conn1!=null;
    }
    public void updateSignal() {
        if (isConnected()) {
            switch (gateType) {
                case "notGate":
                    isLive = !conn1.isLive();
                case "andGate":
                    isLive = conn1.isLive() && conn2.isLive();
                case "orGate":
                    isLive = conn2.isLive() || conn1.isLive();
                case "xorGate":
                    if (conn1.isLive() && conn2.isLive()) {
                        isLive = false;
                    } else {
                        isLive = conn1.isLive() || conn2.isLive();
                    }
                case "nandGate":
                    isLive = !conn1.isLive() && !conn2.isLive();
                case "norGate":
                    isLive = !conn1.isLive() || !conn2.isLive();
                case "xnorGate":
                    boolean dbleNegative = !conn1.isLive() && !conn2.isLive();
                    isLive = conn1.isLive() && conn2.isLive() || dbleNegative;
            }
            if(output!=null){
                output.setIsLive(isLive());
            }
        }
    }
    public String getGateType(){

        return gateType;
    }
    public int getGateInfoResID(){
        return gateInfoResID;
    }
    public int getGateDrawableResID(){
        return gateDrawableResID;
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
    public static Gate buildGate(String gateType){
        switch(gateType){
            case "andGate":
                return new Gate("andGate",R.string.and_gate,R.drawable.and_gate);
            case "nandGate":
                return new Gate("nandGate",R.string.nand_gate,R.drawable.nand_gate);
            case "norGate":
                return new Gate("norGate",R.string.nor_gate,R.drawable.nor_gate);
            case "notGate":
                return new Gate("notGate",R.string.not_gate,R.drawable.not_gate);
            case "orGate":
                return new Gate("orGate",R.string.or_gate,R.drawable.or_gate);
            case "xnorGate":
                return new Gate("xnorGate",R.string.xnor_gate,R.drawable.xnor_gate);
            case "xorGate":
                return new Gate("xorGate",R.string.xor_gate,R.drawable.xor_gate);
        }
        return null;
    }
    public static ArrayList<Gate> buildFullGateList() {
        ArrayList<Gate>gates=new ArrayList<>();
        gates.add(new Gate("andGate",R.string.and_gate,R.drawable.and_gate));
        gates.add(new Gate("nandGate",R.string.nand_gate,R.drawable.nand_gate));
        gates.add(new Gate("norGate",R.string.nor_gate,R.drawable.nor_gate));
        gates.add(new Gate("notGate",R.string.not_gate,R.drawable.not_gate));
        gates.add(new Gate("orGate",R.string.or_gate,R.drawable.or_gate));
        gates.add(new Gate("xnorGate",R.string.xnor_gate,R.drawable.xnor_gate));
        gates.add(new Gate("xorGate",R.string.xor_gate,R.drawable.xor_gate));

        return gates;
    }
}

