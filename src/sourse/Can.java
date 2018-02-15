package sourse;

import java.io.Serializable;

public class Can implements Serializable{
    String name = "";
    Integer index;
    Integer state;
    String curstait="";
    String onstait = "";
    String offstait = "";
    public int baytnumber = 0;
    public int bitnumber = 0;

    public Can(int index, String name,  int state) {
        this.name = name;
        this.index = index;
        this.state = state;
    }

    public Can(int index, int state) {
        this.index = index;
        this.state = state;
    }

    public Can(int index, String name, String onstait, String offstait) {
        this.name = name;
        this.index = index;
        this.onstait = onstait;
        this.offstait = offstait;
    }

    public Can(int index, String name, String onstait, String offstait, int baytnumber, int bitnumber) {
        this.name = name;
        this.index = index;
        this.onstait = onstait;
        this.offstait = offstait;
        this.baytnumber = baytnumber;
        this.bitnumber = bitnumber;
    }
}
