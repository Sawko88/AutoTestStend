package sourse;


import java.util.ArrayList;

public class ParserResGSM {

    ParserResult parserResult ;
    public void SetRes(ParserResult parserResult) {
        this.parserResult = parserResult;
    }


    private ParserValid parserValid = new ParserValid();
    public void StartParsing(ParserValid parserValid) {
        this.parserValid = parserValid;
        StartThreadGSM();
    }
    public void StopParsing(){
        resvMessGSMState = false;
    }

    public ArrayList<String> messGsm = new ArrayList<String>();
    private Thread ResvMessFromGSM;
    private boolean resvMessGSMState = false;

    private void StartThreadGSM() {
        for (int i = 0 ; i< parserResult.resultat.signalSResultat.size(); i++){
            parserResult.resultat.signalSResultat.get(i).state = false;
        }
        parserResult.resTable.resTableEl.get(tablePlase).errorList.clear();
        messGsm.clear();
        parserResult.smsController.messResvTestList.clear();
        parserResult.gprsController.messResvTestList.clear();

        resvMessGSMState = true;
        ResvMessFromGSM = new Thread(new ResvMessFromGSMThread());
        ResvMessFromGSM.start();

    }

    private int currentElementSMS = 0;
    private int currentElementGprs = 0;
    private int tablePlase = 0;
    public void SetTablePlase(int tablePlase) {
        this.tablePlase = tablePlase;
    }

    private class ResvMessFromGSMThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ResvMessFromGSMThread - begin");
            while (resvMessGSMState) {
                if (!parserResult.smsController.messResvTestList.isEmpty()) {
                    if (currentElementSMS < parserResult.smsController.messResvTestList.size()) {
                        String messSMS = parserResult.smsController.messResvTestList.get(currentElementSMS);
                        messGsm.add(messSMS);

                        currentElementSMS++;
                    }
                }
                if (!parserResult.gprsController.messResvTestList.isEmpty()) {
                    if (currentElementGprs < parserResult.gprsController.messResvTestList.size()) {
                        String messGPRS = parserResult.gprsController.messResvTestList.get(currentElementGprs);
                        messGsm.add(messGPRS);

                        currentElementGprs++;
                    }
                }
                //проверка игнора
                if (!messGsm.isEmpty()){
                    if (parserResult.resultat.stateSignalIgnor){
                        for(int g = messGsm.size() ; g>0; g--) {
                            int k = g-1;
                            String messres = messGsm.get(k);
                            FindSignalIgnor(messres,k);
                        }
                    }
                }

                //проверка полученных сообщений

                if (!messGsm.isEmpty()){
                    if(parserResult.resTable.resTableEl.get(tablePlase).wait){
                        for(int k = messGsm.size() ; k>0; k--) {
                            int g = k-1;
                            String messres = messGsm.get(g);

                            for (int i = 0; i < parserResult.resultat.signalSResultat.size(); i++) {
                                switch (parserResult.resultat.signalSResultat.get(i).signalType) {

                                    case NONE:
                                        if (messres.equals(parserResult.resultat.signalSResultat.get(i).code)) {
                                            parserResult.resultat.signalSResultat.get(i).state = true;
                                            LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Получено сообщение <-- "+parserResult.resultat.signalSResultat.get(i).name));
                                            messGsm.remove(g);
                                        }
                                        break;
                                    case CONTROL:
                                        if (messres.contains("CODE " + parserResult.resultat.signalSResultat.get(i).code)) {
                                            parserValid.CheckValid(messres);
                                            parserResult.resultat.signalSResultat.get(i).state = true;
                                            LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Получено сообщение <-- "+parserResult.resultat.signalSResultat.get(i).name));
                                            messGsm.remove(g);
                                        }
                                        break;
                                    case PARAM_OB:
                                        if(messres.contains(parserResult.resultat.signalSResultat.get(i).code)){
                                            parserResult.resultat.signalSResultat.get(i).state = true;
                                            LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Получено сообщение <-- "+parserResult.resultat.signalSResultat.get(i).name));
                                            messGsm.remove(g);
                                        }
                                        break;
                                    case PARAM_GSM:
                                        if(messres.contains(parserResult.resultat.signalSResultat.get(i).code)){
                                            parserResult.resultat.signalSResultat.get(i).state = true;
                                            LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Получено сообщение <-- "+parserResult.resultat.signalSResultat.get(i).name));
                                            messGsm.remove(g);
                                        }
                                        break;
                                    case PARAM_CAN:
                                        if(messres.contains(parserResult.resultat.signalSResultat.get(i).code)){
                                            parserResult.resultat.signalSResultat.get(i).state = true;
                                            LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Получено сообщение <-- "+parserResult.resultat.signalSResultat.get(i).name));
                                            messGsm.remove(g);
                                        }
                                        break;
                                    case PARAM_VER:
                                        break;
                                    case TEST0531:
                                        if(parserResult.resultat.signalSResultat.get(i).code.contains(messres)) {
                                            parserResult.resultat.signalSResultat.get(i).state = true;
                                            LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Получено сообщение <-- "+parserResult.resultat.signalSResultat.get(i).name));
                                            messGsm.remove(g);
                                        }
                                        break;
                                }
                            }
                        }
                        boolean finish = true;
                        for (int i = 0 ; i< parserResult.resultat.signalSResultat.size(); i++){
                            finish &= parserResult.resultat.signalSResultat.get(i).state ;
                        }
                        if (finish){
                            parserResult.resTable.resTableEl.get(tablePlase).finish = true;
                        }
                    }

                    if (!messGsm.isEmpty()){

                        for (int err =  messGsm.size(); err>0; err--) {
                            int i = err-1;
                            if (messGsm.get(i).contains("**STATUS**")){
                                messGsm.remove(i);
                            } else {
                                parserResult.resTable.resTableEl.get(tablePlase).errorList.add("Лишнее сообщение: " + messGsm.get(i));
                                messGsm.remove(i);
                            }
                        }
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

    private void FindSignalIgnor(String messres, int g) {

            for (int i = 0; i < parserResult.resultat.signalSResultatIgnor.size(); i++) {
                switch (parserResult.resultat.signalSResultatIgnor.get(i).signalType) {

                    case NONE:
                        if (messres.equals(parserResult.resultat.signalSResultatIgnor.get(i).code)) {
                            messGsm.remove(g);
                        }
                        break;
                    case CONTROL:
                        if (messres.contains("CODE " + parserResult.resultat.signalSResultatIgnor.get(i).code)) {
                            messGsm.remove(g);
                        }
                        break;
                    case PARAM_OB:
                        if(messres.contains(parserResult.resultat.signalSResultatIgnor.get(i).code)){
                            messGsm.remove(g);
                        }
                        break;
                    case PARAM_GSM:
                        if(messres.contains(parserResult.resultat.signalSResultatIgnor.get(i).code)){
                            messGsm.remove(g);
                        }
                        break;
                    case PARAM_CAN:
                        break;
                    case PARAM_VER:
                        break;
                }
            }

    }
}
