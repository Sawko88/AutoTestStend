package sourse;

import java.io.Serializable;

public class Bagagnik implements Serializable {
    public String name = "";
    public int index = 0;
    public String text = "";
    public boolean state = false;

    public Bagagnik(int i, String name) {
        this.index = i ;
        this.name = name;
    }

    public Bagagnik() {

    }
}
