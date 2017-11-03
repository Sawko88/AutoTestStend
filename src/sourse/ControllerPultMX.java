package sourse;


import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.Enumeration;
import java.util.HashMap;
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

    //SerialPort serialPort;
    boolean ComPortTread = false;
    ComPort comPort1= new ComPort();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Start2");
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++) {
            System.out.println(portNames[i]);
            ComboBoxPort.getItems().add(i,portNames[i] );
        }
        ComboBoxPort.getSelectionModel().select(0);
        PaneComPort.setDisable(true);
        ADC1text.setDisable(true);
        ADC2text.setDisable(true);
        ADC3text.setDisable(true);
        ADC4text.setDisable(true);
        ADC5text.setDisable(true);
        ADC6text.setDisable(true);
    }



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
                        System.out.println("Stopeee");
                        updateDataState = false;
                        if (comPort1.serialPort.isOpened()){
                        comPort1.Close();
                        }
                    }
                });

            } else {
                PaneComPort.setDisable(true);
                ToogleButComPort.setText("Ошибка");
                comPort1.Close();
            }


        } else {
            System.out.println("DisconectPort");
            ToogleButComPort.setText("Открыть");
            PaneComPort.setDisable(true);
            updateDataState = false;
            ComPortTread = false;
            comPort1.Close();
            TextAriaTerminal.clear();

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
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Adc1onAction(ActionEvent actionEvent) {

        if(adc1on.isSelected()){
            comPort1.Send("adc1:1");
            ADC1text.setDisable(false);
        }else {
            comPort1.Send("adc1:0");
            ADC1text.setDisable(true);
            ADC1text.clear();
        }
    }

    public void Out2Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out3Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out4Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }


    }

    public void Out5Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out6Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out7Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out8Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out9Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out10Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Out11Action(ActionEvent actionEvent) {
        JFXToggleButton xz = (JFXToggleButton) actionEvent.getSource();
        if (xz.isSelected()){
            comPort1.Send(xz.getId()+":1");
            TextAriaTerminal.appendText(xz.getId()+":1\r\n");
        } else {
            comPort1.Send(xz.getId()+":0");
            TextAriaTerminal.appendText(xz.getId()+":0\r\n");
        }
    }

    public void Adc2onAction(ActionEvent actionEvent) {
        if(adc2on.isSelected()){
            comPort1.Send("adc2:1");
            ADC2text.setDisable(false);
        }else {
            comPort1.Send("adc2:0");
            ADC2text.setDisable(true);
            ADC2text.clear();
        }
    }

    public void Adc3onAction(ActionEvent actionEvent) {
        if(adc3on.isSelected()){
            comPort1.Send("adc3:1");
            ADC3text.setDisable(false);
        }else {
            comPort1.Send("adc3:0");
            ADC3text.setDisable(true);
            ADC3text.clear();
        }
    }

    public void Adc4onAction(ActionEvent actionEvent) {
        if(adc4on.isSelected()){
            comPort1.Send("adc4:1");
            ADC4text.setDisable(false);
        }else {
            comPort1.Send("adc4:0");
            ADC4text.setDisable(true);
            ADC4text.clear();
        }
    }

    public void Adc5onAction(ActionEvent actionEvent) {
        if(adc5on.isSelected()){
            comPort1.Send("adc5:1");
            ADC5text.setDisable(false);
        }else {
            comPort1.Send("adc5:0");
            ADC5text.setDisable(true);
            ADC5text.clear();
        }
    }

    public void Adc6onAction(ActionEvent actionEvent) {
        if(adc6on.isSelected()){
            comPort1.Send("adc6:1");
            ADC6text.setDisable(false);
        }else {
            comPort1.Send("adc6:0");
            ADC6text.setDisable(true);
            ADC6text.clear();
        }
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
