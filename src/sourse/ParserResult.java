package sourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.LinkedList;

public class ParserResult {
    public Resultat resultat = new Resultat();
    public SmsController smsController;
    public SmsController gprsController;
    public ComPortController comPortController;
    public void SetChannels(ComPortController comPortController, SmsController smsController, SmsController gprsController) {
        this.comPortController = comPortController;
        this.smsController = smsController;
        this.gprsController = gprsController;
    }

    public void StopResult() {
        ReadTest(false);
        parserResGSM.StopParsing();

        //resvMessComState = false;
        resultatThreadState =false;
        ClearLists();
        StopTimer();
    }

    private void StopTimer() {

        System.out.println("timeTest - stop");
        if (timeTest!= null) {
            timeTest.stop();
        }
    }

    private void ClearLists() {
        comPortController.messResvTestList.clear();
        smsController.messResvTestList.clear();
        gprsController.messResvTestList.clear();
    }


    private boolean waitresult = false;
    public void SetWaitResult(boolean b) {
        this.waitresult = b;

    }
    public   LogickTest logickTest;
    public void SetLogickTest(LogickTest logickTest) {
        this.logickTest = logickTest;
    }


    public enum ResStatus {
        NONE , WAIT, OK, ERROR, NULL
    }
    public ResStatus resStatus = ResStatus.NONE;




    public void SetResult(Resultat res) {
        this.resultat = res;
        StartResultatThread();
        parserResGSM.SetRes(this);
        //parserResIndikacia.SetRes(this);
        resStatus = ResStatus.WAIT;
        stateRes = StateRes.INIT;
        waitresult = false;


    }



    private  Thread ResultatThread;
    private boolean resultatThreadState = false;

    private void StartResultatThread() {
        //CheckFinish();
        resultatThreadState = true;
        ResultatThread = new Thread(new ResultatThreadListening());
        ResultatThread.start();
    }



    public ResTable resTable = new ResTable();
    public enum StateRes {
        INIT, INITTABLE, START, NONE, WAITRES, CHECKERROR, STOP
    }
    public StateRes stateRes = StateRes.NONE;

    public LinkedList<String> errorLsst = new LinkedList<String>();

    private ParserResGSM parserResGSM = new ParserResGSM();
    //private ParserResIndikacia parserResIndikacia = new ParserResIndikacia();

    private class ResultatThreadListening implements Runnable {
        @Override
        public void run() {
            System.out.println("ResultatThreadListening - begin");
            while (resultatThreadState){

                switch (stateRes){
                    case INIT:
                        System.out.println("ResultatThreadListening - stateRes - INIT");

                        GetCurrentStates();
                        ClearErrors();
                        stateRes = StateRes.INITTABLE;
                        break;
                    case INITTABLE:
                        System.out.println("ResultatThreadListening - stateRes - INITTABLE");
                        InitTable();
                        parserResGSM.SetTablePlase(0);

                        stateRes = StateRes.START;
                        break;
                    case START:
                        System.out.println("ResultatThreadListening - stateRes - START");
                        ReadTest(true);
                        //StartThreadCom();
                        parserResGSM.StartParsing();

                        StartTimer();
                        stateRes = StateRes.WAITRES;
                        break;
                    case NONE:

                        break;
                    case WAITRES:
                        boolean finish = false;
                        finish = CheckResultat();
                        if (finish && waitresult){
                            stateRes = StateRes.CHECKERROR;
                        }
                        break;


                    case CHECKERROR:
                        System.out.println("ResultatThreadListening - stateRes - CHECKERROR");
                        if (CheckErrror()){
                            resStatus = ResStatus.ERROR;
                        } else {
                            resStatus = ResStatus.OK;
                        }

                        stateRes = StateRes.STOP;
                        break;
                    case STOP:
                        System.out.println("ResultatThreadListening - stateRes - STOP");
                        StopResult();
                        //resStatus= ResStatus.OK;
                        break;
                }

                CheckOndikacia();
                CheckRRBlock();
                CheckZumerMetki();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ResultatThreadListening - finish");
        }
    }

    private boolean errorZumMetki =false;

