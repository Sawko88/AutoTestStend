package sourse;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsController {
    public Button btnCansel;
    public Button btnOk;
    private ControllerTest controllerTest;

    public void BtnCanselAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void BtnOkAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void SetTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }
}
