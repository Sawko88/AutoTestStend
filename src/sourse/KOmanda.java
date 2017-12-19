package sourse;

public class KOmanda {
    Integer index = 0;
    String name = "";
    String komanda= "";

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
