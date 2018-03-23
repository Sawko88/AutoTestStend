package sourse;

import java.util.Arrays;
import java.util.List;

public class KomandaCollection {
    public  List<KOmanda> komndaSpisok = Arrays.asList(
            new KOmanda(0, KOmanda.TypeKomand.NONE, "Своя команда", ""),
            new KOmanda(1, KOmanda.TypeKomand.PARAMNEW_OB,"Общие настройки начальные", ":xxxxx*39#CODE*20#000001*21#10*22#2*23#1*24#0*25#0*26#2*27#0*28#0*29#0*33#1*34#1*36#2*37#1*38#1*43#0*44#0*45#0*46#1*53#1*54#0*55#1*56#0*57#3*"),
            new KOmanda(2, KOmanda.TypeKomand.PARAMNEW_GSM,"Настройки подключения начальные", ":xxxxx*47#84.204.102.210*48#6009*49#APN*51#LOGIN*52#PASSWORD*58#TELEFONE*"),
            new KOmanda(3, KOmanda.TypeKomand.PARAMNEW_CAN,"Настройки CAN начальные", ":ххххх*80#0*87#11100000*88#1*89#1*91#3*92#1*93#1*85#00*"),
            new KOmanda(4, KOmanda.TypeKomand.COMANDNEW, "Сервис начальный вкл", "EXECUTE xxxx 19"),
            new KOmanda(5, KOmanda.TypeKomand.COMANDNEW,"Сервис начальный выкл", "EXECUTE xxxx 1F"),
            new KOmanda(6, KOmanda.TypeKomand.COMAND,"Тестирование вкл", "EXECUTE xxxx 20"),
            new KOmanda(7, KOmanda.TypeKomand.PARAM_OB,"Общие настройки", ":xxxxx*39#CODE*20#000001*21#10*22#2*23#1*24#0*25#0*26#2*27#0*28#0*29#0*33#1*34#1*35#79214280028*36#2*37#1*38#1*43#0*44#0*45#0*46#1*53#1*54#0*55#1*56#0*57#3*"),
            new KOmanda(8, KOmanda.TypeKomand.PARAM_GSM,"Настройки подключения", ":xxxxx*47#84.204.102.210*48#6009*49#APN*51#LOGIN*52#PASSWORD*58#TELEFONE*"),
            new KOmanda(9, KOmanda.TypeKomand.PARAM_CAN,"Настройки CAN", ":ххххх*80#0*87#11100000*88#1*89#1*91#3*92#1*93#1*85#00*"),
            new KOmanda(10, KOmanda.TypeKomand.COMAND, "Сервис вкл", "EXECUTE xxxx 19"),
            new KOmanda(11, KOmanda.TypeKomand.COMAND,"Сервис выкл", "EXECUTE xxxx 1F"),
            new KOmanda(12, KOmanda.TypeKomand.SLED,"Запрос координат", "GPSG# XXXX 1 60"),
            new KOmanda(13, KOmanda.TypeKomand.PARAM_FREE,"Своя настройка", "")
    );

    public void SetKomand(int selectedIndex, String text) {
        (komndaSpisok.get(selectedIndex)).komanda = text;
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
