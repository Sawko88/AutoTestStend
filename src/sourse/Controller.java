package sourse;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class Controller implements Initializable {

    public Button CancelBut;
    public Button OkBut;
    public ComboBox ComboboxComplect;

    public void CancelButClick(ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBut.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComboboxComplect.getSelectionModel().select(0);

    }

    public void OkButClick(ActionEvent actionEvent) throws IOException {
        if (ComboboxComplect.getSelectionModel().getSelectedIndex() == 0)
        {
            Stage stage = (Stage) OkBut.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PultMX.fxml"));
            stage = new Stage();
           // stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        }
        if (ComboboxComplect.getSelectionModel().getSelectedIndex() == 1)
        {
            Random random = new Random();
            int res = random.nextInt(5);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            switch (res){
                case 0: alert.setContentText("Упс, как так получилось?"); break;
                case 1: alert.setContentText("Попробуй ещё раз!"); break;
                case 2: alert.setContentText("Жми ещё!"); break;
                case 3: alert.setContentText("В следующий раз точно получится"); break;
                default: alert.setContentText("Попытка не пытка"); break;
            }

            alert.showAndWait();
        }

    }
}
