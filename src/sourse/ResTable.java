package sourse;

import java.util.Arrays;
import java.util.List;

public class ResTable {
    public static List<ResTableElement> resTableEl = Arrays.asList(
         new ResTableElement(0, ResTableElement.TypeElementRes.SIGNAL, "Не пришёл сигнал"),
         new ResTableElement(1, ResTableElement.TypeElementRes.INDIKACIA, "Нет индикации"),
         new ResTableElement(2, ResTableElement.TypeElementRes.ZVUK, "Нет звукового сигнала метки"),
         new ResTableElement(3, ResTableElement.TypeElementRes.RR_BLOK, "Нет блокировки"),
         new ResTableElement(4, ResTableElement.TypeElementRes.VALID, "Нет валидности")
    );

    public void ClearAllFlags() {
        for (int i = 0 ; i<resTableEl.size(); i++){
            resTableEl.get(i).wait = false;
            resTableEl.get(i).finish = true;
        }
    }

    public void SetFlag(int index, boolean wait, boolean finish) {
        resTableEl.get(index).wait = wait;
        resTableEl.get(index).finish = finish;

    }
}
