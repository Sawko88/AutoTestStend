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
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

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

    public void ConnectCom() {
        if (comPort1.Open(nameComPort)) {
            PrintText(nameComPort+": "+"ComPort открыт",  MessangeSourse.INF0);
            controllerTest.setConnectState(ControllerTest.ConnectionState.BDCONNECT);
            UpdateDataTread();
        } else {
            PrintText(nameComPort+": "+"ComPort не удалось открыть",  MessangeSourse.INF0);
        }
    }

    public void DisconnectCOM() {
        updateDataState = false;
        comPort1.Close();
    }

    public enum MessangeSourse{
        INF0, FROMSMS, TOSMS
    }

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
            case FROMSMS: s+= "<--"+text+"\r\n"; colorText="-fx-fill: #dd1b24;";break;
            default: break;

        }


        ictam.appendText(s);
        ictam.setStyle(ictam.getLength()-s.length()+1, ictam.getLength(), colorText);

        ictam.moveTo(ictam.getLength());
        ictam.requestFollowCaret();
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
        UpdateDataT.start();

    }

    private ComPort comPort1= new ComPort();
    private String nameComPort = "";
    public static LinkedList<String> toStend = new LinkedList<String>();

    private class UpdateDataRun extends Thread implements Runnable {
        @Override
        public void run() {
            while (updateDataState) {

                if (comPort1.messList.size() > 0)
                {
                    String mess = comPort1.messList.poll();

                    Platform.runLater(() -> {
                        PrintText(mess,  MessangeSourse.FROMSMS);
                    });
                }


                while (!toStend.isEmpty()){
                    String txMess = toStend.poll();
                    comPort1.Send(txMess);
                    Platform.runLater(() -> {
                        PrintText(txMess,  MessangeSourse.FROMSMS);
                    });
                }


                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(nameModul+": "+"UpdateDataRun- stop");
        }
    }


}
