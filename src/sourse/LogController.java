package sourse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class LogController extends Application implements Initializable {

    public Button btClear;
    public GridPane grPane;
    public InlineCssTextArea ictam;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private String nameModul="Logi";

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
        //PrintText(nameModul+": "+"Begin",  SmsController.MessangeSourse.INF0);

    }
    private ControllerTest controllerTest;
    public void SetControllerTest(ControllerTest controllerTest) {
        this.controllerTest =controllerTest;
    }

    public void BtClearAction(ActionEvent actionEvent) {
        ictam.clear();
    }

    public void StartLog() {
        StartLogThread();
        controllerTest.setConnectState(ControllerTest.ConnectionState.CONNECTCOM);
    }


    private boolean logThreadStage = false;
    private Thread LogUpdate;

    private void StartLogThread() {
        logThreadStage = true;
        LogUpdate = new LogUpdateThread();
        LogUpdate.start();
        CreateFolderLog();
    }

    private String pathLogs = "";
    private File theDirLogs;
    private void CreateFolderLog() {
        //this.getClass().getClassLoader().getResource("").getPath();
        pathLogs = System.getProperty("user.dir")+"\\Logs";

        //logMessList.add(new LogMess(LogMess.LogType.XZ, System.getProperty("user.dir")));
        theDirLogs = new File(pathLogs);
        if (!theDirLogs.exists()){
            theDirLogs.mkdir();
            System.out.println("create dir");
        } else {
            System.out.println("no create dir");
        }

    }

    public void StopLog() {
        logThreadStage = false;
    }

    public static LinkedList<LogMess> logMessList  = new LinkedList<LogMess>();

    private class LogUpdateThread extends Thread implements Runnable{
        @Override
        public void run() {
            System.out.println("LogUpdateThread- start");
            while (logThreadStage){
                if(!logMessList.isEmpty()){
                    LogMess logMessBuf = logMessList.getFirst();
                    Platform.runLater(() -> {
                        PrintText(logMessBuf);
                    });
                    logMessList.removeFirst();
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("LogUpdateThread- finish");
        }
    }


    private int counterText = 0;
    private void PrintText(LogMess logMess) {
        String s;
        Format format;
        Date date = new Date();
        format = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        s = format.format(date);
        String colorText = null;
        switch (logMess.logType){
            /*case INF0: s+= "---"+text+"\r\n"; colorText="-fx-fill: #945b85;"; break;
            case TOSMS: s+= "-->"+text+"\r\n"; colorText="-fx-fill: #3f4fc9;";break;
            case FROMSMS: s+= "<--"+text; colorText="-fx-fill: #dd1b24;";break;
            default: break;*/

            case OK:
                s+= " : "+logMess.mess+"\r\n"; colorText="-fx-fill: #289a52;";
                break;
            case ERROR:
                s+= " : "+logMess.mess+"\r\n"; colorText="-fx-fill: #dd1b24;";
                break;
            case XZ:
                s+= " : "+logMess.mess+"\r\n"; colorText="-fx-fill: #945b85;";
                break;
            case STEPTEST:
                s+= " : "+logMess.mess+"\r\n"; colorText="-fx-fill: #2634a4;";
                break;
            case RESTEST:
                s+= " : "+logMess.mess+"\r\n"; colorText="-fx-fill: #88179a;";
                break;
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

        AddLogToFile(s);


    }

    private File fileLog;
    private void AddLogToFile(String s) {
        SimpleDateFormat dateFile = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calFile = Calendar.getInstance();
        //String pathControl = "ControlLog\\"+dateFile.format(calFile.getTime())+".txt";
        fileLog = new File(pathLogs+"\\"+dateFile.format(calFile.getTime())+".txt");
        try {
            fileLog.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(fileLog, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(s);

            //bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
