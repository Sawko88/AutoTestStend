package sourse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SmsController extends Application implements Initializable {
    public Button btSend;

    public TextField tfSend;
    public Button btClear;



    public InlineCssTextArea ictam;

    public GridPane grPane;

    ControllerTest controllerTest;

    private final static Logger LOGGER =  Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private boolean connected;
    private volatile boolean isAutoConnected = false;

    private String ip="";
    private Integer port=2015;
    private String password="" ;





    public enum ConnectionDisplayState {

        DISCONNECTED, ATTEMPTING, CONNECTED, AUTOCONNECTED, AUTOATTEMPTING
    }
    private enum MessangeSourse{
        INF0, FROMSMS, TOSMS
    }

    public void DisconnectSMS() {
        if(isConnected()) {
            isAutoConnected = false;
            socket.shutdown();
        }
    }


    private FxSocketClient socket;


    public void SetSettigs(Setting setting) {
        ip = setting.smsIp;
        port = Integer.valueOf(setting.smsPort);
        password = setting.smsPas;
    }

    public void SetControllerTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }

    public void BtSendAction(ActionEvent event) {
        if (!tfSend.getText().equals("")) {
            ARKAN ark = new ARKAN();
            ark.setData(tfSend.getText());
            ark = ark.setARKAN(ARKAN.SEND_SMS);

            messSendList.add(new ArkanList( ark, true, 3));
            //PrintText(tfSend.getText(), Color.BLUE, MessangeSourse.TOSMS);

        }

    }



    private void PrintText(String text, Color color, MessangeSourse inf0) {
        String s;
        Format format;
        Date date = new Date();
        format = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        s = format.format(date);
        String colorText = null;
        switch (inf0){
            case INF0: s+= "---"+text+"\r\n"; colorText="-fx-fill: #945b85;"; break;
            case TOSMS: s+= "-->"+text+"\r\n"; colorText="-fx-fill: #3f4fc9;";break;
            case FROMSMS: s+= "<--"+text+"\r\n"; colorText="-fx-fill: #dd1b24;";break;
            default: break;

        }


        ictam.appendText(s);
        ictam.setStyle(ictam.getLength()-s.length()+1, ictam.getLength(), colorText);

        ictam.moveTo(ictam.getLength());
        ictam.requestFollowCaret();

    }

    public void BtClearAction(ActionEvent event) {
        //tfMess.re
        ictam.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setIsConnected(false);
        isAutoConnected = false;
        displayState(ConnectionDisplayState.DISCONNECTED);
        Runtime.getRuntime().addShutdownHook(new ShutDownThread());
        ictam.setWrapText(true);
        ictam.setEditable(false);
        ictam.setFocusTraversable(false);
        ictam.showParagraphAtBottom(ictam.getParagraphs().size() - 1);
        VirtualizedScrollPane<InlineCssTextArea> vsPane = new VirtualizedScrollPane<>(ictam);
        vsPane.setMaxWidth(Double.MAX_VALUE);
        vsPane.setMaxHeight(Double.MAX_VALUE);
        grPane.setVgrow(vsPane, Priority.ALWAYS);
        grPane.setHgrow(vsPane, Priority.ALWAYS);
        grPane.setMaxWidth(Double.MAX_VALUE);
        grPane.setMaxHeight(Double.MAX_VALUE);
        grPane.add(vsPane, 0, 0);

        System.out.println("Sms - run");
        //taMess.appendText("Begin\r\n");
        PrintText("Begin", Color.GREEN, MessangeSourse.INF0);



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
                                ip,
                                port,
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
                messResvList.add(line);


            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
            if (isClosed) {
                notifyDisconnected();
                if (isAutoConnected) {
                    displayState(ConnectionDisplayState.AUTOATTEMPTING);
                    StopAplication();
                } else {
                    StopAplication();
                    displayState(ConnectionDisplayState.DISCONNECTED);

                }
            } else {
                setIsConnected(true);
                if (isAutoConnected) {
                    displayState(ConnectionDisplayState.AUTOCONNECTED);
                    StartApplication();

                } else {
                    displayState(ConnectionDisplayState.CONNECTED);
                }
            }
        }
    }

    private void StopAplication() {
        sendMessTState = false;
        resvMessTState = false;

    }

    private Thread SendMessT;
    private boolean sendMessTState = false;
    private LinkedList<ArkanList> messSendList = new LinkedList<ArkanList>();

    private Thread ResvMessT;
    private boolean resvMessTState = false;
    public LinkedList<String> messResvList = new LinkedList<String>();

    private void StartApplication() {
        sendMessTState = true;
        SendMessT = new Thread(new SendMessThread());
        SendMessT.start();

        resvMessTState = true;
        ResvMessT = new Thread(new ResvMessthread());
        ResvMessT.start();
        //updateDataState = true;
        RegistrashionArkan();
    }

    private class ResvMessthread implements Runnable {

        @Override
        public void run() {
            System.out.println("ResvMessSMS- start\r\n");
            while (resvMessTState){
                if (!messResvList.isEmpty()){
                    String mess = messResvList.get(0);
                    Platform.runLater(() -> {
                        PrintText(mess, Color.RED, MessangeSourse.FROMSMS);
                    });
                    messResvList.remove(0);
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class SendMessThread implements Runnable {

        private int counterTimeout = 110;

        @Override
        public void run() {
            System.out.println("SendMessSMS- start\r\n");
            while (sendMessTState){
                if (!messSendList.isEmpty()) {
                    int find = findConfitm();
                    if (find >= 0) {
                        String arkanMessConf = messSendList.get(find).arkan.mess;
                        socket.sendMessage(arkanMessConf);
                        messSendList.remove(find);

                    }
                }
                if (!messSendList.isEmpty()) {

                    ArkanList arksend = messSendList.getFirst();
                    if (counterTimeout>100) {
                        counterTimeout = 0;
                        socket.sendMessage(arksend.arkan.mess);
                        arksend.counter--;
                    }
                    counterTimeout++;
                    if (arksend.counter<1){
                        Platform.runLater(() -> {
                            PrintText("Error Send Mess "+arksend.arkan.mess, Color.GREEN, MessangeSourse.INF0);
                        });
                        messSendList.removeFirst();
                        counterTimeout = 110;
                    }
                    if (arksend.confirm) {

                        Platform.runLater(() -> {
                            PrintText(arksend.arkan.mess, Color.BLUE, MessangeSourse.TOSMS);
                        });
                        messSendList.removeFirst();
                        counterTimeout = 11;
                    }
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("SendMessSMS- stop\r\n");

        }





    }
    private int findConfitm() {
        int find = -1;
        if (!messSendList.isEmpty()){
            //System.out.println("size SendList= "+messSendList.size()+"\r\n");
            for (int i=0 ; i< messSendList.size();i++){
                if (messSendList.get(i).arkan.code == ARKAN.STATUS){
                    find = i;
                    break;
                }

            }

        }
        return find;
    }

    public void ConnectSMS() {

        isAutoConnected = true;
        if (isConnected()) {
            //displayState(ConnectionDisplayState.AUTOCONNECTED);
        } else {
            //displayState(ConnectionDisplayState.AUTOATTEMPTING);
            autoConnect();
        }


    }
    public ARKAN arkan = new ARKAN();
    private void RegistrashionArkan() {
        arkan.setPassword(password);
        arkan = arkan.setARKAN(ARKAN.INITIALIZATION);
        if (isConnected()) {
            messSendList.add(new ArkanList(arkan,false, 3));
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
