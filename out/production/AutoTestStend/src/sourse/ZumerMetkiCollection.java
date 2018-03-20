package sourse;

import java.util.Arrays;
import java.util.List;

public class ZumerMetkiCollection {
    public  static List<ZumerMetki> zumerMetkis = Arrays.asList(
            new ZumerMetki(0,"Двойной импульс",ZumerMetki.TypeZumMetki.DVA, Arrays.asList(ZumerMetki.TypeTick.LB, ZumerMetki.TypeTick.HB,ZumerMetki.TypeTick.LB,ZumerMetki.TypeTick.LEVEL_H)),
            new ZumerMetki(1,"Не определил", ZumerMetki.TypeZumMetki.NEOPR, Arrays.asList(ZumerMetki.TypeTick.LEVEL_H)),
            new ZumerMetki(2,"Одиночный импульс", ZumerMetki.TypeZumMetki.ODIN, Arrays.asList(ZumerMetki.TypeTick.LB,ZumerMetki.TypeTick.LEVEL_H)),
            new ZumerMetki(3,"Тестовый импульс", ZumerMetki.TypeZumMetki.TEST, Arrays.asList(ZumerMetki.TypeTick.XZ)),
            new ZumerMetki(4,"Не анализировать", ZumerMetki.TypeZumMetki.NOANAL, Arrays.asList(ZumerMetki.TypeTick.XZ))
    );
}
