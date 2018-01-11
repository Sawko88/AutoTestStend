package sourse;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jssc.SerialPortList;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{
    public Button btnCansel;
    public Button btnOk;
    public ComboBox cboxCom;
    public TextField tfSmsIp;
    public TextField tfSmsPort;
    public TextField tfSmsPas;
    public TextField tfGprsIp;
    public TextField tfGprsPort;
    public TextField tfGprsPas;
    public TextField tfBdIp;
    public TextField tfBdName;
    public TextField tfBdUser;
    public TextField tfBdPas;
    private ControllerTest controllerTest;
    private Setting setting = new Setting();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++) {
            //System.out.println(portNames[i]);
            cboxCom.getItems().add(i,portNames[i] );
        }
        cboxCom.getSelectionModel().select(0);
    }
    public void BtnCanselAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void BtnOkAction(ActionEvent actionEvent) {
        SaveSettings();
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    private void SaveSettings() {
        setting.comPort = (String) cboxCom.getSelectionModel().getSelectedItem();
        setting.smsIp = tfSmsIp.getText();
        setting.smsPort = tfSmsPort.getText();
        setting.smsPas = tfSmsPas.getText();
        setting.gprsIp = tfGprsIp.getText();
        setting.gprsPort = tfGprsPort.getText();
        setting.gprsPas = tfGprsPas.getText();
        setting.bdIp = tfBdIp.getText();
        setting.bdName = tfBdName.getText();
        setting.bdUser = tfBdUser.getText();
        setting.bdPas = tfBdPas.getText();
        controllerTest.SetSettigs(setting);
    }

    public void SetTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }


    public void SetSettings(Setting setting) {
        this.setting = setting;
        tfSmsIp.setText(setting.smsIp);
        tfSmsPort.setText(setting.smsPort);
        tfSmsPas.setText(setting.smsPas);
        tfGprsIp.setText(setting.gprsIp);
        tfGprsPort.setText(setting.gprsPort);
        tfGprsPas.setText(setting.gprsPas);
        tfBdIp.setText(setting.bdIp);
        tfBdName.setText(setting.bdName);
        tfBdUser.setText(setting.bdUser);
        tfBdPas.setText(setting.bdPas);

        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++) {
            //System.out.println(portNames[i]);
            if (setting.comPort.equals(portNames[i])){
                cboxCom.getSelectionModel().select(i);
                break;
            } else {
                cboxCom.getSelectionModel().select(0);
            }
        }
    }
}
