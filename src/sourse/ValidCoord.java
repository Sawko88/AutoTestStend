package sourse;

import java.io.Serializable;

public class ValidCoord implements Serializable{
    public String name = "";
    public int index = 0;
    public boolean state = false;
    public String code = "";

    public ValidCoord(int index, String name, TypeValid valid, String code) {
        this.index = index;
        this.name = name;
        this.typeValid = valid;
        this.code = code;
    }

    public ValidCoord() {

    }

    public enum TypeValid {
        XZ, VALID, NEVALID, NULL
    }

    public TypeValid typeValid = TypeValid.XZ;
}
