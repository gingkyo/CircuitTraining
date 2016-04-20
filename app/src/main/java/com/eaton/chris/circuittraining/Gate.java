package com.eaton.chris.circuittraining;

import java.util.ArrayList;

class Gate {
    private Wire conn1;
    private Wire conn2;
    //private boolean output;
    private String gateType;
    public static int getGateDescByName(String gateName){
        switch (gateName) {
            case "andGate":
                return R.string.andGate;
            case "nandGate":
                return R.string.nandGate;
            case "norGate":
                return R.string.norGate;
            case "notGate":
                return R.string.notGate;
            case "orGate":
                return R.string.orGate;
            case "xnorGate":
                return R.string.xnorGate;
            case "xorGate":
                return R.string.xorGate;
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
    public static ArrayList<Gate> buildFullGateList() {
        ArrayList<Gate>gates=new ArrayList<>();
        gates.add(new AndGate());
        gates.add(new NandGate());
        gates.add(new NorGate());
        gates.add(new NotGate());
        gates.add(new OrGate());
        gates.add(new XnorGate());
        gates.add(new XorGate());

        return gates;
    }
    public Gate(boolean output,String gateType){
        //this.output=output;
        this.gateType=gateType;
    }
    public Gate(String gateType){

       // output=false;
        this.gateType=gateType;
    }
    public boolean getOutput(String gateType){
        if(isConnected()){
            switch(gateType){
                case "notGate":
                    return !conn1.getStatus();
                case "andGate":
                    return conn1.getStatus() && conn2.getStatus();
                case "orGate":
                    return conn1.getStatus() |conn2.getStatus();
                case "xorGate":
                    if(getOutput("andGate")){
                        return false;
                    }else{
                        getOutput("orGate");
                    }
                case "nandGate":
                    return !getOutput("andGate");
                case "norGate":
                    return !getOutput("orGate");
                case "xnorGate":
                    boolean dbleNegative=!conn1.getStatus() && !conn2.getStatus();
                    return getOutput("andGate") | dbleNegative;
            }
        }
        return false;
    }
    public boolean isConnected(){
        if(!gateType.equals("notGate"))
        {
            return conn1!=null && conn2!=null;
        }
        return conn1!=null;
    }
    public boolean addConnection(Wire newConn){
        if(isConnected()){
            return false;
        }
       // if(conn1==null){
            conn1=newConn;
            if(gateType.equals("notGate")){
                return true;
            }
       // }
//        else{
//            conn2=newConn;
//        }
        return true;
    }
    public String getGateType(){
        return gateType;
    }
}
class AndGate extends Gate
{
    public AndGate(){
        super("andGate");
    }
}
class OrGate extends Gate
{
    public OrGate(){
        super("orGate");
    }
}
class XorGate extends Gate
{
    public XorGate(){
        super("xorGate");
    }
}
class NandGate extends Gate
{
    public NandGate(){
        super(true,"nandGate");
    }
}
class NorGate extends Gate
{
    public NorGate(){
        super(true,"norGate");
    }
}
class NotGate extends Gate
{
    public NotGate(){
        super("notGate");
    }
}
class XnorGate extends Gate
{
    public XnorGate(){
        super(true,"xnorGate");
    }
}
class Wire
{
    private boolean isLive;

    public Wire(boolean isLive){
        this.isLive=isLive;
    }
    public boolean getStatus(){
        return isLive;
    }

}
