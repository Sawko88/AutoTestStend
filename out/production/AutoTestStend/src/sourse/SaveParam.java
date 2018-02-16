package sourse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaveParam implements Serializable{
    public String name="";
    public int pos = 0;
    public HashMap<Integer, TableTest> personlist = new HashMap<Integer, TableTest>();
    public Resultat res = new Resultat();
}
