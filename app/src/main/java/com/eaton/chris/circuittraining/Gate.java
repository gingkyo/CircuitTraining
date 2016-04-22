package com.eaton.chris.circuittraining;

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
                    } else {
                        isLive = conn1.isLive() || conn2.isLive();
                    }
                    break;
                case "nandGate":
                    isLive = !conn1.isLive() && !conn2.isLive();
                    break;
                case "norGate":
                    isLive = !conn1.isLive() || !conn2.isLive();
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
    public String getGateType(){

        return gateType;
    }
}

