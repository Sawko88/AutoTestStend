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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static java.awt.Color.white;
import static java.awt.SystemColor.window;

public class ControllerPultMX implements Initializable {



    public ToggleButton ToogleButComPort;
    public ComboBox ComboBoxPort;

    public TextField TextFieldTerminal;
    public Button ButtoncomPortSend;
    public AnchorPane PaneComPort;
    public TextArea TextAriaTerminal;
    public JFXToggleButton out1;
    public JFXToggleButton adc1on;
    //public TextField textAdc1;
    public JFXToggleButton out2;
    public JFXToggleButton out3;
    public JFXToggleButton out4;
    public JFXToggleButton out5;
    public JFXToggleButton out6;
    public JFXToggleButton out7;
    public JFXToggleButton out8;
    public JFXToggleButton out9;
    public JFXToggleButton out10;
    public JFXToggleButton out11;
    public Button Inp1;
    public Button Inp2;
    public Button Inp3;
    public Button Inp4;
    public Button Inp5;
    public Button Inp6;
    public Button Inp7;
    public Button Inp8;
    public Button Inp9;
    public Button Inp10;
    public Button Inp11;
    public Button Inp12;
    public Button Inp13;
    public Button Inp14;
    public Button Inp15;
    public Button Inp16;
    public Button Inp17;
    public Button Inp18;
    public TextField ADC1text;
    public JFXToggleButton adc2on;
    public TextField ADC2text;
    public JFXToggleButton adc3on;
    public TextField ADC3text;
    public JFXToggleButton adc4on;
    public TextField ADC4text;
    public JFXToggleButton adc5on;
    public TextField ADC5text;
    public JFXToggleButton adc6on;
    public TextField ADC6text;
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


