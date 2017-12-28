package sourse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resultat implements Serializable{
    public boolean stateSignal = false;
    public   List<Signal> signalSResultat = new ArrayList<Signal>();
}
