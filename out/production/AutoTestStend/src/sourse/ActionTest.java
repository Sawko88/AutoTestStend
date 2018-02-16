package sourse;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ActionTest implements Serializable {
    public ActionTest(int number, TypeAction type, String name,  String code, String onstait, String offstait) {
        this.number = number;
        this.type = type;
        this.name = name;
        this.onstait = onstait;
        this.offstait = offstait;
        this.code = code;
    }

    public ActionTest(int index) {
        this.number = index;
    }

    public ActionTest(int number, TypeAction type, String name, KOmanda kOmanda) {
        this.number = number;
        this.type = type;
        this.name = name;
        this.kOmanda = kOmanda;
    }

    public ActionTest(int number, TypeAction type, String name, Can can) {
        this.number = number;
        this.type = type;
        this.name = name;
        this.can = can;
    }

    public  enum TypeAction{
        ONOFF, PWRONOFF, PWRNAP, CAN, MOTOR, RARELE, METKAONOFF, NONE, GSMCOM, PAUTHA
    }

    public Can can = new Can(0, "",0);
    public KOmanda kOmanda = new KOmanda(0, "","");
    public int number =0;
    public TypeAction type = TypeAction.NONE;
    public String name ="";
    public String namePosition = " ";
    public String code = "";
    public String position = "";
    public String onstait = "";
    public String offstait = "";
    public String currentstait = "0";


}
