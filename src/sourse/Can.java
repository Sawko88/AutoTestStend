package sourse;

import java.io.Serializable;

public class Can implements Serializable{
    String name = "";
    Integer index;
    Integer state;

    public Can(String name, int index, int state) {
        this.name = name;
        this.index = index;
        this.state = state;
    }

    public Can(int index, int state) {
        this.index = index;
        this.state = state;
    }
}
