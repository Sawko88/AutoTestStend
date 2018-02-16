package sourse;

import java.io.Serializable;

public class Signal implements Serializable{
    public String name = "";
    public int index = 0;
    public String text = "";
    public boolean state = false;

    public Signal(int i, String name) {
        this.index = i ;
        this.name = name;
    }
}
