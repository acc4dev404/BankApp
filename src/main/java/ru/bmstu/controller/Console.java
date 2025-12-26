package ru.bmstu.controller;

import ru.bmstu.model.Customer;
import ru.bmstu.service.Bank;

import java.util.Scanner;

public class Console {
    private final Bank bank;
    private Scanner scanner;
    private int lengthLine = 40;
    private final String[] MenuItems = {
            "Создать клиента",
            "Открыть дебетовый счёт",
            "Открыть кредитный счёт",
            "Пополнить счёт",
            "Снять со счёта",
            "Перевести между счетами",
            "Показать счета клиента",
            "Показать все транзакции",
            "Отчёт банка",
            "Выход"
    };

    public Console(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            showMainMenu();
            int choice = readInt("Выберите действие: ");
            if (choice == 10) {
                break;
            }
            processChoice(choice);
            pause();
        }
        scanner.close();
    }


    private void showMainMenu() {
        showHeader("ГЛАВНОЕ МЕНЮ");
        int counter = 1;
        for (String item : MenuItems) {
            System.out.println(counter++ + ".\t" + item);
        }
        showLine();
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> createCustomer();
            case 2 -> openDebitAccount();
            case 3 -> openCreditAccount();
            case 4 -> deposit();
            case 5 -> withdraw();
            case 6 -> transfer();
            case 7 -> showCustomerAccounts();
            case 8 -> showTransactions();
            case 9 -> showReport();
            default -> showError("Неверный выбор! Введите число от 1 до 10.");
        }
    }

    private void createCustomer() {
        showHeader("СОЗДАНИЕ КЛИЕНТА");
        String fullName = readLine("Введите ФИО клиента: ");

        if (fullName.trim().isEmpty()) {
            showError("ФИО не может быть пустым.");
            return;
        }
        showSuccess("Клиент создан: " + bank.createCustomer(fullName));
    }

    private void openDebitAccount() {
        showHeader("ОТКРЫТИЕ ДЕБЕТОВОГО СЧЁТА");
        int customerId = readInt("Введите ID клиента: ");
        Customer customer = bank.findCustomerById(customerId);
        if (customer != null) {
            showSuccess("Дебетовый счёт открыт: " + bank.openDebitAccount(customer));
        } else {
            showError("Клиент не найден.");
        }
    }

    private void openCreditAccount() {
        showHeader("ОТКРЫТИЕ КРЕДИТНОГО СЧЁТА");
        int customerId = readInt("Введите ID клиента: ");
        double limit = readDouble("Введите кредитный лимит: ");
        if (limit < 0) {
            showError("Лимит не может быть меньше 0.");
            return;
        }
        Customer customer = bank.findCustomerById(customerId);
        if (customer != null) {
            showSuccess("Кредитный счёт открыт: " + bank.openCreditAccount(customer, limit));
        } else {
            showError("Клиент не найден.");
        }
    }

    private void deposit() {
        showHeader("ПОПОЛНЕНИЕ СЧЁТА");
        String accountNumber = readLine("Введите номер счёта: ");
        double amount = readDouble("Введите сумму: ");
        if (amount <= 0) {
            showError("Сумма должна быть положительной.");
            return;
        }
        if (bank.deposit(accountNumber, amount)) {
            showSuccess("Счёт пополнен успешно.");
        } else {
            showError("Не удалось пополнить счёт.");
        }
    }

    private void withdraw() {
        showHeader("СНЯТИЕ СО СЧЁТА");
        String accountNumber = readLine("Введите номер счёта: ");
        double amount = readDouble("Введите сумму: ");
        if (amount <= 0) {
            showError("Сумма должна быть положительной.");
            return;
        }
        if (bank.withdraw(accountNumber, amount)) {
            showSuccess("Средства сняты успешно.");
        } else {
            showError("Не удалось снять средства.");
        }
    }

    private void transfer() {
        showHeader("ПЕРЕВОД СРЕДСТВ");
        String from = readLine("Введите номер счёта отправителя: ");
        String to = readLine("Введите номер счёта получателя: ");
        double amount = readDouble("Введите сумму: ");
        if (amount <= 0) {
            showError("Сумма должна быть положительной.");
            return;
        }
        if (from.equals(to)) {
            showError("Счета не должны совпадать!");
            return;
        }
        if (bank.transfer(from, to, amount)) {
            showSuccess("Перевод выполнен успешно.");
        } else {
            showError("Не удалось выполнить перевод.");
        }
    }

    private void showCustomerAccounts() {
        showHeader("ПРОСМОТР СЧЕТОВ");
        int customerId = readInt("Введите ID клиента: ");
        bank.printCustomerAccounts(customerId);
    }

    private void showTransactions() {
        showHeader("ИСТОРИЯ ОПЕРАЦИЙ");
        bank.printTransactions();
    }

    private void showReport() {
        showHeader("ФИНАНСОВЫЙ ОТЧЕТ");
        bank.printReport();
    }

    private void showLine() {
        System.out.println("═".repeat(lengthLine));
    }

    private void showSuccess(String str) {
        System.out.println("✓ " + str);
    }

    private void showError(String str) {
        System.out.println("✗ " + str);
    }

    private void showHeader(String str) {
        showLine();
        System.out.println(" ".repeat(lengthLine/2 - str.length()/2) + str);
        showLine();
    }

    private void pause() {
        System.out.print("\nНажмите Enter для продолжения...");
        scanner.nextLine();
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Введите целое число: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}