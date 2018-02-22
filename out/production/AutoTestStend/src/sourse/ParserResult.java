package sourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ParserResult {
    private Resultat resultat = new Resultat();
    private SmsController smsController;
    private SmsController gprsController;
    private ComPortController comPortController;
    public void SetChannels(ComPortController comPortController, SmsController smsController, SmsController gprsController) {
        this.comPortController = comPortController;
        this.smsController = smsController;
        this.gprsController = gprsController;
    }

    public void StopResult() {
        ReadTest(false);
        resvMessGSMState = false;
        ClearLists();
        StopTimer();
    }

    private void StopTimer() {
        timeTest.stop();
    }

    private void ClearLists() {
        comPortController.messResvTestList.clear();
        smsController.messResvTestList.clear();
        gprsController.messResvTestList.clear();
    }

    public enum ResStatus {
        NONE , WAIT, OK, ERROR, NULL
    }
    public ResStatus resStatus = ResStatus.NONE;


    private Thread ResvMessFromGSM;
    private boolean resvMessGSMState = false;

    public void SetResult(Resultat res) {
        this.resultat = res;
        if (!resultat.stateSignal){
            resStatus = ResStatus.NULL;
        } else {
            StartTimer();
            if (resultat.signalSResultat.isEmpty()){
                resStatus = ResStatus.NULL;
            }else {
                ReadTest(true);

                resvMessGSMState = true;
                ResvMessFromGSM = new Thread(new ResvMessFromGSMThread());
                ResvMessFromGSM.start();

                resStatus = ResStatus.WAIT;
            }

        }


    }

    private void ReadTest(boolean b) {
        smsController.SetTesting(b);
        gprsController.SetTesting(b);
        comPortController.SetTesting(b);
    }


    Timeline timeTest;
    private void StartTimer() {
        //double time = Double.parseDouble(currentstait);
        timeTest = new Timeline(new KeyFrame(Duration.seconds(60), ae -> FinishTest()));

        timeTest.play();
    }



    private void FinishTest() {
        resStatus = ResStatus.ERROR;
    }


    public ResStatus GetResultStatus(){
        return  resStatus;
    }

    private int currentElementSMS = 0;
    private int currentElementGprs = 0;

    private class ResvMessFromGSMThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ResvMessFromGSMThread - begin");
            while (resvMessGSMState){
                if (!smsController.messResvTestList.isEmpty()){
                    if (currentElementSMS<smsController.messResvTestList.size()){
                        String messSMS = smsController.messResvTestList.get(currentElementSMS);
                        for (int i=0; i< resultat.signalSResultat.size(); i++){
                            switch (resultat.signalSResultat.get(i).signalType){

                                case NONE:
                                    if (messSMS.equals(resultat.signalSResultat.get(i).code)){
                                        resStatus = ResStatus.OK;
                                    }
                                    break;
                                case CONTROL:
                                    if (messSMS.contains("CODE "+resultat.signalSResultat.get(i).code)){
                                        resStatus = ResStatus.OK;
                                    }
                                    break;
                                case PARAM_OB:
                                    break;
                                case PARAM_GSM:
                                    break;
                                case PARAM_CAN:
                                    break;
                                case PARAM_VER:
                                    break;
                            }
                        }
                        currentElementSMS++;
                    }
                }
                if (!gprsController.messResvTestList.isEmpty()){
                    if (currentElementGprs<gprsController.messResvTestList.size()){
                        String messSMS = gprsController.messResvTestList.get(currentElementGprs);
                        for (int i=0; i< resultat.signalSResultat.size(); i++){
                            switch (resultat.signalSResultat.get(i).signalType){

                                case NONE:
                                    if (messSMS.equals(resultat.signalSResultat.get(i).code)){
                                        resStatus = ResStatus.OK;
                                    }
                                    break;
                                case CONTROL:
                                    if (messSMS.contains("CODE "+resultat.signalSResultat.get(i).code)){
                                        resStatus = ResStatus.OK;
                                    }
                                    break;
                                case PARAM_OB:
                                    break;
                                case PARAM_GSM:
                                    break;
                                case PARAM_CAN:
                                    break;
                                case PARAM_VER:
                                    break;
                            }
                        }
                        currentElementGprs++;
                    }
                }



                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currentElementSMS = 0;
            currentElementGprs = 0;
            System.out.println("ResvMessFromGSMThread - finish");
        }
    }
}
