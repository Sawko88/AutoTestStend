package sourse;

import java.util.Arrays;
import java.util.List;

public class CanCollection {
    public  static List<Can> canSpisok = Arrays.asList(
        new Can("Дверь", 0,0),
        new Can("Замок зажигания", 1,0),
        new Can("Капот", 2,0),
        new Can("Тормоз", 3,0)

    );

    public void setState(int selectedIndex, int i) {
        (canSpisok.get(selectedIndex)).state = i;
    }

    public boolean getState(int selectedIndex) {
        if ( (canSpisok.get(selectedIndex)).state == 1)
        {
            return true;
        } else {
            return false;
        }

    }
}
