package com.eaton.chris.circuittraining;

public class Gate implements CircuitComponent{

    private String gateType;
    private Wire conn1;
    private Wire conn2;
    private Wire output;
    private boolean isLive;

    public Gate(String gateType){
        this.gateType=gateType;
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
    public boolean isLive() {
        return isLive;
    }
    public boolean addInput(Wire wire){
        if(isConnected()){
            return false;
        }
        if(conn1==null){
            conn1=wire;
            if(gateType.equals("notGate")) return true;
        }
        else{
            conn2=wire;
        }
        return true;
    }
    public boolean isConnected(){
        if(gateType.equals("notGate")) return conn1!=null;
        return conn1!=null && conn2!=null;
    }
    public void updateSignal() {
        if (isConnected()) {
            switch (gateType) {
                case "notGate":
                    isLive = !conn1.isLive();
                    break;
                case "andGate":
                    isLive = conn1.isLive() && conn2.isLive();
                    break;
                case "orGate":
                    isLive = conn2.isLive() || conn1.isLive();
                    break;
                case "xorGate":
                    if (conn1.isLive() && conn2.isLive()) {
                        isLive = false;
                        break;
                    }
                    isLive = conn1.isLive() || conn2.isLive();
                    break;
                case "nandGate":
                    if(conn1.isLive() && conn2.isLive()){
                        isLive=false;
                        break;
                    }
                    isLive=true;
                    break;
                case "norGate":
                    isLive = !conn1.isLive() && !conn2.isLive();
                    break;
                case "xnorGate":
                    boolean dbleNegative = !conn1.isLive() && !conn2.isLive();
                    isLive = conn1.isLive() && conn2.isLive() || dbleNegative;
                    break;
            }
            if(output!=null){
                output.setIsLive(isLive);
            }
        }
    }
    public void resetComponent(){}
    public String getGateType(){
        return gateType;
    }
}

