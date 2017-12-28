package sourse;

import java.util.Arrays;
import java.util.List;

public class SignalCollection {
    public  static List<Signal> signalSpisok = Arrays.asList(
            new Signal(0, "Снятие перитметра"),
            new Signal(1, "Постановка под охрану"),
            new Signal(2, "Переход в сервис удалённо"),
            new Signal(3, "Переход в сервис по кнопке"),
            new Signal(4, "Дверь открыта"),
            new Signal(5, "Дверь закрыта"),
            new Signal(6, "Выход из сервиса"),
            new Signal(7, "Тестирование начало"),
            new Signal(8, "Тест"),
            new Signal(9, "Тестирование пройдено успешно")
    );
}
