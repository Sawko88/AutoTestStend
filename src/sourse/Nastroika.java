package sourse;

public class Nastroika {
    public int index = 0;
    public String name = "-";
    public int onOff = 0;
    public String nameParam = " ";
    public double metka33;
    public double pitanie;
    public double obooti;
    public double pause = 1;
    public Can can = new Can(0, 0);
    public KOmanda komanda = new KOmanda(0,"");

    public Nastroika(int index) {
        //this.pause = 1;
        this.index = index;
    }

    public Nastroika(String nameParam){
        this.nameParam = nameParam;
    }
}
