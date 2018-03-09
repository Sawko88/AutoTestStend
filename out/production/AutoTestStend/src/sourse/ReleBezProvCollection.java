package sourse;

import java.util.Arrays;
import java.util.List;

public class ReleBezProvCollection {
    public  static List<ReleBezProv> releBezProvSpisok = Arrays.asList(
            new ReleBezProv(0,"Разблокировано", ReleBezProv.Typebezblock.BEZBLOCK),
            new ReleBezProv(1,"Заблокировано", ReleBezProv.Typebezblock.BLOCK)
    );
}
