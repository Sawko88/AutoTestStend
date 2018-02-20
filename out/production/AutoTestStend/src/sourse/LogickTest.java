package sourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.util.LinkedList;
import java.util.Map;

public class LogickTest {
    private SmsController smsController;
    private SmsController gprsController;
    private ComPortController comPortController;
    private ParserGsmSend parserGsmSend = new ParserGsmSend();
    private ParserCanSend parserCanSend = new ParserCanSend();

    public void SetChannals(SmsController smsController, SmsController gprsController, ComPortController comPortController) {
        this.comPortController = comPortController;
        this.smsController = smsController;
        this.gprsController = gprsController;
    }

    private ObgectTest obgectTest;
    public void SetObject(ObgectTest obgectTest) {
        this.obgectTest = obgectTest;
    }


    private ControllerTest controllerTest;
    public void SetTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }


    private LinkedList<SaveParam> listLogickTest = new LinkedList<SaveParam>();

    public void StopLogickTest() {
        logickTestState = LogickTestState.STOP;
    }

    public enum LogickTestState {
        START,BEGINTEST, TEST,FINISHTEST, NONE, STOP, PAUSASTART, PAUSASTOP, PAUSAWAIT
    }
    private LogickTestState logickTestState = LogickTestState.NONE;
    private Thread LogickTestThread;
    private boolean logickTestThreadConfirm = false;

    public void StartLogickTest(LinkedList<SaveParam> listLogickTest) {
        this.listLogickTest = (LinkedList<SaveParam>) listLogickTest.clone();
        logickTestThreadConfirm = true;
        LogickTestThread = new Thread(new LogickTestStateThread());
        LogickTestThread.start();
        logickTestState = LogickTestState.START;
    }
    private SaveParam currentTest;
    private class LogickTestStateThread implements Runnable {
        @Override
        public void run() {
            System.out.println("LogickTestStateThread - run");
            controllerTest.ControlButtonTest(true);

            while (logickTestThreadConfirm){
                switch (logickTestState){

                    case START:
                        System.out.println("LogickTestStateThread - START");
                        parserGsmSend.InitObgect(obgectTest);
                        logickTestState = LogickTestState.BEGINTEST;
                        break;
                    case BEGINTEST:
                        System.out.println("LogickTestStateThread - BEGINTEST");
                        currentTest = listLogickTest.getFirst();
                        System.out.println(currentTest.name+" - begin");
                        logickTestState = LogickTestState.TEST;
                        break;
                    case TEST:
                        System.out.println("LogickTestStateThread - TEST");
                        ParsingTest(currentTest.personlist);

                        break;
                    case FINISHTEST:
                        System.out.println("LogickTestStateThread - FINISHTEST");
                        System.out.println(currentTest.name+" - finish");
                        listLogickTest.removeFirst();
                        if (listLogickTest.isEmpty()){
                            logickTestState = LogickTestState.STOP;
                        }else {
                            logickTestState = LogickTestState.BEGINTEST;
                        }
                        break;
                    case NONE:
                        break;
                    case STOP:
                        System.out.println("LogickTestStateThread - STOP");
                        controllerTest.btStopTest.setDisable(true);
                        logickTestThreadConfirm = false;
                        break;
                    case PAUSASTART:
                        System.out.println("LogickTestStateThread - PAUSASTART");
                        logickTestState = LogickTestState.PAUSAWAIT;
                        break;
                    case PAUSASTOP:
                        System.out.println("LogickTestStateThread - PAUSASTOP");
                        logickTestState = LogickTestState.TEST;
                        break;
                    case PAUSAWAIT:
                        break;
                    default:break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("LogickTestStateThread - finish");
            controllerTest.ControlButtonTest(false);
        }
    }

    private int countPersonList = 0;
    private void ParsingTest(Map<Integer, TableTest> personlist) {
        TableTest tableTest = new TableTest();
        if (personlist.isEmpty()){
            countPersonList = 0;
            logickTestState = LogickTestState.FINISHTEST;
        } else
        {
            tableTest =  personlist.get(countPersonList);

            System.out.println(currentTest.name+" : "+tableTest.actionTest.name+"-->"+tableTest.actionTest.namePosition);

            switch (tableTest.actionTest.type){

                case ONOFF:
                    String messONOFF = tableTest.actionTest.code+tableTest.actionTest.currentstait;
                    SendToPult(messONOFF);
                    break;
                case PWRONOFF:
                    String messPWRONOFF = tableTest.actionTest.code+tableTest.actionTest.currentstait;
                    SendToPult(messPWRONOFF);
                    break;
                case PWRNAP:
                    String par;
                    par = String.valueOf((int)(70 - (Double.parseDouble(tableTest.actionTest.currentstait)-9)*5));
                    String messPWRNAP = tableTest.actionTest.code+par;
                    SendToPult(messPWRNAP);
                    break;
                case CAN:
                    String messCAN = parserCanSend.GetMess(tableTest.actionTest.can);
                    messCAN = tableTest.actionTest.code+messCAN;
                    SendToPult(messCAN);
                    break;
                case MOTOR:
                    String messMOTOR = tableTest.actionTest.code+tableTest.actionTest.currentstait;
                    SendToPult(messMOTOR);
                    break;
                case RARELE:
                    String messRARELE = tableTest.actionTest.code+tableTest.actionTest.currentstait;
                    SendToPult(messRARELE);
                    break;
                case METKAONOFF:
                    String messMETKAONOFF = tableTest.actionTest.code+tableTest.actionTest.currentstait;
                    SendToPult(messMETKAONOFF);
                    break;
                case NONE:
                    break;
                case GSMCOM:
                    String messGSMCOM = parserGsmSend.GetMess(tableTest.actionTest.kOmanda);
                    SendToGSM(messGSMCOM);
                    break;
                case PAUTHA:
                    StartTimer(tableTest.actionTest.currentstait);
                    break;
                default: break;
            }

            personlist.remove(countPersonList);
            countPersonList++;
        }

    }

    private void SendToGSM(String messGSMCOM) {
        smsController.SendMess(messGSMCOM);
    }

    Timeline timeline;
    private void StartTimer(String currentstait) {
        double time = Double.parseDouble(currentstait);
        timeline = new Timeline(new KeyFrame(Duration.seconds(time), ae -> FinishTimer()));
        logickTestState = LogickTestState.PAUSASTART;
        timeline.play();
    }

    private void FinishTimer() {
        logickTestState = LogickTestState.PAUSASTOP;
    }

    private void SendToPult(String mess) {
        comPortController.SendMess(mess);
    }
}
