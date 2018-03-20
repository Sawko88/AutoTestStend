package sourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;


import java.util.ArrayList;

import java.util.Map;

public class LogickTest {
    private SmsController smsController;
    private SmsController gprsController;
    private ComPortController comPortController;
    private ParserGsmSend parserGsmSend = new ParserGsmSend();
    public ParserCanSend parserCanSend = new ParserCanSend();
    private ParserResult parserResult = new ParserResult();

    public void SetChannals(SmsController smsController, SmsController gprsController, ComPortController comPortController) {
        this.comPortController = comPortController;
        this.smsController = smsController;
        this.gprsController = gprsController;
        parserResult.SetChannels(comPortController,smsController,gprsController);
        parserResult.SetLogickTest(this);

    }

    private ObgectTest obgectTest;
    public void SetObject(ObgectTest obgectTest) {
        this.obgectTest = obgectTest;
    }


    private ControllerTest controllerTest;
    public void SetTest(ControllerTest controllerTest) {
        this.controllerTest = controllerTest;
    }


    private ArrayList<SaveParam> listLogickTest = new ArrayList<SaveParam>();

    public void StopLogickTest() {
        erroFlag = ErrorFlag.XZ;
        logickTestState = LogickTestState.STOP;
    }

    public void InitThread() {
        logickTestThreadConfirm = true;
        LogickTestThread = new Thread(new LogickTestStateThread());
        LogickTestThread.start();
    }

    public void StopTherad() {
        parserResult.StopResult();
        logickTestThreadConfirm = false;
    }

    public enum LogickTestState {
        START,BEGINTEST, TEST, WAITRESULT, FINISHTEST, NONE, STOP, PAUSASTART, PAUSASTOP, PAUSAWAIT
    }
    private LogickTestState logickTestState = LogickTestState.NONE;
    private Thread LogickTestThread;
    private boolean logickTestThreadConfirm = false;

    public void StartLogickTest(ArrayList<SaveParam> listLogickTest) {
        this.listLogickTest = (ArrayList<SaveParam>) listLogickTest.clone();

        logickTestState = LogickTestState.START;
    }
    public SaveParam currentTest;
    public enum ErrorFlag {
        XZ, OK, ERROR
    }
    public ErrorFlag erroFlag = ErrorFlag.XZ;

