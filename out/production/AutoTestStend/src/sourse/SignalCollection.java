package sourse;

import java.util.Arrays;
import java.util.List;

public class SignalCollection {
    public List<Signal> signalSpisok = Arrays.asList(
            new Signal(0, Signal.SignalType.CONTROL, "0C","Снятие перитметра"),
            new Signal(1, Signal.SignalType.CONTROL, "0D","Постановка под охрану"),
            new Signal(2, Signal.SignalType.CONTROL, "37","Переход в сервис удалённо"),
            new Signal(3, Signal.SignalType.CONTROL, "50","Переход в сервис по кнопке"),
            new Signal(4, Signal.SignalType.CONTROL, "5A","Дверь открыта"),
            new Signal(5, Signal.SignalType.CONTROL, "5B","Дверь закрыта"),
            new Signal(6, Signal.SignalType.CONTROL, "3A","Выход из сервиса удалённо"),
            new Signal(7, Signal.SignalType.CONTROL, "3B","Тестирование начало"),
            new Signal(8, Signal.SignalType.CONTROL, "25","Тест"),
            new Signal(9, Signal.SignalType.NONE, "TEST 000000000","Тестирование пройдено успешно"),
            new Signal(10, Signal.SignalType.CONTROL, "5E","Зажигание включено"),
            new Signal(11, Signal.SignalType.CONTROL, "5F","Зажигание выключено"),
            new Signal(12, Signal.SignalType.CONTROL, "34","Двигатель разблокирован"),
            new Signal(13, Signal.SignalType.CONTROL, "55","Подача питания на блок"),
            new Signal(14, Signal.SignalType.PARAM_GSM, "IP:","Настройки GSM"),
            new Signal(15, Signal.SignalType.PARAM_OB, "79214280028","Настройки общие"),
            new Signal(16, Signal.SignalType.NONE, "TEST 000000001","Тестирование пройдено успешно, без GPS"),
            new Signal(17, Signal.SignalType.CONTROL, "6F","Мониторинг"),
            new Signal(18, Signal.SignalType.TEST0531, "TEST 000000000 TEST 000000001","Тестирование 0531"),
            new Signal(19, Signal.SignalType.CONTROL, "51","Выход их сервиса по кнопке"),
            new Signal(20, Signal.SignalType.CONTROL, "66","Восстановлене резервной АКБ"),
            new Signal(21, Signal.SignalType.CONTROL, "24","Восстановление АКБ")


    );
}
