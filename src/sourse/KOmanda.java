package sourse;

import java.io.Serializable;

public class KOmanda implements Serializable{
    Integer index = 0;
    String name = "";
    String komanda= "";
    public TypeKomand typeKomand = TypeKomand.NONE;

    public KOmanda(int i, TypeKomand typeKomand, String name, String komanda) {
        this.index = i;
        this.name = name;
        this.komanda = komanda;
        this.typeKomand = typeKomand;
    }

    public enum TypeKomand {
        PARAM, COMAND, NONE
    }

    public KOmanda(int i, String name, String s) {
        this.index = i;
        this.name = name;
        this.komanda = s;
    }

    public KOmanda(int index, String komanda) {
        this.index = index;
        this.komanda = komanda;
    }
}
