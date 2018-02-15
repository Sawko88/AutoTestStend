package sourse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class LogickTest {
    private SmsController smsController;
    private SmsController gprsController;
    private ComPortController comPortController;

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
        START,BEGINTEST, TEST,FINISHTEST, NONE, STOP
    }
    private LogickTestState logickTestState = LogickTestState.NONE;
    private Thread LogickTestThread;
    private boolean logickTestThreadConfirm = false;

    public void StartLogickTest(LinkedList<SaveParam> listLogickTest) {
        this.listLogickTest = listLogickTest;
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
                        if(currentTest.personlist.isEmpty()) {
                            countPersonList = 0;
                            logickTestState = LogickTestState.FINISHTEST;
                        }
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
                    default:break;
                }

                try {
                    Thread.sleep(50);
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
        if (!personlist.isEmpty()){
            tableTest =  personlist.get(countPersonList);

            System.out.println(currentTest.name+" : "+tableTest.actionTest.name+"-->"+tableTest.actionTest.namePosition);
            personlist.remove(countPersonList);
            countPersonList++;
        }

    }
}
