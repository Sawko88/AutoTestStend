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


    private String ip="";
    private Integer port=2015;
    private String password="" ;

    public void setDisconnect(boolean disconnect) {
        this.disconectWasBe = disconnect;
        if (this.disconectWasBe){
            statusCon = statusConnect.INITPROG;
            StopAplication();
        }
    }


    public enum MessangeSourse{
        INF0, FROMSMS, TOSMS
    }

    public MySocket mySocket = new MySocket();

    private Thread autoConnectThread;

    public void ConnectSMS() {
        autoConnectThread = new AutoConnectThread();
        autoConnectThread.start();

        //mySocket.setAutoCon(true);
        //mySocket.autoConnect();
    }

    public enum statusConnect {

        SOCKETINIT, INITPROG, PRIORINIT, INITDEV
    }

    statusConnect statusCon = statusConnect.SOCKETINIT;
    private boolean disconectWasBe = true;

    private class AutoConnectThread extends Thread implements Runnable {
        @Override
        public void run() {
            while (controllerTest.tbConnect.isSelected()){

                switch (statusCon){
                    case SOCKETINIT:

                            mySocket.setParam(ip, port);
                            mySocket.setAutoCon( true);
                            mySocket.autoConnect();
                            statusCon = statusConnect.INITPROG;

                        break;
                    case INITPROG:
                        if (mySocket.isConnected()){
                            if (disconectWasBe){

                                System.out.println("disconectWasBe");
                                disconectWasBe = false;
                                StartApplication();
                                ARKAN arkanInit = new ARKAN();
                                arkanInit.setPassword(password);
                                arkanInit = arkanInit.setARKAN(ARKAN.INITIALIZATION);
                                //if (isConnected()) {
                                messSendList.add(new ArkanList(arkanInit,false, 3));
                                //statusCon=statusConnect.PRIORINIT;
                            }
                        }
                        break;
                    case PRIORINIT:
                        break;
                    case INITDEV:
                        break;
                    default: break;
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void DisconnectSMS() {
    }

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
            ark.setLogNum(controllerTest.tfLogNum.getText());
            ark = ark.setARKAN(ARKAN.SEND_SMS);

            messSendList.add(new ArkanList( ark, false, 3));
            //PrintText(tfSend.getText(), Color.BLUE, MessangeSourse.TOSMS);

        }

    }



    public void PrintText(String text,  MessangeSourse inf0) {
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

        //setIsConnected(false);
        //isAutoConnected = false;
       // displayState(ConnectionDisplayState.DISCONNECTED);

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
        PrintText("Begin",  MessangeSourse.INF0);

        mySocket.setParent(this);



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println("Sms - run");
    }



    private void StopAplication() {
        sendMessTState = false;
        resvMessTState = false;

    }

    private Thread SendMessT;
    private boolean sendMessTState = false;
    public LinkedList<ArkanList> messSendList = new LinkedList<ArkanList>();

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
       // RegistrashionArkan();
    }

    private class ResvMessthread implements Runnable {

        @Override
        public void run() {
            System.out.println("ResvMessSMS- start\r\n");
            while (resvMessTState){
                if (!messResvList.isEmpty()){
                    String mess = messResvList.get(0);

                    ARKAN arkanResv = ARKAN.parserArkan(mess);
                    if (arkanResv.id == ARKAN.ID) {
                        switch (arkanResv.code) {
                            case ARKAN.STATUS:
                                int seach = findMessToConfirm(arkanResv.statuscode, arkanResv.statuscounter);
                                if (seach >= 0) {
                                    if (arkanResv.statusmess == 0) {
                                        messSendList.get(seach).confirm = true;
                                        messResvList.remove(0);
                                    } else {
                                        messResvList.remove(0);
                                        Platform.runLater(() -> {
                                            PrintText("Сообщение не подтверждено",  MessangeSourse.INF0);
                                        });

                                    }
                                } else {
                                    messResvList.remove(0);
                                    Platform.runLater(() -> {
                                        PrintText("Не нашлось подтверждение",  MessangeSourse.INF0);
                                    });
                                }
                                break;
                            case ARKAN.RECEIVED_SMS:
                                Platform.runLater(() -> {
                                    PrintText(arkanResv.data,  MessangeSourse.FROMSMS);
                                });
                                messResvList.remove(0);
                                break;
                            default:
                                Platform.runLater(() -> {
                                    PrintText("Непонятное сообщение",  MessangeSourse.INF0);
                                });
                                messResvList.remove(0);
                                break;

                        }
                    } else {
                        Platform.runLater(() -> {
                            PrintText("Непонятное сообщение "+ arkanResv.data ,  MessangeSourse.INF0);
                        });
                        messResvList.remove(0);
                    }

                    /*Platform.runLater(() -> {
                        PrintText(mess, Color.RED, MessangeSourse.FROMSMS);
                    });*/
                   //messResvList.remove(0);
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int findMessToConfirm(Integer code, Integer counter) {
        int seach = 0;

        for (int i =0 ; i<messSendList.size();i++){
            ArkanList arkanList = messSendList.get(i);
            if (arkanList.arkan.counterCure == counter){
                seach = i;
                break;
            }else {
                seach= -1;
            }
        }
        return seach;
    }

    private class SendMessThread implements Runnable {

        private int counterTimeout = 50;

        @Override
        public void run() {
            System.out.println("SendMessSMS- start\r\n");
            while (sendMessTState){
                if (!messSendList.isEmpty()) {
                    int find = findConfitm();
                    if (find >= 0) {
                        String arkanMessConf = messSendList.get(find).arkan.mess;
                       // if(isConnected()) {
                            mySocket.SendMEss(arkanMessConf);
                        //}
                        messSendList.remove(find);

                    }
                }
                if (!messSendList.isEmpty()) {
                    ArkanList arksend = messSendList.getFirst();
                    //socket.sendMessage(arksend.arkan.mess);
                    //messSendList.removeFirst();

                    if (counterTimeout>=50) {
                        counterTimeout = 0;
                        //mySocket.socket.sendMessage(arksend.arkan.mess);
                        mySocket.SendMEss(arksend.arkan.mess);
                        arksend.counter--;
                    }
                    counterTimeout++;
                    if (arksend.counter<1){
                        Platform.runLater(() -> {
                            PrintText("Error Send Mess "+arksend.arkan.mess,  MessangeSourse.INF0);
                        });
                        messSendList.removeFirst();
                        counterTimeout = 50;
                    }
                    if (arksend.confirm) {
                        if (arksend.arkan.code == ARKAN.SEND_SMS ){
                            Platform.runLater(() -> {
                                PrintText(arksend.arkan.data, MessangeSourse.TOSMS);
                            });
                        }
                        messSendList.removeFirst();
                        counterTimeout = 50;
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


    private void RegistrashionArkan() {
        ARKAN arkanInit = new ARKAN();
        arkanInit.setPassword(password);
        arkanInit = arkanInit.setARKAN(ARKAN.INITIALIZATION);
        //if (isConnected()) {
            messSendList.add(new ArkanList(arkanInit,false, 3));
        //}

        ARKAN arkanPrior = new ARKAN();
        arkanPrior = arkanPrior.setARKAN(ARKAN.PRIORITY);
        messSendList.add(new ArkanList(arkanPrior, false,3));

        ARKAN arkanDev = new ARKAN();
        arkanDev.setLogNum(controllerTest.tfLogNum.getText());
        arkanDev = arkanDev.setARKAN(ARKAN.TRANSFER_NUMDER_FONE);
        messSendList.add(new ArkanList(arkanDev, false,3));
    }



}