    public static LinkedList<String> toStend = new LinkedList<String>();
    public JFXToggleButton pwrOn;
    public JFXSlider pwrSlider;
    public Button pwrChange;
    //SerialPort serialPort;
    boolean ComPortTread = false;
    ComPort comPort1= new ComPort();
    GPS gps = new GPS();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Start2");
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++) {
            System.out.println(portNames[i]);
            ComboBoxPort.getItems().add(i,portNames[i] );
        }
        ComboBoxPort.getSelectionModel().select(0);
        comboxGPSSpeed.getSelectionModel().select(1);
        comboboxGPSTimeout.getSelectionModel().select(0);
        PaneComPort.setDisable(true);
        ADC1text.setDisable(true);
        ADC2text.setDisable(true);
        ADC3text.setDisable(true);
        ADC4text.setDisable(true);
        ADC5text.setDisable(true);
        ADC6text.setDisable(true);




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
        if (comPort1.serialPort.isOpened()){
            comPort1.Close();
        }
        gps.StopGps();
    }

    //@Override
    public void ConnectComPort(ActionEvent actionEvent) throws SerialPortException {
        if (ToogleButComPort.isSelected()){
            if (comPort1.Open((String) ComboBoxPort.getSelectionModel().getSelectedItem())) {
                System.out.println("ConnextComPort");
                ToogleButComPort.setText("Закрыть");
                PaneComPort.setDisable(false);
                UpdateDataTread();
                Stage stage = (Stage) ToogleButComPort.getScene().getWindow();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        CloseWin();
                    }
                });

            } else {


                ToogleButComPort.setText("Ошибка");
                comPort1.Close();

                gps.StopGps();
                PaneComPort.setDisable(true);
            }


        } else {
            System.out.println("DisconectPort");
            ToogleButComPort.setText("Открыть");
            PaneComPort.setDisable(true);
            updateDataState = false;
            ComPortTread = false;
            comPort1.Close();
            TextAriaTerminal.clear();
            toogleGpsOn.setSelected(false);
            gps.StopGps();

        }
    }

    public boolean updateDataState = false;

    private void UpdateDataTread() {
        Thread UpdateDataT = new Thread(new ControllerPultMX.UpdateDataRun());
        UpdateDataT.start();
        updateDataState = true;
    }



    public void Out1Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Adc1onAction(ActionEvent actionEvent) {

        if(adc1on.isSelected()){
            toStend.add("adc1:1");
            ADC1text.setDisable(false);
        }else {
            toStend.add("adc1:0");
            ADC1text.setDisable(true);
            ADC1text.clear();
        }
    }

    public void Out2Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out3Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out4Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
           //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
           // TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }


    }

    public void Out5Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out6Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out7Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out8Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out9Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out10Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out11Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            toStend.add(xz.getId()+":1");
            //TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            toStend.add(xz.getId()+":0");
            //TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Adc2onAction(ActionEvent actionEvent) {
        if(adc2on.isSelected()){
            toStend.add("adc2:1");
            ADC2text.setDisable(false);
        }else {
            toStend.add("adc2:0");
            ADC2text.setDisable(true);
            ADC2text.clear();
        }
    }

    public void Adc3onAction(ActionEvent actionEvent) {
        if(adc3on.isSelected()){
            toStend.add("adc3:1");
            ADC3text.setDisable(false);
        }else {
            toStend.add("adc3:0");
            ADC3text.setDisable(true);
            ADC3text.clear();
        }
    }

    public void Adc4onAction(ActionEvent actionEvent) {
        if(adc4on.isSelected()){
            toStend.add("adc4:1");
            ADC4text.setDisable(false);
        }else {
            toStend.add("adc4:0");
            ADC4text.setDisable(true);
            ADC4text.clear();
        }
    }

    public void Adc5onAction(ActionEvent actionEvent) {
        if(adc5on.isSelected()){
            toStend.add("adc5:1");
            ADC5text.setDisable(false);
        }else {
            toStend.add("adc5:0");
            ADC5text.setDisable(true);
            ADC5text.clear();
        }
    }

    public void Adc6onAction(ActionEvent actionEvent) {
        if(adc6on.isSelected()){
            toStend.add("adc6:1");
            ADC6text.setDisable(false);
        }else {
            toStend.add("adc6:0");
            ADC6text.setDisable(true);
            ADC6text.clear();
        }
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
        if(pwrOn.isSelected()){
            toStend.add("pwr1:0");
        } else {
            toStend.add("pwr1:1");
        }
    }

    public void pwrChangeAction(ActionEvent actionEvent) {
        int pos = (int) pwrSlider.getValue();
        toStend.add("pwr1="+pos);
    }

    public void init(ControllerTest controllerTest) {
    }


    private class UpdateDataRun implements Runnable {
        @Override
        public void run() {
            while (updateDataState) {

                    if (comPort1.messList.size() > 0)
                    {
                        String mess = comPort1.messList.poll();
                        if (mess.contains("ADC")) {
                            int i = mess.indexOf("=");
                            if (i>0){
                                String adcName = mess.substring(0, i);

                                if (PaneComPort.lookup("#"+adcName+"text")!=null){
                                    TextField adcText = (TextField) PaneComPort.lookup("#"+adcName+"text");
                                    int enfFloat = mess.indexOf("\r");
                                    float adcNum = Float.parseFloat(mess.substring(i+1,enfFloat))/1000;
                                    String adcMess = Float.toString(adcNum);

                                    Platform.runLater(() -> {
                                        adcText.setText(adcMess+"V");
                                    });

                                }
                            }
                            //ADC1text.setText(mess.substring(i + 1) + "V");
                        }
                        if (mess.contains("Inp")){

                            int numbt = mess.indexOf("=");
                            if (numbt>0) {
                                String nameBt;
                                nameBt = mess.substring(0, numbt);
                                String inpState;
                                inpState = mess.substring(numbt+1, numbt+2);
                                int iii =  Integer.parseInt(inpState)  ;
                                if (PaneComPort.lookup("#" + nameBt) != null) {
                                    Button inpbut = (Button) PaneComPort.lookup("#" + nameBt);
                                    switch (iii){
                                        case 1:
                                            Platform.runLater(() -> {
                                                inpbut.setStyle("-fx-background-color:green;");
                                                inpbut.setText("1");
                                            });
                                            break;
                                        case 0:
                                            Platform.runLater(() -> {
                                                inpbut.setStyle("-fx-background-color:red;");
                                                inpbut.setText("0");
                                            });
                                            break;
                                        default:
                                            Platform.runLater(() -> {
                                                inpbut.setStyle("-fx-background-color:grey;");
                                                inpbut.setText("-");
                                            });
                                            break;
                                    }

                                }
                            }
                        }
                        Platform.runLater(() -> {
                            //System.out.println(comPort1.messList.element());

                            TextAriaTerminal.appendText(mess);
                        });
                    }

                    while (!gps.messGPS.isEmpty())
                    {
                        String gpsMess = gps.messGPS.poll();
                        comPort1.Send("gps="+gpsMess);
                        Platform.runLater(() -> {

                            textGpsLog.appendText(gpsMess+"\r\n");
                            textGpsAzimutItigo.setText(gps.GetAzimutStr());
                        });


                    }
                    while (!toStend.isEmpty()){
                        String txMess = toStend.poll();
                        comPort1.Send(txMess);
                        TextAriaTerminal.appendText(txMess+"\r\n");
                    }
                

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void ActionComSend(ActionEvent actionEvent) throws SerialPortException {
        comPort1.Send(TextFieldTerminal.getText());
    }



}
