package sourse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class ComPortController extends Application implements Initializable {
    public Button btClear;
    public GridPane grPane;
    public InlineCssTextArea ictam;
    public TextField tfSend;
    public Button btSend;

    public void BtClearAction(ActionEvent actionEvent) {
        ictam.clear();
    }

    public void BtSendAction(ActionEvent actionEvent) {
        toStend.add(tfSend.getText());

    }
    private ControllerTest controllerTest;
    public void SetControllerTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        System.out.println(nameModul+": "+" run");
        //taMess.appendText("Begin\r\n");
        PrintText(nameModul+": "+"Begin",  MessangeSourse.INF0);
    }

    public void SetSettings(String comPort) {
        this.nameComPort = comPort;
    }
    private ParserResIndikacia parserResIndikacia = new ParserResIndikacia();
    private ParserResBlockBezProv parserResBlockBezProv = new ParserResBlockBezProv();
    private ParserZumerMetki parserZumerMetki = new ParserZumerMetki();

    public void ConnectCom() {
        if (comPort1.Open(nameComPort)) {
            Platform.runLater(() -> {
                PrintText(nameComPort+": "+"ComPort открыт",  MessangeSourse.INF0);
            });
            controllerTest.setConnectState(ControllerTest.ConnectionState.BDCONNECT);
            UpdateDataTread();
            parserResIndikacia.SetCotrollerTest(controllerTest);
            parserResIndikacia.SetTablePlase(1);
            parserResIndikacia.StartParsing();

            parserZumerMetki.SetTablePlase(2);
            parserZumerMetki.StartParsing();

            parserResBlockBezProv.SetTablePlase(3);
            parserResBlockBezProv.StartParsing();
            //SendMess("inp1=?");
        } else {
            Platform.runLater(() -> {
                PrintText(nameComPort+": "+"ComPort не удалось открыть",  MessangeSourse.INF0);
            });
        }
    }

    public void DisconnectCOM() {
        updateDataState = false;
        parserResIndikacia.StopParsing();
        parserResBlockBezProv.StopParsing();
        parserZumerMetki.StopParsing();
        Platform.runLater(() -> {
            PrintText(nameComPort+": "+"Закрыт",  MessangeSourse.INF0);
        });
        comPort1.Close();
    }

    public void SendMess(String mess) {
        toStend.add(mess);
    }
    private boolean testing = false;
    public void SetTesting(boolean b) {
        this.testing = b;
    }

    public Indikacia GetCurrentInd() {
        return parserResIndikacia.currIndikacia;
    }

    public ReleBezProv GetCurrentRRBlock() {
        return parserResBlockBezProv.currReleBezProv;
    }

    public ZumerMetki GetCurrentZumerMetki() {
        return parserZumerMetki.currZum;
    }

    public enum MessangeSourse{
        INF0, FROMSMS, TOSMS
    }
    private int counterText = 0;
    private void PrintText(String text, MessangeSourse inf0) {
        String s;
        Format format;
        Date date = new Date();
        format = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        s = format.format(date);
        String colorText = null;
        switch (inf0){
            case INF0: s+= "---"+text+"\r\n"; colorText="-fx-fill: #945b85;"; break;
            case TOSMS: s+= "-->"+text+"\r\n"; colorText="-fx-fill: #3f4fc9;";break;
            case FROMSMS: s+= "<--"+text; colorText="-fx-fill: #dd1b24;";break;
            default: break;

        }



        ictam.appendText(s);
        ictam.setStyle(ictam.getLength()-s.length()+1, ictam.getLength(), colorText);

        ictam.moveTo(ictam.getLength());
        ictam.requestFollowCaret();

        counterText++;
        if (counterText>1000){
            counterText = 0;
            ictam.clear();
        }
    }

    private String nameModul="";
    public void SetNameModul(String com) {
        this.nameModul = com;
    }
    Thread UpdateDataT;
    private boolean updateDataState = false;
    private void UpdateDataTread() {
        updateDataState = true;

        UpdateDataT = new UpdateDataRun();
        UpdateDataT.setPriority(Thread.MAX_PRIORITY);
        UpdateDataT.start();

    }

    private ComPort comPort1= new ComPort();
    private String nameComPort = "";
    public ArrayList<String> toStend = new ArrayList<String>();
    public ArrayList<String> messResvTestList = new ArrayList<String>();

    private class UpdateDataRun extends Thread implements Runnable {
        @Override
        public void run() {
            while (updateDataState) {

                if (!comPort1.messList.isEmpty()) {
                    try {
                        if (comPort1.messList.get(0) != null) {

                            String mess = comPort1.messList.get(0);


                            if (mess.equals("") || mess == null) {

                            } else {
                                controllerTest.controllerPultMX.pultList.add(mess);
                                if (mess.contains(Indikacia.code)) {
                                    //parserResIndikacia.messInd.add(mess);
                                    parserResIndikacia.AddInd(mess);
                                }
                                if (mess.contains(ReleBezProv.code)) {
                                    parserResBlockBezProv.messRRBlock.add(mess);
                                }

                                if (mess.contains(ZumerMetki.code)) {

                                    //parserZumerMetki.messZumMetki.add(mess);
                                    parserZumerMetki.AddZum(mess);
                                }

                                Platform.runLater(() -> {
                                    PrintText(mess, MessangeSourse.FROMSMS);
                                });
                            }
                            comPort1.messList.remove(0);

                        } else {
                            comPort1.messList.remove(0);
                        }
                    } catch (NoSuchElementException x) {
                        System.out.println("Error COM- port:" + x);

                        comPort1.messList.clear();
                    } catch (NullPointerException e) {
                        System.out.println("Error COM- port:" + e);
                        System.out.println("comPort1.messList.size:" + comPort1.messList.size());
                        comPort1.messList.remove(0);
                        continue;
                    }


                }


                if (!toStend.isEmpty()){
                    String txMess = toStend.get(0);
                    comPort1.Send(txMess);
                    Platform.runLater(() -> {
                        PrintText(txMess,  MessangeSourse.TOSMS);
                    });
                    if (txMess.contains("can1>")){
                        controllerTest.controllerPultMX.pultList.add(txMess);
                    }
                    toStend.remove(0);
                }


                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(nameModul+": "+"UpdateDataRun- stop");
        }
    }


}
