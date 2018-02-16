package sourse;

import javafx.scene.control.ToolBar;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;

public class TableTest implements Serializable{
    /*public int index;
    public OneActionController oneActionController;
    public ToolBar toolBar;

    public TableTest(int index,OneActionController oneActionController, ToolBar toolBar) {
        this.index = index;
        this.oneActionController = oneActionController;
        this.toolBar = toolBar;
    }

    public TableTest(int i, OneActionController oneActionController) {

    }*/
    //Nastroika nnn = new Nastroika(0);
    public ActionTest actionTest = new ActionTest(0);
    //Resultat res = new Resultat();



    public TableTest(ActionTest actBuf) {
        this.actionTest = actBuf;
    }

    public TableTest() {

    }
}
