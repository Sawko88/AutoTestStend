package sourse;

public class NullNastroika {
    ActionTest nastroika = new ActionTest(0);

    public NullNastroika(String s) {
        nastroika.namePosition = s;
    }

    public NullNastroika() {

    }
    private ActionTestCollection actionTestCollection = new ActionTestCollection();
    public ActionTest GetNastroika(ActionTest nastroikaBuf) {
        nastroikaBuf = actionTestCollection.ActionSpisok.get(nastroikaBuf.number);
        nastroikaBuf.name = "-";
        nastroikaBuf.namePosition = "-";
        return  nastroikaBuf;
    }
}
