package sourse;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SmsController extends Application implements Initializable {
    public Button btSend;

    public TextField tfSend;
    public Button btClear;
    public TextFlow tfMess;
    public TextField tfMesssss;
    public WebView wvMess;
    ControllerTest controllerTest;

    private final static Logger LOGGER =  Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private boolean connected;
    private volatile boolean isAutoConnected;

    private static final int DEFAULT_RETRY_INTERVAL = 2000; // in milliseconds



    public enum ConnectionDisplayState {

        DISCONNECTED, ATTEMPTING, CONNECTED, AUTOCONNECTED, AUTOATTEMPTING
    }

    public void DisconnectSMS() {
        if(isConnected()) {
            isAutoConnected = false;
            socket.shutdown();
        }
    }


    private FxSocketClient socket;

    public void SetControllerTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }

    public void BtSendAction(ActionEvent event) {
        if (!tfSend.getText().equals("")) {
            socket.sendMessage(tfSend.getText());
            PrintText(tfSend.getText(), Color.BLUE);
        }

    }

    private void PrintText(String text, Color color) {
        String s;
        Format format;
        Date date = new Date();
        format = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        s = format.format(date);

        Text t = new Text();
        t.setText(s+"-"+text+"\r\n");
        t.setFill(color);
        t.setFocusTraversable(true);
        tfMess.getChildren().add(t);
        

    }

    public void BtClearAction(ActionEvent event) {
        //tfMess.re
        tfMess.getChildren().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setIsConnected(false);
        isAutoConnected = false;
        displayState(ConnectionDisplayState.DISCONNECTED);
        Runtime.getRuntime().addShutdownHook(new ShutDownThread());


        System.out.println("Sms - run");
        //taMess.appendText("Begin\r\n");
        PrintText("Begin", Color.GREEN);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println("Sms - run");
    }

    /*
     * Synchronized method set up to wait until there is no socket connection.
     * When notifyDisconnected() is called, waiting will cease.
     */
    private synchronized void waitForDisconnect() {
        while (connected) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    /*
     * Synchronized method responsible for notifying waitForDisconnect()
     * method that it's OK to stop waiting.
     */
    private synchronized void notifyDisconnected() {
        connected = false;
        notifyAll();
    }

    /*
     * Synchronized method to set isConnected boolean
     */
    private synchronized void setIsConnected(boolean connected) {
        this.connected = connected;
    }

    /*
     * Synchronized method to check for value of connected boolean
     */
    private synchronized boolean isConnected() {
        return (connected);
    }

    private void connect() {
        socket = new FxSocketClient(new FxSocketListener(),
                "localhost",
                2015,
                Constants.instance().DEBUG_NONE);
        socket.connect();
    }

    private void autoConnect() {
        new Thread() {
            @Override
            public void run() {
                while (isAutoConnected) {
                    if (!isConnected()) {
                        socket = new FxSocketClient(new FxSocketListener(),
                                "localhost",
                                2015,
                                Constants.instance().DEBUG_NONE);
                        socket.connect();
                    }
                    waitForDisconnect();
                    try {
                        Thread.sleep(2 * 1000);//таймаут переподключения
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }.start();
    }

    private void displayState(ConnectionDisplayState state) {
        switch (state) {
            case DISCONNECTED:
                System.out.println("socket-DISCONNECTED");
                break;
            case ATTEMPTING:
                System.out.println("socket-ATTEMPTING");
            case AUTOATTEMPTING:
                System.out.println("socket-AUTOATTEMPTING");
                break;
            case CONNECTED:
                System.out.println("socket-CONNECTED");
                break;
            case AUTOCONNECTED:
                System.out.println("socket-AUTOCONNECTED");
                break;
        }
    }


    class ShutDownThread extends Thread {

        @Override
        public void run() {
            if (socket != null) {
                if (socket.debugFlagIsSet(Constants.instance().DEBUG_STATUS)) {
                    LOGGER.info("ShutdownHook: Shutting down Server Socket");
                }
                socket.shutdown();
            }
        }
    }

    class FxSocketListener implements SocketListener {

        @Override
        public void onMessage(String line) {
            if (line != null && !line.equals("")) {
                //rcvdMsgsData.add(line);
                PrintText(line, Color.RED);
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
            if (isClosed) {
                notifyDisconnected();
                if (isAutoConnected) {
                    displayState(ConnectionDisplayState.AUTOATTEMPTING);
                } else {
                    displayState(ConnectionDisplayState.DISCONNECTED);
                }
            } else {
                setIsConnected(true);
                if (isAutoConnected) {
                    displayState(ConnectionDisplayState.AUTOCONNECTED);
                } else {
                    displayState(ConnectionDisplayState.CONNECTED);
                }
            }
        }
    }

    public void ConnectSMS() {

        isAutoConnected = true;
        if (isConnected()) {
            displayState(ConnectionDisplayState.AUTOCONNECTED);
        } else {
            displayState(ConnectionDisplayState.AUTOATTEMPTING);
            autoConnect();
        }

    }

   /* @FXML
    private void handleSendMessageButton(ActionEvent event) {
        if (!sendTextField.getText().equals("")) {
            socket.sendMessage(sendTextField.getText());
            sentMsgsData.add(sendTextField.getText());
        }
    }*/

    /*@FXML
    private void handleConnectButton(ActionEvent event) {
        displayState(ConnectionDisplayState.ATTEMPTING);
        connect();
    }*/



    /*@FXML
    private void handleAutoConnectCheckBox(ActionEvent event) {
        if (autoConnectCheckBox.isSelected()) {
            isAutoConnected = true;
            if (isConnected()) {
                displayState(ConnectionDisplayState.AUTOCONNECTED);
            } else {
                displayState(ConnectionDisplayState.AUTOATTEMPTING);
                autoConnect();
            }
        } else {
            isAutoConnected = false;
            if (isConnected()) {
                displayState(ConnectionDisplayState.CONNECTED);
            } else {
                displayState(ConnectionDisplayState.DISCONNECTED);
            }
        }
    }*/


}
