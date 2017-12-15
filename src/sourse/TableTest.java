package sourse;

import javafx.scene.control.ToolBar;

import javax.print.attribute.standard.MediaSize;

public class TableTest {
    public int index;
    public OneActionController oneActionController;
    public ToolBar toolBar;

    public TableTest(int index,OneActionController oneActionController, ToolBar toolBar) {
        this.index = index;
        this.oneActionController = oneActionController;
        this.toolBar = toolBar;
    }

    public TableTest(int i, OneActionController oneActionController) {

    }
}
