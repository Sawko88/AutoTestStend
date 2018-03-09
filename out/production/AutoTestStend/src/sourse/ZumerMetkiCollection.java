package sourse;

import java.util.Arrays;
import java.util.List;

public class ZumerMetkiCollection {
    public  static List<ZumerMetki> zumerMetkis = Arrays.asList(
            new ZumerMetki(0,"Определил",ZumerMetki.TypeZumMetki.OPR, Arrays.asList(ZumerMetki.TypeTick.LB, ZumerMetki.TypeTick.HB,ZumerMetki.TypeTick.LB)),
            new ZumerMetki(1,"Не определил", ZumerMetki.TypeZumMetki.NEOPR, Arrays.asList(ZumerMetki.TypeTick.LEVEL_H))
    );
}
