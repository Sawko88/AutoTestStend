package sourse;

import java.io.Serializable;

public class TableElement implements Serializable{
    public Element element;
    public int index;

    public TableElement(int count, Element element) {
        this.index = count;
        this.element = element;
    }
}
