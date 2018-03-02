package sourse;

import java.util.Arrays;
import java.util.List;

public class ResTable {
    public static List<ResTableElement> resTableEl = Arrays.asList(
         new ResTableElement(0, ResTableElement.TypeElementRes.SIGNAL),
         new ResTableElement(1, ResTableElement.TypeElementRes.INDIKACIA),
         new ResTableElement(2, ResTableElement.TypeElementRes.ZVUK),
         new ResTableElement(3, ResTableElement.TypeElementRes.RR_BLOK)

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
