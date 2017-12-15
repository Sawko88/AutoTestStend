package sourse;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;



public class Element implements Initializable {

    public ToolBar tbTest;
    public Label labPos;
    public Label labName;
    public ComboBox cbSviaz;
    public Label lbState;
    public Button btStart;
    public Button btSetting;
    public Button btDel;
    public ControllerTest controllerTest;
    TableElement te;
    public Map<Integer, TableTest> actionList = new LinkedHashMap<Integer, TableTest>();

    public void MouseClickTbTest(MouseEvent mouseEvent) {

        //System.out.println("btn = "+btStart);
    }

    public void ActBtStart(ActionEvent actionEvent) {
        System.out.println("Test"+String.valueOf(te.index));
    }

    public void ActBtSetting(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/PersoneElement.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);

        PersonTestController personTestController = loader.getController();
        personTestController.SetElement(this);
        personTestController.SetPerson();

        stage.showAndWait();
    }

    public void ActBtDel(ActionEvent actionEvent) {
        controllerTest.tableTb.remove(tbTest);
        ((VBox) tbTest.getParent()).getChildren().remove(tbTest);
    }

    public void MouseMovedTbTest(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public void SetMainApp(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }

    public void init() {
        te =  controllerTest.tableTb.get(tbTest);
        labPos.setText(String.valueOf(te.index));
        labName.setText("Test"+String.valueOf(te.index));
    }

    public void SetPosition(int index){
        labPos.setText(String.valueOf(index));
        (controllerTest.tableTb.get(tbTest)).index = index;
    }

    public String getName() {
        return controllerTest.tableTb.get(tbTest).element.labName.getText();
    }


    public void SetName(String text) {
        labName.setText(text);
        //(controllerTest.tableTb.get(tbTest)).element = this;
    }

    public String getPos() {

        return String.valueOf(controllerTest.tableTb.get(tbTest).index);
    }
}
