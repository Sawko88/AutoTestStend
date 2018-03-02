package sourse;

import java.io.Serializable;
import java.util.List;

public class Indikacia implements Serializable{

    public Indikacia(TypeInd typeInd) {
        this.typeInd = typeInd;
    }

    public <T> Indikacia(int i, String name, TypeInd typeInd, List<T> ts) {
        this.index = i ;
        this.name = name;
        this.typeInd = typeInd;
        this.arrayTicks = (List<TypeTick>) ts;
    }

    public enum TypeInd {
        SERVIC, SNIATIE, OHRANA, OHRANA_ZZ, TESTIROVANIE, LOADING, PANIKA, SYPEROHRANA
    }
    public TypeInd typeInd = TypeInd.LOADING;

    public String name = "";
    public int index = 0;
    public String text = "";
    public boolean state = false;
    public static final String code = "inp1=";
    public int timePickSmoll = 160;
    public int timePickBig = 270;
    public int timePausaMin = 1000;

    public enum TypeTick{
        XZ, LS, LB, LP, HS, HB, HP, LEVEL_H, LEVEL_L
    }

    public List<TypeTick> arrayTicks;

    public Indikacia(int i, String name, TypeInd typeInd) {
        this.index = i ;
        this.name = name;
        this.typeInd = typeInd;
    }

    public Indikacia() {

    }
}
