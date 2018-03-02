package sourse;

import java.util.LinkedList;

public class ResTableElement {
    public ResTableElement(int ind, TypeElementRes type) {
        this.typeElementRes = type;
        this.index = ind;
    }

    public enum TypeElementRes {
        SIGNAL, INDIKACIA, ZVUK, RR_BLOK, NONE
    }
    public TypeElementRes typeElementRes = TypeElementRes.NONE;
    public boolean wait = false;
    public boolean finish = true;
    public Resultat resultat = new Resultat();
    public LinkedList<String> errorList = new LinkedList<String>();
    public int index = 0;
}
