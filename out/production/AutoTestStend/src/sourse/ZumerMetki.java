package sourse;

import java.io.Serializable;
import java.util.List;

public class ZumerMetki implements Serializable {
    public String name = "";
    public int index = 0;
    public String text = "";
    public boolean state = false;
    public static  String code = "inp8=";

    public <T> ZumerMetki(int i, String name, TypeZumMetki typeZumMetki, List<T> arrayTicks) {
        this.index = i ;
        this.name = name;
        this.typeZumMetki = typeZumMetki;
        this.arrayTicks = (List<Indikacia.TypeTick>) arrayTicks;
    }

    public ZumerMetki(TypeZumMetki typeZumMetki) {
        this.typeZumMetki = typeZumMetki;
    }

    public enum TypeZumMetki {
        XZ, OPR, NEOPR
    }
    public TypeZumMetki typeZumMetki = TypeZumMetki.XZ;

    public enum  TypeTick {
        XZ, LB, HB, LEVEL_H, LEVEL_L
    }
    public static int timePickBig = 300;

    public List<Indikacia.TypeTick> arrayTicks;

    public ZumerMetki(int i, String name) {
        this.index = i ;
        this.name = name;
    }

    public ZumerMetki() {

    }
}
