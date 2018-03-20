package sourse;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;

public class ParserZumerMetki {

    private int tablePlase = 0;
    private Thread ZumMetkiCheck;
    private boolean zumMetkiCheckState = false;

    public void SetTablePlase(int tablePlase) {
        this.tablePlase = tablePlase;
    }


    public ArrayList<String> messZumMetki = new ArrayList<String>();
    public ArrayList<ZumerMetkiTime> zumMetkiList = new ArrayList<ZumerMetkiTime>();
    private Thread ResvMessZumMetki;
    private boolean resvMessZumMetkiState = false;

    public void StartParsing() {
        ResTable.resTableEl.get(tablePlase).errorList.clear();
        messZumMetki.clear();
        zumMetkiList.clear();
        resvMessZumMetkiState = true;
        ResvMessZumMetki = new Thread(new ResvMessZumMetkiThread());
        ResvMessZumMetki.start();

        zumMetkiCheckState =true;
        ZumMetkiCheck = new Thread(new ZumMetkiCheckThread());
        ZumMetkiCheck.start();


    }

    public void StopParsing() {
        resvMessZumMetkiState = false;
        zumMetkiCheckState = false;
        messZumMetki.clear();
        zumMetkiList.clear();
    }

    private class ResvMessZumMetkiThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ResvMessZumMetkiThread - begin");
            while (resvMessZumMetkiState){

                if(!messZumMetki.isEmpty()){
                    String messFromList = messZumMetki.get(0);
                    ZumerMetkiTime zumerMetkiTimeBuf = new ZumerMetkiTime();
                    int ind = messFromList.indexOf("=");
                    if (ind>0) {
                        zumerMetkiTimeBuf.state = messFromList.substring(ind + 1, ind + 2);
                        zumerMetkiTimeBuf.timeMC = System.currentTimeMillis();

                        zumMetkiList.add(zumerMetkiTimeBuf);
                    }
                    messZumMetki.remove(0);


                }


                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            System.out.println("ResvMessZumMetkiThread - finish");

        }
    }

    public ZumerMetki currZum = new ZumerMetki(ZumerMetki.TypeZumMetki.XZ);
    private ZumerMetki newZum = new ZumerMetki(ZumerMetki.TypeZumMetki.XZ);

    List<ZumerMetki.TypeTick> lista = new ArrayList<ZumerMetki.TypeTick>();
    private int countLevel = 0;
    public ZumerMetkiTime currZumTime = new ZumerMetkiTime();

    private class ZumMetkiCheckThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ZumMetkiCheckThread - begin");
            while (zumMetkiCheckState){
                if (!zumMetkiList.isEmpty()){
                    try {


                        countLevel = 0;
                        ZumerMetkiTime zumerMetkiBuf = new ZumerMetkiTime();

                        zumerMetkiBuf = zumMetkiList.get(0);
                        int delta = (int) (zumerMetkiBuf.timeMC - currZumTime.timeMC);
                        if (currZumTime.state.contains("1")) {
                            if (0 < delta && delta < ZumerMetki.timePickBig) {
                                lista.add(ZumerMetki.TypeTick.HB);

                            } else {
                                System.out.println("ErrorLevel ZM 1 = " + delta);
                            }
                        }
                        if (currZumTime.state.contains("0")) {
                            if (0 < delta && delta < ZumerMetki.timePickBig) {
                                lista.add(ZumerMetki.TypeTick.LB);

                            } else {
                                System.out.println("ErrorLevel ZM 0 = " + delta);
                                lista.clear();
                            }
                        }


                        //System.out.println("Indikacia=" + IndikaciaCollection.indikaciaSpisok.get(1).name);
                        //lista.clear();


                        currZumTime = zumerMetkiBuf;
                        //System.out.println(indBuf.state+"==>"+indBuf.timeMC);
                        zumMetkiList.remove(0);
                    } catch (NoSuchElementException g){
                        System.out.println("Error ZumMetkiCheckThread "+ g);
                        zumMetkiList.clear();
                    }

                }

                countLevel++;
                if (countLevel>5){
                    if (currZumTime.state.contains("1")){
                        lista.add(ZumerMetki.TypeTick.LEVEL_H);
                    }
                    if (currZumTime.state.contains("0")){
                        lista.add(ZumerMetki.TypeTick.LEVEL_L);
                    }
                    countLevel = 0;
                }

                if (!lista.isEmpty()) {

                    CheckZumerMetki();

                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ZumMetkiCheckThread - finish");
        }
    }


    private int countIndnew = 0;
    private void CheckZumerMetki() {
        if (lista.contains(ZumerMetki.TypeTick.LEVEL_H) || lista.contains(ZumerMetki.TypeTick.LEVEL_L)){

            for (int i = 0 ; i<ZumerMetkiCollection.zumerMetkis.size(); i++){
                if(lista.equals(ZumerMetkiCollection.zumerMetkis.get(i).arrayTicks)){
                    newZum = ZumerMetkiCollection.zumerMetkis.get(i);
                    if (newZum.typeZumMetki != currZum.typeZumMetki){
                        currZum = newZum;
                        System.out.println("ZumerMetki = " + currZum.name);

                    }

                }
            }
            //TimerLevelStart();
            lista.clear();
        }

        for (int i = 0 ; i<ZumerMetkiCollection.zumerMetkis.size(); i++){
            if(lista.equals(ZumerMetkiCollection.zumerMetkis.get(i).arrayTicks)){
                newZum = ZumerMetkiCollection.zumerMetkis.get(i);
                if (newZum.typeZumMetki != currZum.typeZumMetki){

                    currZum = newZum;
                    System.out.println("ZumerMetki = " + currZum.name);



                }
                lista.clear();


            }
        }

        if (lista.size()>6)
        {
            lista.clear();
        }
    }
}
