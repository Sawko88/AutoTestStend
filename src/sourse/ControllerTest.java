package sourse;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;

public class ControllerTest implements Initializable{
    public JFXToggleButton Pult;
    public Stage stagePult;
    public ControllerPultMX controllerPultMX;

    public void PultAction(ActionEvent actionEvent) throws IOException {
        if (Pult.isSelected()){
            stagePult.show();
        } else {
            //controllerPultMX.CloseWin();
            stagePult.hide();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stagePult = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PultMX.fxml"));
            stagePult.setScene(new Scene(root));
            stagePult.initModality(Modality.WINDOW_MODAL);

            stagePult.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

        //controllerPultMX.init(this);

        Pult.setSelected(true);
    }

}
