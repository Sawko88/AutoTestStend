package sourse;

import java.util.Arrays;
import java.util.List;

public class KomandaCollection {
    public  List<KOmanda> komndaSpisok = Arrays.asList(
            new KOmanda(0, KOmanda.TypeKomand.NONE, "Своя команда", ""),
            new KOmanda(1, KOmanda.TypeKomand.PARAM,"Настройки подключения начальные", ":33333*58#79214032773*49#internet *51#gdata*52#gdata*"),
            new KOmanda(2, KOmanda.TypeKomand.COMAND, "Сервис начальный вкл", "EXECUTE 3333 19"),
            new KOmanda(3, KOmanda.TypeKomand.COMAND,"Сервис начальный выкл", "EXECUTE 3333 1F"),
            new KOmanda(4, KOmanda.TypeKomand.PARAM,"Общие настройки начальные", ":33333*37#1*39#xxxx*53#1*"),
            new KOmanda(5, KOmanda.TypeKomand.PARAM,"Настройки CAN", ":ххххх*87#11110111*"),
            new KOmanda(3, KOmanda.TypeKomand.COMAND,"Тестирование вкл", "EXECUTE 3333 20")

    );

    public void SetKomand(String text) {
        (komndaSpisok.get(0)).komanda = text;
        //System.out.println((komndaSpisok.get(0)).komanda);
    }

    public String GetKonamda(int selectedIndex) {
        return (komndaSpisok.get(selectedIndex)).komanda;
    }

    public KOmanda Get(int selectedIndex) {
        return komndaSpisok.get(selectedIndex);
    }

    public String GetName(int selectedIndex) {
        return (komndaSpisok.get(selectedIndex)).name;
    }
}
