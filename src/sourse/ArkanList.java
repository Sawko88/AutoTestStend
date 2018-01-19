package sourse;

public class ArkanList {
    public ARKAN arkan = new ARKAN();
    public boolean confirm = false;
    public int  counter = 0;

    public ArkanList(ARKAN arkan, boolean b, int i) {
        this.arkan = arkan;
        this.confirm = b ;
        this.counter = i;
    }
}
