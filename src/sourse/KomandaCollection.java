package sourse;

import java.util.Arrays;
import java.util.List;

public class KomandaCollection {
    public  static List<KOmanda> komndaSpisok = Arrays.asList(
            new KOmanda(0, "Своя команда", ""),
            new KOmanda(1, "Супер Охрана вкл", ""),
            new KOmanda(2, "Супер Охрана выкл", ""),
            new KOmanda(3, "Снятие", ""),
            new KOmanda(4, "Тревога", "")

    );

    public void SetKomand(String text) {
        (komndaSpisok.get(0)).komanda = text;
        System.out.println((komndaSpisok.get(0)).komanda);
    }

    public String GetKonamda(int selectedIndex) {
        return (komndaSpisok.get(selectedIndex)).komanda;
    }
}
