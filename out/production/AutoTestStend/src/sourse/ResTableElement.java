package sourse;

import java.util.LinkedList;

public class ResTableElement {
    public ResTableElement(int ind, TypeElementRes type, String name) {
        this.typeElementRes = type;
        this.index = ind;
        this.name = name;
    }

    public enum TypeElementRes {
        SIGNAL, INDIKACIA, ZVUK, RR_BLOK, NONE, VALID
    }
    public TypeElementRes typeElementRes = TypeElementRes.NONE;
    public boolean wait = false;
    public boolean finish = true;
    public Resultat resultat = new Resultat();
    public LinkedList<String> errorList = new LinkedList<String>();
    public int index = 0;
    public String name ="";
}
