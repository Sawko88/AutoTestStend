package sourse;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Element {

    public ToolBar tbTest;
    public Label labPos;
    public Label labName;
    public ComboBox cbSviaz;
    public Label lbState;
    public Button btStart;
    public Button btSetting;
    public Button btDel;


    public void MouseClickTbTest(MouseEvent mouseEvent) {
        System.out.println("btn = "+btStart.getParent());
    }

    public void ActBtStart(ActionEvent actionEvent) {
    }

    public void ActBtSetting(ActionEvent actionEvent) {
    }

    public void ActBtDel(ActionEvent actionEvent) {
        ((VBox) tbTest.getParent()).getChildren().remove(tbTest);
    }

    public void MouseMovedTbTest(MouseEvent mouseEvent) {
    }
}
