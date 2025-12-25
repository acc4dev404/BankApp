package ru.bmstu.service;

import ru.bmstu.model.*;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();


    public Customer createCustomer(String fullName) {
        Customer customer = new Customer(fullName);
        customers.add(customer);
        return customer;
    }

    public Account openDebitAccount(Customer owner) {
        Account acc = new DebitAccount(owner);
        accounts.add(acc);
        return acc;
    }

    public Customer findCustomerById(int customerId) {
        return customers.stream()
                .filter(customer -> customer.getId() == customerId)
                .findFirst()
                .orElse(null);
    }

    public Account openCreditAccount(Customer owner, double creditLimit) {
        Account acc = new CreditAccount(owner, creditLimit);
        accounts.add(acc);
        return acc;
    }

    public Account findAccountByNumber(String accountNumber) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public boolean deposit(String accountNumber, double amount) {
        Account acc = findAccountByNumber(accountNumber);
        if (acc == null) {
            transactions.add(new Transaction(
                    TransactionType.DEPOSIT,
                    amount,
                    null,
                    accountNumber,
                    false,
                    "Пополнение несуществующего счета"));

            return false;
        }
        boolean success = acc.deposit(amount);
        transactions.add(new Transaction(
                TransactionType.DEPOSIT,
                amount,
                null,
                accountNumber,
                success,
                "Пополнение счета"));
        return success;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account acc = findAccountByNumber(accountNumber);
        if (acc == null) {
            transactions.add(new Transaction(
                    TransactionType.WITHDRAW,
                    amount,
                    accountNumber,
                    null,
                    false,
                    "Снятие средств с несуществующего счета"));

            return false;
        }
        boolean success = acc.withdraw(amount);
        transactions.add(new Transaction(
                TransactionType.WITHDRAW,
                amount,
                accountNumber,
                null,
                success,
                "Снятие средств со счета"));
        return success;
    }

    public boolean transfer(String from, String to, double amount) {
        Account fromAcc = findAccountByNumber(from);
        Account toAcc = findAccountByNumber(to);
        if (fromAcc == null) {
            transactions.add(new Transaction(
                    TransactionType.TRANSFER,
                    amount,
                    from,
                    to,
                    false,
                    "Перевод средств с несуществующего счета"));
            return false;
        }
        if (toAcc == null) {
            transactions.add(new Transaction(
                    TransactionType.TRANSFER,
                    amount,
                    from,
                    to,
                    false,
                    "Перевод средств на несуществующий счет"));

            return false;
        }
        boolean success = fromAcc.transfer(toAcc, amount);
        transactions.add(new Transaction(
                TransactionType.TRANSFER,
                amount,
                from,
                to,
                success,
                "Перевод средств между счетами"));

        return success;
    }

    public void printCustomerAccounts(int customerId) {
        boolean found = false;
        for (Account acc : accounts) {
            if (acc.getOwner().getId() == customerId) {
                System.out.println(acc);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Клиент не найден. ID: " + customerId);
        }
    }

    public void printTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("Журнал транзакций пуст.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public void printReport() {
        long debitCount = accounts.stream().filter(a -> a instanceof DebitAccount).count();
        long creditCount = accounts.stream().filter(a -> a instanceof CreditAccount).count();
        double totalDebitBalance = accounts.stream()
                .filter(a -> a instanceof DebitAccount)
                .mapToDouble(Account::getBalance)
                .sum();
        double totalCreditBalance = accounts.stream()
                .filter(a -> a instanceof CreditAccount)
                .mapToDouble(Account::getBalance)
                .sum();
        long successCount = transactions.stream().filter(Transaction::isSuccess).count();
        long failCount = transactions.size() - successCount;
        System.out.println("Количество дебетовых счетов: " + debitCount);
        System.out.println("Количество кредитных счетов: " + creditCount);
        System.out.println("Общий баланс дебетовых счетов: " + totalDebitBalance);
        System.out.println("Общий баланс кредитных счетов: " + totalCreditBalance);
        System.out.println("Количество успешных транзакций: " + successCount);
        System.out.println("Количество неуспешных транзакций: " + failCount);
    }
}
