package com.eaton.chris.circuittraining;

class Gate {
    private Wire conn1;
    private Wire conn2;
    private boolean output;
    private String gateType;

    public static Gate logicGateFactory(int resourceId) {
        switch (resourceId) {
            case R.drawable.and_gate:
                return new AndGate();
            case R.drawable.nand_gate:
                return new NandGate();
            case R.drawable.nor_gate:
                return new NorGate();
            case R.drawable.not_gate:
                return new NotGate();
            case R.drawable.or_gate:
                return new OrGate();
            case R.drawable.xnor_gate:
                return new XnorGate();
            case R.drawable.xor_gate:
                return new XorGate();
        }
        return null;
    }

    public Gate(boolean output,String gateType){
        this.output=output;
        this.gateType=gateType;
    }
    public Gate(String gateType){

        output=false;
        this.gateType=gateType;
    }
    public boolean setOutput(String gateType){
        if(!isConnected()){
            switch(gateType){
                case "notGate":
                    return !conn1.getStatus();
                case "andGate":
                    return conn1.getStatus() && conn2.getStatus();
                case "orGate":
                    return conn1.getStatus() |conn2.getStatus();
                case "xorGate":
                    if(setOutput("andGate")){
                        return false;
                    }else{
                        setOutput("orGate");
                    }
                case "nandGate":
                    return !setOutput("andGate");
                case "norGate":
                    return !setOutput("orGate");
                case "xnorGate":
                    boolean dbleNegative=!conn1.getStatus() && !conn2.getStatus();
                    return setOutput("andGate") | dbleNegative;
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
        if(conn1.equals(null)){
            conn1=newConn;
            if(gateType.equals("notGate")){
                return true;
            }
        }
        else{
            conn2=newConn;
        }
        return true;
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
