package sourse;

import java.util.LinkedList;

public class ParserResBlockBezProv {

    private int tablePlase = 0;
    public void SetTablePlase(int tablePlase) {
        this.tablePlase = tablePlase;
    }


    public LinkedList<String> messRRBlock = new LinkedList<String>();
    private Thread ResvMessRRBlock;
    private boolean resvMessRRBlockState = false;

    public void StartParsing() {
        ResTable.resTableEl.get(tablePlase).errorList.clear();
        messRRBlock.clear();
        resvMessRRBlockState = true;
        ResvMessRRBlock = new Thread(new ResvMessRRBlockThread());
        ResvMessRRBlock.start();

    }

    public void StopParsing() {
        resvMessRRBlockState = false;

    }

    private int counterLevel = 0;
    public ReleBezProv currReleBezProv = new ReleBezProv(ReleBezProv.Typebezblock.XZ);
    public ReleBezProv newReleBezProv = new ReleBezProv(ReleBezProv.Typebezblock.XZ);

    private class ResvMessRRBlockThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ResvMessRRBlockThread - begin");
            while (resvMessRRBlockState){

                if(!messRRBlock.isEmpty()) {
                    String messFromList = messRRBlock.getFirst();
                    //IndikaciaTime indikaciaTimeBuf = new IndikaciaTime();
                    int ind = messFromList.indexOf("=");
                    boolean bufstteblock = false;
                    if (messFromList.substring(ind + 1, ind + 2).contains("1")) {

                        newReleBezProv.typebezblock = ReleBezProv.Typebezblock.BLOCK;
                        counterLevel = 0;

                    } else {

                        newReleBezProv.typebezblock = ReleBezProv.Typebezblock.BEZBLOCK;
                        counterLevel = 0;

                    }
                    messRRBlock.removeFirst();
                }
                counterLevel++;
                if (counterLevel>2){
                    if (newReleBezProv.typebezblock!= currReleBezProv.typebezblock){
                        //currReleBezProv.typebezblock = newReleBezProv.typebezblock;
                        for (int i =0 ; i < ReleBezProvCollection.releBezProvSpisok.size(); i++){
                            if (newReleBezProv.typebezblock == ReleBezProvCollection.releBezProvSpisok.get(i).typebezblock){
                                currReleBezProv = ReleBezProvCollection.releBezProvSpisok.get(i);
                            }
                        }
                        System.out.println("RR_block : " + currReleBezProv.typebezblock);
                    }
                    counterLevel=0;
                }




                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ResvMessRRBlockThread - finish");
        }
    }
}
