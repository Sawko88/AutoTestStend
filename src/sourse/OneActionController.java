package sourse;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OneActionController implements Initializable {
    public PersonTestController personTest;

    public ToolBar tbOne;
    public Button btnDelOne;
    public Button btnOneSet;


    public void SetMainApp(PersonTestController personTestController) {
        this.personTest = personTestController;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initOneAct() {

    }


    public void BtnDelOneAct(ActionEvent actionEvent) {
        ((VBox) tbOne.getParent()).getChildren().remove(tbOne);
    }


    public void BtnOneSetAct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(OneActionController.class.getResource("/fxml/PersonOneAction.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);

        PersonOneActionController personOneActionController = loader.getController();
        personOneActionController.SetOneAction(this);
        //personTestController.SetPerson();

        stage.showAndWait();
    }
}
