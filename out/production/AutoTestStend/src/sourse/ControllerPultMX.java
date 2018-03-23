package sourse;


import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jssc.*;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import static java.awt.Color.white;
import static java.awt.SystemColor.window;

public class ControllerPultMX implements Initializable {



    public ToggleButton ToogleButComPort;
    public ComboBox ComboBoxPort;

    public TextField TextFieldTerminal;

    public AnchorPane PaneComPort;
    //public TextArea TextAriaTerminal;
    public JFXToggleButton out1;
   // public JFXToggleButton adc1on;
    //public TextField textAdc1;
    public JFXToggleButton out2;
    public JFXToggleButton out3;
    public JFXToggleButton out4;
    public JFXToggleButton out5;
    public JFXToggleButton out6;



    public TextField ADC1text;
   // public JFXToggleButton adc2on;
    public TextField ADC2text;
    //public JFXToggleButton adc3on;
    public TextField ADC3text;
   // public JFXToggleButton adc4on;
    public TextField ADC4text;
   // public JFXToggleButton adc5on;
   // public TextField ADC5text;
   // public JFXToggleButton adc6on;
   // public TextField ADC6text;
    public ComboBox comboxGPSSpeed;
    public ComboBox comboboxGPSTimeout;
    public RadioButton radioGpsTime5zero;
    public ToggleGroup GroupTimeForm;
    public RadioButton radioGpsTimeNull;
    public RadioButton radioGpsValidA;
    public ToggleGroup GroupValidGPS;
    public RadioButton radioGpsValidV;
    public TextField textGpsCoordN;
    public TextField textGpsCoordE;
    public Button butGpsResetCoord;
    public RadioButton radioGpsStrokaGPRMC;
    public ToggleGroup GroupTimeForm1;
    public RadioButton radioGpsStrokaNull;
    public TextField textGpsSpeed;
    public TextField textGpsSpeedYz;
    public TextField textGpsSpeedKm;
    public TextField textGpsAzimut;
    public TextField textGpsAzimetChangeVar;
    public CheckBox checkGpsAzimetChange;
    public TextField textGpsAzimutItigo;
    public Button butGPSSetSpeed;
    public JFXToggleButton toogleGpsOn;
    public ToggleGroup GroupGpsType;
    public TextArea textGpsLog;


    //public static LinkedList<String> toStend = new LinkedList<String>();
    public JFXToggleButton pwrOn;
    public JFXSlider pwrSlider;
    public Button pwrChange;
    public JFXToggleButton pwrOnRes;
    public JFXSlider pwrSliderRes;
    public Button pwrChangeRes;
    public Button inp1;
    public Button inp2;
    public Button inp3;
    public Button inp4;
    public Button inp5;
    public Button inp6;
    public Button inp7;
    public Button inp8;
    public Button inp9;
    public Button inp10;
    public Button inp11;
    public JFXToggleButton met1;
    public JFXToggleButton met2;
    public JFXToggleButton rar1;
    public JFXToggleButton rar2;
    public JFXToggleButton mot1;
    public JFXToggleButton mot2;
    public JFXToggleButton pwr1;
    public JFXToggleButton pwr2;
    public JFXToggleButton ADC1on;
    public JFXToggleButton ADC2on;
    public JFXToggleButton ADC3on;
    public JFXToggleButton ADC4on;
    public JFXToggleButton canZZ;
    public JFXToggleButton canKap;
    public JFXToggleButton canDor;
    public JFXToggleButton canTor;
    public JFXToggleButton canBag;
    //SerialPort serialPort;
    boolean ComPortTread = false;
    //ComPort comPort1= new ComPort();
    GPS gps = new GPS();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Start2");


        comboxGPSSpeed.getSelectionModel().select(1);
        comboboxGPSTimeout.getSelectionModel().select(0);
        PaneComPort.setDisable(true);
        ADC1text.setDisable(true);
        ADC2text.setDisable(true);
        ADC3text.setDisable(true);
        ADC4text.setDisable(true);
        //ADC5text.setDisable(true);
        //ADC6text.setDisable(true);




