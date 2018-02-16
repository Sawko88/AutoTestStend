package sourse;

import java.util.Arrays;
import java.util.List;

public class ActionTestCollection {
    public   List<ActionTest> ActionSpisok = Arrays.asList(
            new ActionTest(0, ActionTest.TypeAction.NONE, "-", " ", "1", "0"),
            new ActionTest(1, ActionTest.TypeAction.ONOFF, "Замок зажигания", "out1=", "1", "0"),
            new ActionTest(2, ActionTest.TypeAction.ONOFF, "Капот", "out2=", "1", "0"),
            new ActionTest(3, ActionTest.TypeAction.ONOFF, "Дверь", "out3=", "1", "0"),
            new ActionTest(4, ActionTest.TypeAction.ONOFF, "Тормоз", "out4=", "1", "0"),
            new ActionTest(5, ActionTest.TypeAction.ONOFF, "Датчик объёма", "out5=", "1", "0"),
            new ActionTest(6, ActionTest.TypeAction.ONOFF, "КТС", "out6=", "1", "0"),
            new ActionTest(7, ActionTest.TypeAction.PWRONOFF, "Основное питание, вкл/выкл", "pwr1:", "1", "0"),
            new ActionTest(8, ActionTest.TypeAction.PWRONOFF, "Резервное питание, вкл/выкл", "pwr2:", "1", "0"),
            new ActionTest(9, ActionTest.TypeAction.PWRNAP, "Основное питание, 9-15В", "pwr1=", "1", "0"),
            new ActionTest(10, ActionTest.TypeAction.PWRNAP, "Резервное питание, 9-15В", "pwr2=", "1", "0"),
            new ActionTest(11, ActionTest.TypeAction.MOTOR, "Мотор блока", "mot1:", "1", "0"),
            new ActionTest(12, ActionTest.TypeAction.MOTOR, "Мотор РР", "mot2:", "1", "0"),
            new ActionTest(13, ActionTest.TypeAction.METKAONOFF, "Метка №1", "met1:", "1", "0"),
            new ActionTest(14, ActionTest.TypeAction.METKAONOFF, "Метка №2", "met2:", "1", "0"),
            new ActionTest(15, ActionTest.TypeAction.RARELE, "РР, вкл/выкл", "rar1:", "1", "0"),
            new ActionTest(16, ActionTest.TypeAction.PAUTHA, "Пауза", "", "1", "0"),
            new ActionTest(17, ActionTest.TypeAction.GSMCOM, "Послать команду", new KOmanda(0, "","")),
            new ActionTest(18, ActionTest.TypeAction.CAN, "Команда CAN", new Can(0, "",0))
    );

}