    private void CheckZumerMetki() {
        if (resTable.resTableEl.get(2).wait){
            ZumerMetki zumerMetkiBud = new ZumerMetki();
            zumerMetkiBud = comPortController.GetCurrentZumerMetki();
            if (resultat.zumerMetkiResultat.typeZumMetki == ZumerMetki.TypeZumMetki.OPR){
                if (zumerMetkiBud.typeZumMetki == resultat.zumerMetkiResultat.typeZumMetki) {
                    resTable.resTableEl.get(2).finish = true;
                    LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, logickTest.currentTest.name+" : Зумер метки получен <-- "+resultat.releBezProvResultat.name ));

                    currZunerMetki = zumerMetkiBud;
                }
            }else {

                    if (zumerMetkiBud.typeZumMetki == resultat.zumerMetkiResultat.typeZumMetki) {
                        resTable.resTableEl.get(2).finish = true;
                        LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, logickTest.currentTest.name+" : Зумер метки получен <-- "+resultat.releBezProvResultat.name ));

                        currZunerMetki = zumerMetkiBud;
                    }
                    if (zumerMetkiBud.typeZumMetki != resultat.zumerMetkiResultat.typeZumMetki) {
                        if (currZunerMetki.typeZumMetki!= zumerMetkiBud.typeZumMetki) {
                            resTable.resTableEl.get(2).finish = false;
                            resTable.resTableEl.get(2).errorList.add("Сработал Зумер Метки ");
                            currZunerMetki = zumerMetkiBud;
                        }
                        //errorZumMetki = true;
                    }


            }

        } else {
            ZumerMetki zumerMetkiBud = new ZumerMetki();
            zumerMetkiBud = comPortController.GetCurrentZumerMetki();
            if (currZunerMetki.typeZumMetki!= zumerMetkiBud.typeZumMetki) {
                if (zumerMetkiBud.typeZumMetki == ZumerMetki.TypeZumMetki.OPR) {
                    resTable.resTableEl.get(2).errorList.add("Сработал Зумер Метки ");
                    currZunerMetki = zumerMetkiBud;
                }
            }

        }

    }

    private void CheckRRBlock() {
        if (resTable.resTableEl.get(3).wait){
            ReleBezProv bufRRblock = new ReleBezProv();
            bufRRblock = comPortController.GetCurrentRRBlock();
            if (bufRRblock.typebezblock!= currRRBlock.typebezblock){
                if (bufRRblock.typebezblock == resultat.releBezProvResultat.typebezblock){
                    resTable.resTableEl.get(3).finish = true;
                    LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, logickTest.currentTest.name+" : блокировки РР получена <-- "+resultat.releBezProvResultat.name ));

                } else {
                    resTable.resTableEl.get(3).errorList.add("Неверное состояние блокировки РР: " + bufRRblock.name);
                }
                currRRBlock = bufRRblock;
            }
            if (currRRBlock.typebezblock == resultat.releBezProvResultat.typebezblock){
                if (!resTable.resTableEl.get(3).finish) {
                    resTable.resTableEl.get(3).finish = true;
                    LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, logickTest.currentTest.name + " : блокировки РР получена <-- " + resultat.releBezProvResultat.name));
                }
            }
        } else {
            ReleBezProv bufRRblock2 = new ReleBezProv();
            bufRRblock2 = comPortController.GetCurrentRRBlock();
            if (bufRRblock2.typebezblock!= currRRBlock.typebezblock){
                resTable.resTableEl.get(3).errorList.add("Неверное состояние блокировки РР: " + bufRRblock2.name);
                currRRBlock = bufRRblock2;
            }

        }
    }

    private void ClearErrors() {
        errorLsst.clear();
        for (int i = 0 ; i< resTable.resTableEl.size(); i++){
            resTable.resTableEl.get(i).errorList.clear();
        }
    }

    private void CheckOndikacia() {
        if (resTable.resTableEl.get(1).wait){
            Indikacia bufInd = new Indikacia();
            bufInd = comPortController.GetCurrentInd();
            if (bufInd.typeInd!= currInd.typeInd){
                if (bufInd.typeInd == resultat.indikaciaResultat.typeInd){
                    resTable.resTableEl.get(1).finish = true;
                    LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, logickTest.currentTest.name+" : Индикация получена <-- "+resultat.indikaciaResultat.name ));
                } else {
                    resTable.resTableEl.get(1).errorList.add("Неправильная индикация: " + bufInd.name);
                }
                currInd = bufInd;
            }
        } else {
            Indikacia bufInd2 = new Indikacia();
            bufInd2 = comPortController.GetCurrentInd();
            if (bufInd2.typeInd!= currInd.typeInd){
                resTable.resTableEl.get(1).errorList.add("Неправильная индикация: " + bufInd2.name);
                currInd = bufInd2;
            }

        }
    }

    private Indikacia currInd = new Indikacia();
    private ReleBezProv currRRBlock = new ReleBezProv();
    private ZumerMetki currZunerMetki = new ZumerMetki();

    private void GetCurrentStates() {
        currInd = comPortController.GetCurrentInd();
        currRRBlock = comPortController.GetCurrentRRBlock();
        currZunerMetki = comPortController.GetCurrentZumerMetki();

    }

    private boolean CheckErrror() {
        for (int i = 0 ; i < resTable.resTableEl.size(); i++){
            if (!resTable.resTableEl.get(i).errorList.isEmpty()){
                for (int l = 0; l<resTable.resTableEl.get(i).errorList.size(); l++){
                    errorLsst.add(resTable.resTableEl.get(i).errorList.getFirst());
                    resTable.resTableEl.get(i).errorList.removeFirst();
                }
            }
        }
        if (errorLsst.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    private void InitTable() {
        resTable.ClearAllFlags();
        if (resultat.stateSignal){
            resTable.SetFlag(0,true, false);
        }
        if (resultat.stateIndikacia){
            resTable.SetFlag(1,true, false);
        }
        if (resultat.stateZumerMetki){
            resTable.SetFlag(2,true, false);
        }
        if (resultat.stateReleBezProv){
            resTable.SetFlag(3,true, false);
        }


    }

    private boolean CheckResultat() {
        boolean f = true;
        for (int i =0; i<resTable.resTableEl.size(); i++){
            f &= resTable.resTableEl.get(i).finish;
        }
        return f;
    }

    //private  Thread ResvMEssFromCom;
    //private boolean resvMessComState = false;

    /*private void StartThreadCom() {
        resvMessComState = true;
        ResvMEssFromCom = new Thread(new ResvMEssFromComThread());
        ResvMEssFromCom.start();
    }*/

    private int currentElementCom = 0;

   /* private class ResvMEssFromComThread implements Runnable {

        @Override
        public void run() {
            System.out.println("ResvMEssFromComThread - begin");
            while (resvMessComState){
                if (!comPortController.messResvTestList.isEmpty()){
                    if (currentElementCom<comPortController.messResvTestList.size()) {
                        String messCOM = comPortController.messResvTestList.get(currentElementCom);
                        if (messCOM.contains(resultat.indikaciaResultat.code)) {
                           parserResIndikacia.messInd.add(messCOM);
                        }
                        currentElementCom++;
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currentElementCom = 0;
            System.out.println("ResvMEssFromComThread - finsh");
        }
    }
*/

    private void ReadTest(boolean b) {
        smsController.SetTesting(b);
        gprsController.SetTesting(b);
        //comPortController.SetTesting(b);
    }


    Timeline timeTest;
    private void StartTimer() {
        //double time = Double.parseDouble(currentstait);
        int timer ;
        timer = Integer.parseInt(resultat.hourTine)*3600 + Integer.parseInt(resultat.minTime)*60 +Integer.parseInt(resultat.secTime)*1;
        if (timer<=0){
            timer = 1;
        }
        timeTest = new Timeline(new KeyFrame(Duration.seconds(timer), ae -> FinishTest()));
        System.out.println("timeTest - start");
        timeTest.play();
    }



    private void FinishTest() {
       // boolean f = true;
        for (int i =0; i<resTable.resTableEl.size(); i++){

            if (resTable.resTableEl.get(i).wait) {
                if (!resTable.resTableEl.get(i).finish) {
                    errorLsst.add("Тест завершон по таймеру " + resTable.resTableEl.get(i).typeElementRes);
                }
            }
        }
        //errorLsst.add("Тест завершон по таймеру");
        resStatus = ResStatus.ERROR;
    }


    public ResStatus GetResultStatus(){
        return  resStatus;
    }






}
