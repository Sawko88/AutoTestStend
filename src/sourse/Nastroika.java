package sourse;

import java.io.Serializable;

public class Nastroika implements Serializable{
    private static final long serialVersionUID = 1L;
    public int index = 0;
    public String name = "-";
    public int onOff = 0;
    public String nameParam = " ";
    public double metka33=0;
    public double pitanie= 0;
    public double obooti= 0 ;
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

    //@Override
    //public String toString(){
       // return name+"1111"+nameParam;
    //}
}
