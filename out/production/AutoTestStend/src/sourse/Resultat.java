package sourse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resultat implements Serializable{
    public boolean stateSignal = false;
    public   List<Signal> signalSResultat = new ArrayList<Signal>();
    public boolean signalFinish = false;

    public boolean stateIndikacia = false;
    public Indikacia indikaciaResultat = new Indikacia();
    public boolean indikaciaFinish = false;

    public boolean stateReleProv = false;
    public ReleProv releProvResultat = new ReleProv();

    public boolean stateReleBezProv = false;
    public ReleBezProv releBezProvResultat = new ReleBezProv();

    public boolean stateSirena = false;
    public Sirena sirenaResultat = new Sirena();

    public boolean stateBagagnik = false;
    public Bagagnik bagagnikResultat = new Bagagnik();

    public boolean stateWebasto = false;
    public Webasto webastoResultat = new Webasto();

    public boolean stateAutoZap = false;
    public AutoZap autoZapResultat = new AutoZap();

    public boolean stateZumerSniatia = false;
    public ZumerSniatia zumerSniatiaResultat = new ZumerSniatia();

    public boolean stateZumerMetki = false;
    public ZumerMetki zumerMetkiResultat = new ZumerMetki();

    public boolean statePovorot = false;
    public Povorot povorotResultat = new Povorot();
    public String secTime = "00";
    public String minTime = "05";
    public String hourTine= "00";
}
