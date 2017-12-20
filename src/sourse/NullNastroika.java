package sourse;

public class NullNastroika {
    Nastroika nastroika = new Nastroika(0);

    public NullNastroika(String s) {
        nastroika.nameParam = s;
    }

    public NullNastroika() {

    }

    public Nastroika GetNastroika(Nastroika nastroikaBuf) {
        nastroikaBuf.name = "-";
        nastroikaBuf.nameParam = "";
        return  nastroikaBuf;
    }
}
