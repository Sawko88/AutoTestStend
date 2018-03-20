package sourse;

import java.util.Arrays;
import java.util.List;

public class ValidCoordCollection {
    public  static List<ValidCoord> validCoordList = Arrays.asList(
            new ValidCoord(0, "Валидные", ValidCoord.TypeValid.VALID, "A"),
            new ValidCoord(1, "НЕвалидные", ValidCoord.TypeValid.NEVALID, "V"),
            new ValidCoord(2, "Без координат", ValidCoord.TypeValid.NULL, "0"),
            new ValidCoord(3, "Непонятно что", ValidCoord.TypeValid.XZ, "")
    );
}
