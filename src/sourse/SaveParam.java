package sourse;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaveParam implements Serializable{
    public String name="";
    public int pos = 0;
    public Map<Integer, TableTest> personlist = new LinkedHashMap<Integer, TableTest>();
    public Resultat res = new Resultat();
}
