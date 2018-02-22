package sourse;

import java.io.Serializable;

public class Signal implements Serializable{
    public String name = "";
    public int index = 0;
    public String text = "";
    public boolean state = false;
    public String code = "";

    public Signal(int index, SignalType type, String code, String name) {
        this.index = index;
        this.signalType = type;
        this.code = code;
        this.name = name;
    }

    public  enum SignalType {
        NONE, CONTROL , PARAM_OB, PARAM_GSM, PARAM_CAN, PARAM_VER
    }
    public SignalType signalType = SignalType.NONE;

    public Signal(int i, String name) {
        this.index = i ;
        this.name = name;
    }
}
