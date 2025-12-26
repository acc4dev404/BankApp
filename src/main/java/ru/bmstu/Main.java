package ru.bmstu;

import ru.bmstu.service.Bank;
import ru.bmstu.service.Logger;
import ru.bmstu.controller.Console;

public class Main {
    public static void main(String[] args) {
        Logger.log("INFO","Запуск системы");

        Bank bank = new Bank();
        Console menu = new Console(bank);
        menu.run();

        Logger.log("INFO","Остановка системы");
    }
}