    private class LogickTestStateThread implements Runnable {
        @Override
        public void run() {
            System.out.println("LogickTestStateThread - run");


            while (logickTestThreadConfirm){
                switch (logickTestState){

                    case START:
                        erroFlag = ErrorFlag.OK;
                        controllerTest.ControlButtonTest(true);
                        LogController.logMessList.add(new LogMess(LogMess.LogType.XZ, "---------------------------------------------------------------------------------"));
                        LogController.logMessList.add(new LogMess(LogMess.LogType.XZ, "Тестирование " + obgectTest.logNum+ " началось"));
                        System.out.println("LogickTestStateThread - START");
                        parserGsmSend.InitObgect(obgectTest);
                        logickTestState = LogickTestState.BEGINTEST;
                        //controllerTest.errorListShow.clear();
                        Platform.runLater(() -> {
                            controllerTest.ClearColorElement();
                        });
                        break;
                    case BEGINTEST:
                        System.out.println("LogickTestStateThread - BEGINTEST");
                        currentTest = listLogickTest.get(0);
                        LogController.logMessList.add(new LogMess(LogMess.LogType.START, currentTest.name+ " поехали"));
                        Platform.runLater(() -> {
                                    controllerTest.SetElementState(listLogickTest.get(0).pos, "В процессе");
                                });
                        parserResult.SetResult(currentTest.res);

                        System.out.println(currentTest.name+" - begin");
                        logickTestState = LogickTestState.TEST;
                        break;
                    case TEST:
                        System.out.println("LogickTestStateThread - TEST");

                        ParsingTest(currentTest.personlist);

                        break;
                    case WAITRESULT:
                        //System.out.println("LogickTestStateThread - WAITRESULT");
                        parserResult.SetWaitResult(true);
                        switch (parserResult.GetResultStatus()){

                            case NONE:
                                System.out.println("LogickTestStateThread - ResultTest - NONE");
                                logickTestState = LogickTestState.FINISHTEST;
                                break;
                            case WAIT:
                                //System.out.println("LogickTestStateThread - ResultTest - WAIT");
                                break;
                            case OK:
                                System.out.println("LogickTestStateThread - ResultTest - OK");
                                logickTestState = LogickTestState.FINISHTEST;
                                LogController.logMessList.add(new LogMess(LogMess.LogType.OK, currentTest.name+ " закончили без ошибок"));
                                Platform.runLater(() -> {
                                    controllerTest.SetColorElement(listLogickTest.get(0).pos, ControllerTest.ColorTestElement.GREAN);
                                });
                                break;
                            case ERROR:
                                System.out.println("LogickTestStateThread - ResultTest - ERROR");
                                erroFlag = ErrorFlag.ERROR;
                                LogController.logMessList.add(new LogMess(LogMess.LogType.ERROR, currentTest.name+ " закончили со следующими ошибками:"));
                                //controllerTest.errorListShow.addAll(parserResult.errorLsst);
                                for (int err = 0 ; err< parserResult.errorLsst.size(); err++){
                                    System.out.println(parserResult.errorLsst.get(err));
                                    LogController.logMessList.add(new LogMess(LogMess.LogType.ERROR, parserResult.errorLsst.get(err)));
                                    controllerTest.errorListShow.add(currentTest.name+ " : "+parserResult.errorLsst.get(err));
                                }
                                Platform.runLater(() -> {
                                            controllerTest.SetColorElement(listLogickTest.get(0).pos, ControllerTest.ColorTestElement.RED);
                                        });
                                parserResult.errorLsst.clear();
                                logickTestState = LogickTestState.FINISHTEST;
                                break;
                            case NULL:
                                System.out.println("LogickTestStateThread - ResultTest - NULL");
                                logickTestState = LogickTestState.FINISHTEST;
                                break;
                            default: break;
                        }

                        break;
                    case FINISHTEST:
                        System.out.println("LogickTestStateThread - FINISHTEST");
                        System.out.println(currentTest.name+" - finish");

                        parserResult.StopResult();

                        listLogickTest.remove(0);
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
                        LogController.logMessList.add(new LogMess(LogMess.LogType.XZ, "Тестирование " + obgectTest.logNum+ " закончилось"));
                        LogController.logMessList.add(new LogMess(LogMess.LogType.XZ, "---------------------------------------------------------------------------------"));
                        controllerTest.btStopTest.setDisable(true);
                        parserResult.StopResult();
                        //logickTestThreadConfirm = false;
                        if (timeline!=null){
                            timeline.stop();

                        }
                        countPersonList = 0;
                        controllerTest.ControlButtonTest(false);
                        controllerTest.ShowAllert(erroFlag);
                        logickTestState = LogickTestState.NONE;
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

        }
    }

    private int countPersonList = 0;
    private void ParsingTest(Map<Integer, TableTest> personlist) {
        TableTest tableTest = new TableTest();
        if (personlist.isEmpty()){
            countPersonList = 0;
            logickTestState = LogickTestState.WAITRESULT;
        } else
        {
            tableTest =  personlist.get(countPersonList);
            try {
                System.out.println(currentTest.name + " : " + tableTest.actionTest.name + "-->" + tableTest.actionTest.namePosition);
            }catch (NullPointerException w){
                System.out.println(currentTest.name + w);
            }
            LogController.logMessList.add(new LogMess(LogMess.LogType.STEPTEST, currentTest.name+" : "+tableTest.actionTest.name+"-->"+tableTest.actionTest.namePosition));

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
                    System.out.println("PWRnap = "+ par);
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
