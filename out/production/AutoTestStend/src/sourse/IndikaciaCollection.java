package sourse;

import java.util.Arrays;
import java.util.List;

public class IndikaciaCollection {
    public  static List<Indikacia> indikaciaSpisok = Arrays.asList(
            new Indikacia(0,"Снятие", Indikacia.TypeInd.SNIATIE, Arrays.asList(Indikacia.TypeTick.LEVEL_H)),
            new Indikacia(1,"Постановка", Indikacia.TypeInd.OHRANA,Arrays.asList(Indikacia.TypeTick.LB, Indikacia.TypeTick.HP)),
            new Indikacia(2,"Тестирование", Indikacia.TypeInd.TESTIROVANIE,Arrays.asList(Indikacia.TypeTick.HB, Indikacia.TypeTick.LP)),
            new Indikacia(3,"Сервис", Indikacia.TypeInd.SERVIC,Arrays.asList(Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB,Indikacia.TypeTick.HP)),
            new Indikacia(4,"Загрузка", Indikacia.TypeInd.LOADING,Arrays.asList(Indikacia.TypeTick.LEVEL_L)),
            new Indikacia(5,"Охрана ЗЗ", Indikacia.TypeInd.OHRANA_ZZ,Arrays.asList(Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HB)),
            new Indikacia(6,"Паника", Indikacia.TypeInd.PANIKA,Arrays.asList(  Indikacia.TypeTick.LB,Indikacia.TypeTick.HB, Indikacia.TypeTick.LB,Indikacia.TypeTick.HB,Indikacia.TypeTick.LB,Indikacia.TypeTick.HB,Indikacia.TypeTick.LB,Indikacia.TypeTick.HB,Indikacia.TypeTick.LB,Indikacia.TypeTick.HP)),
            new Indikacia(7,"Супер Охрана", Indikacia.TypeInd.SYPEROHRANA,Arrays.asList(Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HB,Indikacia.TypeTick.LB, Indikacia.TypeTick.HP))
    );
}
