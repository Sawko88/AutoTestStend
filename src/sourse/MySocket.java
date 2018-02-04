package sourse;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;



/**
 * FXML Controller class
 *
 * @author jtconnor
 */
public class MySocket implements Initializable {



    private final static Logger LOGGER
            = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private String ip = "127.0.0.1";
    private Integer port = 2015;

    private boolean connected;
    private volatile boolean isAutoConnected;

    private static final int DEFAULT_RETRY_INTERVAL = 2000; // in milliseconds

    public void setParam(String ip, Integer port) {
        this.ip = ip;
        this.port = port;

    }

    SmsController smsController;

    public void setParent(SmsController parent) {
        this.smsController = parent;
    }

    public void setAutoCon(boolean autoCon) {
        this.isAutoConnected = autoCon;
    }

    public void SendMEss(String mess) {
        socket.sendMessage(mess);
    }


    public enum ConnectionDisplayState {

        DISCONNECTED, ATTEMPTING, CONNECTED, AUTOCONNECTED, AUTOATTEMPTING
    }

    public FxSocketClient socket;

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
    public synchronized boolean isConnected() {
        return (connected);
    }

    public void connect() {
        socket = new FxSocketClient(new FxSocketListener(),
                ip,
                port,
                Constants.instance().DEBUG_NONE);
        socket.connect();
    }

    public void autoConnect() {
        new Thread() {
            @Override
            public void run() {
                while (isAutoConnected) {
                    if (!isConnected()) {
                        socket = new FxSocketClient(new FxSocketListener(),
                                ip,
                                port,
                                Constants.instance().DEBUG_NONE);
                        socket.connect();
                    }
                    waitForDisconnect();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }.start();
    }

    public ConnectionDisplayState oldState = ConnectionDisplayState.DISCONNECTED;

    private void displayState(ConnectionDisplayState state) {

            switch (state) {
                case DISCONNECTED:
                    System.out.println("socket - DISCONNECTED");
                    smsController.setDisconnect(true);
                    //smsController.statusCon = SmsController.statusConnect.SOCKETINIT;
                    break;
                case ATTEMPTING:
                    System.out.println("socket - ATTEMPTING");
                    break;
                case AUTOATTEMPTING:
                    System.out.println("socket - AUTOATTEMPTING");
                    break;
                case CONNECTED:
                    System.out.println("socket - CONNECTED");
                    break;
                case AUTOCONNECTED:
                    System.out.println("socket - AUTOCONNECTED");
                    break;
            }

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIsConnected(false);
        isAutoConnected = false;
        displayState(ConnectionDisplayState.DISCONNECTED);


        Runtime.getRuntime().addShutdownHook(new ShutDownThread());
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

            /*StringBuilder sb = new StringBuilder();
            for (int i=0; i< size; i++) {
               sb.append(String.format("%02X", line[i]));

             }
             String mmmm = sb.toString();*/

            if (line != null && !line.equals("")) {
                smsController.messResvList.add(line);
                //smsController.PrintText(line, SmsController.MessangeSourse.FROMSMS);
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
            if (isClosed) {
                notifyDisconnected();
                if (isAutoConnected) {
                    displayState(ConnectionDisplayState.DISCONNECTED);
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



    @FXML
    private void handleConnectButton(ActionEvent event) {
        displayState(ConnectionDisplayState.ATTEMPTING);
        connect();
    }

    @FXML
    private void handleDisconnectButton(ActionEvent event) {
        socket.shutdown();
    }

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

    /*@FXML
    private void handleRetryIntervalTextField(ActionEvent event) {
        try {
            Integer.parseInt(retryIntervalTextField.getText());
        } catch (NumberFormatException ex) {
            retryIntervalTextField.setText(
                    Integer.toString(DEFAULT_RETRY_INTERVAL / 1000));
        }
    }*/
}
