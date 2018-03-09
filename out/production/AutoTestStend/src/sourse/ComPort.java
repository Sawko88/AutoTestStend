package sourse;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class ComPort {

    SerialPort serialPort;
    public String comPortString = "";
    public boolean comPortThread = false;

    public void ComPortAddString (String s){
        comPortString = comPortString+s;
        //System.out.println("@"+s);

    }
    boolean Open (String comPortName){
        serialPort = new SerialPort(comPortName);//(String) ComboBoxPort.getSelectionModel().getSelectedItem()
        try {

            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(
                    new ComPort.PortReader(buffer -> Platform.runLater(() -> ComPortAddString(buffer))),
                    SerialPort.MASK_RXCHAR);
            ComPortMessThread();
            return true;
        } catch (SerialPortException e) {
            e.printStackTrace();

            return false;
        }
    }

    private void ComPortMessThread() {
        comPortThread = true;
        Thread ComPortMessParser = new Thread(new ComPort.ComPortMessPArserRun());
        ComPortMessParser.setPriority(Thread.MAX_PRIORITY);
        ComPortMessParser.start();
    }

    public LinkedList<String> messList = new LinkedList<>();

    //String string = "";
    private class ComPortMessPArserRun extends Thread {
        @Override
        public void run() {
            while (comPortThread){

                    if (!comPortString.equals("")) {
                        int i = comPortString.indexOf("\n");
                        if (i>0){
                            while (i>0) {
                                String str = comPortString.substring(0, i+1);
                                //System.out.println(str);
                                messList.add(str);
                                comPortString = comPortString.substring(i+1);
                                i = comPortString.indexOf("\n");

                            }
                        }else {
                            comPortString+="";
                        }
                        //string =  comPort1.comPortString.substring(comPort1.comPortString.indexOf("/n"));


                    }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ComPortMessPArserRun- stop");
        }
    }

    private class PortReader implements SerialPortEventListener {
        private final Consumer<String> textHandler;

        PortReader(Consumer<String> textHandler) {
            this.textHandler = textHandler;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR()) {
                try {

                    String buffer = serialPort.readString();
                    if (buffer!=null){
                        //comPortString = comPortString+buffer;
                        textHandler.accept(buffer);
                        //System.out.println(buffer);
                    }
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    void Close(){
        comPortThread = false;
        try {
            if (serialPort!= null) {
                if (serialPort.isOpened()) {
                    serialPort.closePort();
                }
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

    }

    void Send(String string){
        try {
            //serialPort.writeString( string+"\r\n");//TextFieldTerminal.getText()
            try {
                serialPort.writeString(string+"\r\n", String.valueOf(StandardCharsets.ISO_8859_1));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    String Read(){
        if (comPortString!= ""){
            return comPortString;
        }
        return "";
    }

}
