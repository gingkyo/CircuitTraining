package com.eaton.chris.circuittraining;

import java.util.ArrayList;

class Gate {
    protected Wire conn1;
    protected Wire conn2;
    //private boolean output;
    protected String gateType;
    protected int gateInfoResID;
    protected int gateDrawableResID;

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
                return new AndGate();
            case "nandGate":
                return new NandGate();
            case "norGate":
                return new NorGate();
            case "notGate":
                return new NotGate();
            case "orGate":
                return new OrGate();
            case "xnorGate":
                return new XnorGate();
            case "xorGate":
                return new XorGate();
        }
        return null;
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
    public int getGateInfoResID(){
        return gateInfoResID;
    }
    public int getGateDrawableResID(){
        return gateDrawableResID;
    }
}
class AndGate extends Gate
{
    public AndGate(){
        super("andGate");
        gateInfoResID=R.string.and_gate;
        gateDrawableResID=R.drawable.and_gate;
    }
}
class OrGate extends Gate
{
    public OrGate(){

        super("orGate");
        gateInfoResID=R.string.or_gate;
        gateDrawableResID=R.drawable.or_gate;
    }
}
class XorGate extends Gate
{
    public XorGate(){

        super("xorGate");
        gateInfoResID=R.string.xor_gate;
        gateDrawableResID=R.drawable.xor_gate;
    }
}
class NandGate extends Gate
{
    public NandGate(){

        super(true,"nandGate");
        gateInfoResID=R.string.nand_gate;
        gateDrawableResID=R.drawable.nand_gate;
    }
}
class NorGate extends Gate
{
    public NorGate(){

        super(true,"norGate");
        gateInfoResID=R.string.nor_gate;
        gateDrawableResID=R.drawable.nor_gate;
    }
}
class NotGate extends Gate
{
    public NotGate(){
        super("notGate");
        gateInfoResID=R.string.not_gate;
        gateDrawableResID=R.drawable.not_gate;
    }
}
class XnorGate extends Gate
{
    public XnorGate(){

        super(true,"xnorGate");
        gateInfoResID=R.string.xnor_gate;
        gateDrawableResID=R.drawable.xnor_gate;
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