        GroupTimeForm = new ToggleGroup();
        radioGpsTime5zero.setToggleGroup(GroupTimeForm);
        radioGpsTimeNull.setToggleGroup(GroupTimeForm);
        GroupTimeForm.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(GroupTimeForm.getSelectedToggle() != null){
                    System.out.println(GroupTimeForm.getSelectedToggle().toString());
                }
            }
        });

        GroupValidGPS = new ToggleGroup();
        radioGpsValidA.setToggleGroup(GroupValidGPS);
        radioGpsValidV.setToggleGroup(GroupValidGPS);
        GroupValidGPS.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (GroupValidGPS.getSelectedToggle() == radioGpsValidA){
                    gps.SetValid("A");
                } else {
                    gps.SetValid("V");
                }
            }
        });

        GroupGpsType = new ToggleGroup();
        radioGpsStrokaGPRMC.setToggleGroup(GroupGpsType);
        radioGpsStrokaNull.setToggleGroup(GroupGpsType);
        GroupGpsType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(GroupGpsType.getSelectedToggle() != null){
                    System.out.println(GroupGpsType.getSelectedToggle().toString());
                }
            }
        });

        UpdateGPS();


    }

    //private ParserCanSend parserCanSend = new ParserCanSend();

    private void UpdateGPS() {
        if (GroupTimeForm.getSelectedToggle() == radioGpsTime5zero) {
            gps.SetFormatTime("00000.000");
        } else {
            gps.SetFormatTime("00.0");
        }
        if (GroupValidGPS.getSelectedToggle() == radioGpsValidA){
            gps.SetValid("A");
        } else {
            gps.SetValid("V");
        }

        if (GroupGpsType.getSelectedToggle()==radioGpsStrokaGPRMC){
            gps.SetType(1);
        } else {
            gps.SetType(0);
        }

        gps.SetCoordN(textGpsCoordN.getText());
        gps.SetCoordE(textGpsCoordE.getText());
        gps.SetSpeed(textGpsSpeed.getText());
        gps.SetAzimut(textGpsAzimut.getText());
        gps.SetAzimutChange(checkGpsAzimetChange.isSelected());
        gps.SetAzimutChangeVar(textGpsAzimetChangeVar.getText());


    }
    public void CloseWin(){
        System.out.println("Stopeee");
        updateDataState = false;

        gps.StopGps();
    }

    //@Override


    public boolean updateDataState = false;

    private void UpdateDataTread() {
        Thread UpdateDataT = new Thread(new UpdateDataRun());
        UpdateDataT.start();
        updateDataState = true;
    }



    public void Out1Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "out1=";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    private void SendCom(String mess) {
        controllerTest.comPortController.SendMess(mess);
    }

    public void Adc1onAction(ActionEvent actionEvent) {
        String mess = "adc1:";
        if(ADC1on.isSelected()){
            mess = mess+"1";
            ADC1text.setDisable(false);
        }else {
            mess = mess+"0";
            ADC1text.setDisable(true);
            //ADC1text.clear();
        }
        SendCom(mess);
    }

    public void Out2Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "out2=";
        if (xz.isSelected()){

            mess = mess+"0";
        } else {
            mess = mess+"1";
        }
        SendCom(mess);
    }

    public void Out3Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "out3=";
        if (xz.isSelected()){

            mess = mess+"0";
        } else {
            mess = mess+"1";
        }
        SendCom(mess);
    }

    public void Out4Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "out4=";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);


    }

    public void Out5Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "out5=";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void Out6Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "out6=";
        if (xz.isSelected()){

            mess = mess+"0";
        } else {
            mess = mess+"1";
        }
        SendCom(mess);
    }



    public void Adc2onAction(ActionEvent actionEvent) {
        String mess = "adc2:";
        if(ADC2on.isSelected()){
            mess = mess+"1";
            ADC2text.setDisable(false);
        }else {
            mess = mess+"0";
            ADC2text.setDisable(true);
           // ADC2text.clear();
        }
        SendCom(mess);
    }

    public void Adc3onAction(ActionEvent actionEvent) {
        String mess = "adc3:";
        if(ADC3on.isSelected()){
            mess = mess+"1";
            ADC3text.setDisable(false);
        }else {
            mess = mess+"0";
            ADC3text.setDisable(true);
            //ADC3text.clear();
        }
        SendCom(mess);
    }

    public void Adc4onAction(ActionEvent actionEvent) {
        String mess = "adc4:";
        if(ADC4on.isSelected()){
            mess = mess+"1";
            ADC4text.setDisable(false);
        }else {
            mess = mess+"0";
            ADC4text.setDisable(true);
            //ADC4text.clear();
        }
        SendCom(mess);
    }

    public void Adc5onAction(ActionEvent actionEvent) {

    }

    public void Adc6onAction(ActionEvent actionEvent) {

    }

    public void ActionGpscoordN(ActionEvent actionEvent) {
        gps.SetCoordN(textGpsCoordN.getText());
    }

    public void ActiongpsCoordE(ActionEvent actionEvent) {
        gps.SetCoordE(textGpsCoordE.getText());
    }

    public void ActionGpsResetCoord(ActionEvent actionEvent) {
        gps.SetDefCoord();
        textGpsCoordN.setText(gps.GetCoordN());
        textGpsCoordE.setText(gps.GetCoordE());
    }

    public void ActionGpsSpeed(ActionEvent actionEvent) {
        gps.SetSpeed(textGpsSpeed.getText());
        float speed = Float.parseFloat(textGpsSpeed.getText());
        float speedYz = (float) (speed*1.94);
        float speedKm = (float) (speed*3.6);
        String stSpeedYz = new DecimalFormat("#0.00").format(speedYz).replace(",",".");
        textGpsSpeedYz.setText(stSpeedYz);
        String stSpeedKm = new DecimalFormat("#0.00").format(speedKm).replace(",",".");
        textGpsSpeedKm.setText(stSpeedKm);
    }

    public void ActionGpsAzimut(ActionEvent actionEvent) {
        gps.SetAzimut(textGpsAzimut.getText());
    }

    public void ActionGpsAzimetChange(ActionEvent actionEvent) {
        gps.SetAzimutChange(checkGpsAzimetChange.isSelected());
        gps.SetAzimutChangeVar(textGpsAzimetChangeVar.getText());
    }

    public void ActionGpsAzimetChangeVar(ActionEvent actionEvent) {
        gps.SetAzimutChangeVar(textGpsAzimetChangeVar.getText());
    }

    public void ActionGpsSetSpeed(ActionEvent actionEvent) {
        gps.SetSpeedUart(comboxGPSSpeed.getSelectionModel().getSelectedIndex());
    }

    public void ActionGpsOn(ActionEvent actionEvent) {
        if (toogleGpsOn.isSelected()){
            gps.SetInterval(comboboxGPSTimeout.getSelectionModel().getSelectedIndex());
            gps.StartGps();
        } else {
            gps.StopGps();
        }

    }

    public void pwrOnAction(ActionEvent actionEvent) {

        String mess = "pwr1:";
        if(pwr1.isSelected()){
            mess = mess+"1";

        }else {
            mess = mess+"0";

        }
        SendCom(mess);
    }

    public void pwrChangeAction(ActionEvent actionEvent) {
        int pos = (int) pwrSlider.getValue();
        SendCom("pwr1="+pos);
    }


    private ControllerTest controllerTest;
    public void SetControllerTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;

    }

    public void SetDisable(boolean b) {
        PaneComPort.setDisable(b);
    }

    public void DisconnectPult() {
        SetDisable(true);
        CloseWin();
    }

    public void ConnectionPult() {
        SetDisable(false);
        numberInitMess= 0;
        countInit= 0;
        UpdateDataTread();

    }

    public void pwrOnActionRes(ActionEvent actionEvent) {
        String mess = "pwr2:";
        if(pwr2.isSelected()){
            mess = mess+"1";

        }else {
            mess = mess+"0";

        }
        SendCom(mess);
    }

    public void pwrChangeActionRes(ActionEvent actionEvent) {
        int pos = (int) pwrSliderRes.getValue();
        SendCom("pwr2="+pos);
    }

    private int countInit = 0;
    private int numberInitMess = 0;
    public List<String> arrayInit = Arrays.asList("out1=?","out2=?","out3=?","out4=?","out5=?","out6=?",
            "inp1=?","inp2=?","inp3=?","inp4=?","inp5=?","inp6=?","inp7=?","inp8=?","inp9=?","inp10=?","inp11=?",
            "adc1=?","adc2=?","adc3=?","adc4=?",
            "met1:?", "met2:?",
            "rar1:?", "rar2:?",
            "mot1:?", "mot2:?",
            "pwr1:?", "pwr2:?",
            "adc1:?","adc2:?","adc3:?","adc4:?"
    );


    public  ArrayList<String> pultList = new ArrayList<String>();

    public void Met1Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "met1:";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void Met2Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "met2:";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void Rar1Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "rar1:";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void Rar2Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "rar2:";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void Mot1Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "mot1:";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void Mot2Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        String mess = "mot2:";
        if (xz.isSelected()){

            mess = mess+"1";
        } else {
            mess = mess+"0";
        }
        SendCom(mess);
    }

    public void canZZAction(ActionEvent actionEvent) {
        String mess = "can1>";
        if (canZZ.isSelected()){
            mess =mess+ controllerTest.logickTest.parserCanSend.SendCan(1,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(1).onstait);

        } else {
            mess = mess+ controllerTest.logickTest.parserCanSend.SendCan(1,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(1).offstait);
        }
        SendCom(mess);
    }

    public void canKapAction(ActionEvent actionEvent) {
        String mess = "can1>";
        if (canKap.isSelected()){
            mess =mess+ controllerTest.logickTest.parserCanSend.SendCan(2,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(2).onstait);

        } else {
            mess = mess+ controllerTest.logickTest.parserCanSend.SendCan(2,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(2).offstait);
        }
        SendCom(mess);
    }

    public void canDorAction(ActionEvent actionEvent) {
        String mess = "can1>";
        if (canDor.isSelected()){
            mess =mess+ controllerTest.logickTest.parserCanSend.SendCan(0,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(0).onstait);

        } else {
            mess = mess+ controllerTest.logickTest.parserCanSend.SendCan(0,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(0).offstait);
        }
        SendCom(mess);
    }

    public void canTorAction(ActionEvent actionEvent) {
        String mess = "can1>";
        if (canTor.isSelected()){
            mess =mess+ controllerTest.logickTest.parserCanSend.SendCan(3,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(3).onstait);

        } else {
            mess = mess+ controllerTest.logickTest.parserCanSend.SendCan(3,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(3).offstait);
        }
        SendCom(mess);
    }

    public void canBagAction(ActionEvent actionEvent) {
        String mess = "can1>";
        if (canBag.isSelected()){
            mess =mess+ controllerTest.logickTest.parserCanSend.SendCan(4,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(4).onstait);

        } else {
            mess = mess+ controllerTest.logickTest.parserCanSend.SendCan(4,controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(4).offstait);
        }
        SendCom(mess);
    }


    //private CanCollection canCollection = new CanCollection();
    //private ParserCanSend parserCanSend = new ParserCanSend();

    private class UpdateDataRun implements Runnable {
        @Override
        public void run() {
            System.out.println("UpdateDataRunPult - begin");
            while (updateDataState) {

                    if (!pultList.isEmpty()) {
                        try {
                            if (pultList.get(0) != null) {

                                String mess = pultList.get(0);
                                if (mess != "" && mess != null) {

                                    if (mess.contains("can1>")) {


                                        for (int i = 0; i < controllerTest.logickTest.parserCanSend.canCollection.canSpisok.size(); i++) {
                                            switch (controllerTest.logickTest.parserCanSend.canCollection.canSpisok.get(i).index) {
                                                case 0:
                                                    if (controllerTest.logickTest.parserCanSend.GetCanStetePult(i)) {
                                                        Platform.runLater(() -> {
                                                            canDor.setSelected(true);
                                                        });
                                                    } else {
                                                        Platform.runLater(() -> {
                                                            canDor.setSelected(false);
                                                        });
                                                    }
                                                    break;
                                                case 1:
                                                    if (controllerTest.logickTest.parserCanSend.GetCanStetePult(i)) {
                                                        Platform.runLater(() -> {
                                                            canZZ.setSelected(true);
                                                        });
                                                    } else {
                                                        Platform.runLater(() -> {
                                                            canZZ.setSelected(false);
                                                        });
                                                    }
                                                    break;
                                                case 2:
                                                    if (controllerTest.logickTest.parserCanSend.GetCanStetePult(i)) {
                                                        Platform.runLater(() -> {
                                                            canKap.setSelected(true);
                                                        });
                                                    } else {
                                                        Platform.runLater(() -> {
                                                            canKap.setSelected(false);
                                                        });
                                                    }
                                                    break;

                                                case 3:
                                                    if (controllerTest.logickTest.parserCanSend.GetCanStetePult(i)) {
                                                        Platform.runLater(() -> {
                                                            canTor.setSelected(true);
                                                        });
                                                    } else {
                                                        Platform.runLater(() -> {
                                                            canTor.setSelected(false);
                                                        });
                                                    }
                                                    break;

                                                case 4:
                                                    if (controllerTest.logickTest.parserCanSend.GetCanStetePult(i)) {
                                                        Platform.runLater(() -> {
                                                            canBag.setSelected(true);
                                                        });
                                                    } else {
                                                        Platform.runLater(() -> {
                                                            canBag.setSelected(false);
                                                        });
                                                    }
                                                    break;
                                            }

                                        }

                                    }

                                    if (mess.contains("pwr")) {
                                        int numbt = mess.indexOf(":");
                                        if (numbt > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, numbt);
                                            String pwrState;
                                            pwrState = mess.substring(numbt + 1, numbt + 2);
                                            if (!mess.contains("?")) {
                                                int iii = Integer.parseInt(pwrState);
                                                if (PaneComPort.lookup("#" + nameBt) != null) {
                                                    JFXToggleButton pwrBut = (JFXToggleButton) PaneComPort.lookup("#" + nameBt);

                                                    switch (iii) {
                                                        case 1:

                                                            Platform.runLater(() -> {
                                                                pwrBut.setSelected(true);
                                                            });

                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                pwrBut.setSelected(false);
                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {

                                                            });
                                                            break;
                                                    }

                                                }
                                            }
                                        }
                                    }

                                    if (mess.contains("met")) {
                                        int numbt = mess.indexOf(":");
                                        if (numbt > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, numbt);
                                            String metState;
                                            metState = mess.substring(numbt + 1, numbt + 2);
                                            if (!mess.contains("?")) {
                                                int iii = Integer.parseInt(metState);
                                                if (PaneComPort.lookup("#" + nameBt) != null) {
                                                    JFXToggleButton metBut = (JFXToggleButton) PaneComPort.lookup("#" + nameBt);

                                                    switch (iii) {
                                                        case 1:

                                                            Platform.runLater(() -> {
                                                                metBut.setSelected(true);
                                                            });

                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                metBut.setSelected(false);
                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {

                                                            });
                                                            break;
                                                    }

                                                }
                                            }
                                        }
                                    }

                                    if (mess.contains("rar")) {
                                        int numbt = mess.indexOf(":");
                                        if (numbt > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, numbt);
                                            String rarState;
                                            rarState = mess.substring(numbt + 1, numbt + 2);
                                            if (!mess.contains("?")) {
                                                int iii = Integer.parseInt(rarState);
                                                if (PaneComPort.lookup("#" + nameBt) != null) {
                                                    JFXToggleButton rarBut = (JFXToggleButton) PaneComPort.lookup("#" + nameBt);

                                                    switch (iii) {
                                                        case 1:

                                                            Platform.runLater(() -> {
                                                                rarBut.setSelected(true);
                                                            });

                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                rarBut.setSelected(false);
                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {

                                                            });
                                                            break;
                                                    }

                                                }
                                            }
                                        }
                                    }

                                    if (mess.contains("mot")) {
                                        int numbt = mess.indexOf(":");
                                        if (numbt > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, numbt);
                                            String motState;
                                            motState = mess.substring(numbt + 1, numbt + 2);
                                            if (!mess.contains("?")) {
                                                int iii = Integer.parseInt(motState);
                                                if (PaneComPort.lookup("#" + nameBt) != null) {
                                                    JFXToggleButton motBut = (JFXToggleButton) PaneComPort.lookup("#" + nameBt);

                                                    switch (iii) {
                                                        case 1:

                                                            Platform.runLater(() -> {
                                                                motBut.setSelected(true);
                                                            });

                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                motBut.setSelected(false);
                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {

                                                            });
                                                            break;
                                                    }

                                                }
                                            }
                                        }
                                    }

                                    if (mess.contains("ADC")) {
                                        int i = mess.indexOf("=");
                                        if (i > 0) {
                                            String adcName = mess.substring(0, i);

                                            if (PaneComPort.lookup("#" + adcName + "text") != null) {
                                                TextField adcText;
                                                try {
                                                    adcText = (TextField) PaneComPort.lookup("#" + adcName + "text");
                                                } catch (ClassCastException xz) {
                                                    System.out.println(" Pult - adc !!! " + xz);
                                                    pultList.remove(0);
                                                    continue;
                                                }

                                                float adcNum = (float) 0;
                                                if (mess.contains(",")) {
                                                    int enfFloat = mess.indexOf(",");
                                                    if (enfFloat > 0) {
                                                        try {
                                                            adcNum = Float.parseFloat(mess.substring(i + 1, enfFloat)) / 1000;
                                                        } catch (NumberFormatException xz) {
                                                            System.out.println("NumberFormatException !!!!! " + xz);
                                                            pultList.remove(0);
                                                            continue;
                                                        }

                                                    }
                                                } else {
                                                    int enfFloat = mess.indexOf("\r");
                                                    if (enfFloat > 0) {
                                                        try {
                                                            adcNum = Float.parseFloat(mess.substring(i + 1, enfFloat)) / 1000;
                                                        } catch (NumberFormatException xz) {
                                                            System.out.println("NumberFormatException !!!!! " + xz);
                                                            pultList.remove(0);
                                                            continue;
                                                        }

                                                    }
                                                }
                                                String adcMess = Float.toString(adcNum);

                                                Platform.runLater(() -> {
                                                    adcText.setText(adcMess);
                                                });

                                            }
                                        }
                                        int k = mess.indexOf(":");
                                        if (k > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, k);
                                            String adcState;
                                            adcState = mess.substring(k + 1, k + 2);
                                            if (!mess.contains("?")) {
                                                int iii = Integer.parseInt(adcState);
                                                if (PaneComPort.lookup("#" + nameBt + "on") != null) {
                                                    JFXToggleButton adcBut = (JFXToggleButton) PaneComPort.lookup("#" + nameBt + "on");

                                                    switch (iii) {
                                                        case 1:

                                                            Platform.runLater(() -> {
                                                                adcBut.setSelected(true);
                                                            });

                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                adcBut.setSelected(false);
                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {

                                                            });
                                                            break;
                                                    }

                                                }
                                            }
                                        }


                                        //ADC1text.setText(mess.substring(i + 1) + "V");
                                    }
                                    if (mess.contains("inp")) {

                                        int numbt = mess.indexOf("=");
                                        if (numbt > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, numbt);
                                            String inpState;
                                            inpState = mess.substring(numbt + 1, numbt + 2);
                                            int iii = Integer.parseInt(inpState);
                                            if (PaneComPort.lookup("#" + nameBt) != null) {
                                                Button inpbut = (Button) PaneComPort.lookup("#" + nameBt);
                                                if (inpbut == inp1 || inpbut == inp2 || inpbut == inp3 || inpbut == inp4 || inpbut == inp5 || inpbut == inp6 || inpbut == inp7 || inpbut == inp8) {
                                                    switch (iii) {
                                                        case 1:
                                                            Platform.runLater(() -> {
                                                                inpbut.setStyle("-fx-background-color:red;");
                                                                //inpbut.setText("0");
                                                            });
                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                inpbut.setStyle("-fx-background-color:green;");
                                                                //inpbut.setText("1");

                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {
                                                                inpbut.setStyle("-fx-background-color:grey;");
                                                                // inpbut.setText("-");
                                                            });
                                                            break;
                                                    }
                                                } else {
                                                    switch (iii) {
                                                        case 1:
                                                            Platform.runLater(() -> {
                                                                inpbut.setStyle("-fx-background-color:green;");
                                                                //inpbut.setText("1");
                                                            });
                                                            break;
                                                        case 0:
                                                            Platform.runLater(() -> {
                                                                inpbut.setStyle("-fx-background-color:red;");
                                                                // inpbut.setText("0");
                                                            });
                                                            break;
                                                        default:
                                                            Platform.runLater(() -> {
                                                                inpbut.setStyle("-fx-background-color:grey;");
                                                                // inpbut.setText("-");
                                                            });
                                                            break;
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    if (mess.contains("out")) {
                                        int numbt = mess.indexOf(":");
                                        if (numbt > 0) {
                                            String nameBt;
                                            nameBt = mess.substring(0, numbt);
                                            String outState;
                                            outState = mess.substring(numbt + 1, numbt + 2);
                                            int iii = Integer.parseInt(outState);
                                            if (PaneComPort.lookup("#" + nameBt) != null) {
                                                JFXToggleButton outBut = (JFXToggleButton) PaneComPort.lookup("#" + nameBt);

                                                switch (iii) {
                                                    case 1:
                                                        if (outBut == out2 || outBut == out3 || outBut == out6) {
                                                            Platform.runLater(() -> {
                                                                outBut.setSelected(false);
                                                            });
                                                        } else {
                                                            Platform.runLater(() -> {
                                                                outBut.setSelected(true);
                                                            });
                                                        }
                                                        break;
                                                    case 0:
                                                        if (outBut == out2 || outBut == out3 || outBut == out6) {
                                                            Platform.runLater(() -> {
                                                                outBut.setSelected(true);
                                                            });
                                                        } else {
                                                            Platform.runLater(() -> {
                                                                outBut.setSelected(false);
                                                            });
                                                        }
                                                        break;
                                                    default:
                                                        Platform.runLater(() -> {

                                                        });
                                                        break;
                                                }

                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Error Pult: пустое сообщение");

                                }

                                pultList.remove(0);
                            } else {
                                pultList.remove(0);
                            }
                        } catch (NoSuchElementException e) {
                            System.out.println("Error Pult: " + e);
                            pultList.clear();
                            continue;
                        } catch (NullPointerException xz) {
                            System.out.println("Error Pult: " + xz);
                            System.out.println("pultList.size: " + pultList.size());
                            pultList.remove(0);
                            continue;
                        }


                    }


                        if (!gps.messGPS.isEmpty()) {
                            String gpsMess = gps.messGPS.get(0);
                            SendCom("gps1>" + gpsMess);
                            Platform.runLater(() -> {

                                textGpsLog.appendText(gpsMess + "\r\n");
                                textGpsAzimutItigo.setText(gps.GetAzimutStr());
                            });
                            gps.messGPS.remove(0);

                        }

                        countInit++;
                        if (countInit > 25) {
                            countInit = 0;
                            if (numberInitMess < arrayInit.size()) {
                                SendCom(arrayInit.get(numberInitMess));
                                numberInitMess++;
                            }

                        }



                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("UpdateDataRun - Pult - finish");
        }
    }






}
