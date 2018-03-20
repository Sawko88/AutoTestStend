package sourse;

import java.util.Arrays;
import java.util.List;

public class CanCollection {
    public  static List<Can> canSpisok = Arrays.asList(
        new Can(0,"Дверь", "01","00", 2, 2),
        new Can(1,"Замок зажигания", "01","00", 4, 6),
        new Can(2,"Капот", "01","00", 3, 4),
        new Can(3,"Тормоз", "01","00", 3, 6),
        new Can(4,"Багажник", "01","00", 3, 2)

    );

    public void setState(int selectedIndex, int i) {
        (canSpisok.get(selectedIndex)).state = i;
    }

    public boolean getState(int selectedIndex) {
        if ( canSpisok.get(selectedIndex).curstait == canSpisok.get(selectedIndex).onstait)
        {
            return true;
        } else {
            return false;
        }

    }

    public Can GetCan(int selectedIndex) {
        Can cann;
        cann = canSpisok.get(selectedIndex);
        return cann;
    }

    public String GetName(int selectedIndex) {
        return (canSpisok.get(selectedIndex)).name;
    }
}
