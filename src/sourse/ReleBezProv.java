package sourse;

import java.io.Serializable;

public class ReleBezProv implements Serializable {
    public String name = "";
    public int index = 0;
    public String text = "";
    public boolean state = false;
    public static String code = "inp11=";
    public boolean stateblock = false;

    public ReleBezProv(int i, String name, Typebezblock type) {
        this.index = i ;
        this.name = name;
        this.typebezblock = type;
    }

    public ReleBezProv(Typebezblock type) {
        this.typebezblock = type;

    }

    public enum Typebezblock {
        XZ, BLOCK, BEZBLOCK
    }

    public Typebezblock typebezblock = Typebezblock.XZ;

    public ReleBezProv(int i, String name) {
        this.index = i ;
        this.name = name;
    }

    public ReleBezProv() {

    }
}
