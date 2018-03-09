package sourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

public class ParserResIndikacia {

    private int tablePlase = 0;
    public void SetTablePlase(int tablePlase) {
        this.tablePlase = tablePlase;
    }


    public LinkedList<String> messInd = new LinkedList<String>();
    private Thread ResvMessIndikacia;
    private boolean resvMessIndickaciaState = false;
    public Indikacia currIndikacia = new Indikacia(Indikacia.TypeInd.LOADING);
    private Indikacia newIndikacia = new Indikacia(Indikacia.TypeInd.LOADING);

    private Thread IndikaciaCheck;
    private boolean indikaciaCheckState = false;



    public void StartParsing() {
        ResTable.resTableEl.get(tablePlase).errorList.clear();
        messInd.clear();
        indList.clear();
        resvMessIndickaciaState = true;

        ResvMessIndikacia = new Thread(new ResvMessIndikaciaThread());
        ResvMessIndikacia.setPriority(Thread.MAX_PRIORITY);
        ResvMessIndikacia.start();

        indikaciaCheckState =true;
        IndikaciaCheck = new Thread(new IndikaciaCheckThread());
        IndikaciaCheck.start();




    }



    private LinkedList<IndikaciaTime> indList = new LinkedList<IndikaciaTime>();

    public IndikaciaTime currIndTime = new IndikaciaTime();

    public void StopParsing() {
        resvMessIndickaciaState = false;
        indikaciaCheckState = false;
        messInd.clear();
        indList.clear();
    }

    private class ResvMessIndikaciaThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ResvMessIndikaciaThread - begin");
            while (resvMessIndickaciaState){
                if(!messInd.isEmpty()){
                    try {


                        String messFromList = messInd.getFirst();
                        IndikaciaTime indikaciaTimeBuf = new IndikaciaTime();
                        int ind = messFromList.indexOf("=");
                        indikaciaTimeBuf.state = messFromList.substring(ind + 1, ind + 2);
                        indikaciaTimeBuf.timeMC = System.currentTimeMillis();
                        indList.add(indikaciaTimeBuf);
                        messInd.removeFirst();
                    }catch (NoSuchElementException xz){
                        System.out.println("Indikacia error : "+xz);
                        messInd.clear();
                    }
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ResvMessIndikaciaThread - finish");
        }
    }




    List<Indikacia.TypeTick> lista = new ArrayList<Indikacia.TypeTick>();
    private int countLevel = 0;
    private class IndikaciaCheckThread implements Runnable {
        @Override
        public void run() {
            System.out.println("IndikaciaCheckThread - begin");
            while (indikaciaCheckState){
                if (!indList.isEmpty()){
                    try {


                        countLevel = 0;
                        IndikaciaTime indBuf = new IndikaciaTime();

                        indBuf = indList.getFirst();
                        int delta = (int) (indBuf.timeMC - currIndTime.timeMC);
                        if (currIndTime.state.contains("1")) {
                            if (0 < delta && delta < 350) {
                                lista.add(Indikacia.TypeTick.HB);
                            } else if ((currIndikacia.timePausaMin * 0.5) < delta) {
                                lista.add(Indikacia.TypeTick.HP);
                            } else {
                                System.out.println("ErrorLevel= " + delta);
                            }
                        }
                        if (currIndTime.state.contains("0")) {
                            if (0 < delta && delta < 350) {
                                lista.add(Indikacia.TypeTick.LB);
                            } else if ((currIndikacia.timePausaMin * 0.5) < delta) {
                                lista.add(Indikacia.TypeTick.LP);
                            } else {
                                System.out.println("ErrorLevel= " + delta);
                                lista.clear();
                            }
                        }


                        //System.out.println("Indikacia=" + IndikaciaCollection.indikaciaSpisok.get(1).name);
                        //lista.clear();


                        currIndTime = indBuf;
                        //System.out.println(indBuf.state+"==>"+indBuf.timeMC);
                        indList.removeFirst();
                    } catch (NoSuchElementException g){
                        System.out.println("Error IndikaciaCheckThread "+ g);
                        indList.clear();
                    }

                }

                countLevel++;
                if (countLevel>50){
                    if (currIndTime.state.contains("1")){
                        lista.add(Indikacia.TypeTick.LEVEL_H);
                    }
                    if (currIndTime.state.contains("0")){
                        lista.add(Indikacia.TypeTick.LEVEL_L);
                    }
                    countLevel = 0;
                }

                if (!lista.isEmpty()) {

                    CheckInd();

                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("IndikaciaCheckThread - finish");
        }
    }
    private int countIndnew = 0;
    private void CheckInd() {
        if (lista.contains(Indikacia.TypeTick.LEVEL_H) || lista.contains(Indikacia.TypeTick.LEVEL_L)){

            for (int i = 0 ; i<IndikaciaCollection.indikaciaSpisok.size(); i++){
                if(lista.equals(IndikaciaCollection.indikaciaSpisok.get(i).arrayTicks)){
                    newIndikacia = IndikaciaCollection.indikaciaSpisok.get(i);
                    if (newIndikacia.typeInd != currIndikacia.typeInd){


                            currIndikacia = newIndikacia;
                            System.out.println("Indikacia = " + currIndikacia.name);



                    }



                }
            }
            //TimerLevelStart();
            lista.clear();
        }
        for (int i = 0 ; i<IndikaciaCollection.indikaciaSpisok.size(); i++){
            if(lista.equals(IndikaciaCollection.indikaciaSpisok.get(i).arrayTicks)){
                newIndikacia = IndikaciaCollection.indikaciaSpisok.get(i);
                if (newIndikacia.typeInd != currIndikacia.typeInd){
                    countIndnew++;
                    if (countIndnew>2){
                        currIndikacia = newIndikacia;
                        System.out.println("Indikacia = " + currIndikacia.name);
                        countIndnew = 0;
                    }

                }
                lista.clear();


            }
        }

        if (lista.contains(Indikacia.TypeTick.HP) ||lista.contains(Indikacia.TypeTick.LP)){
            lista.clear();
        }


        if (lista.size()>14)
        {
            lista.clear();
        }



    }
}
