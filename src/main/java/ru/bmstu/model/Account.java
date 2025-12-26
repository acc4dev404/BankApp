package ru.bmstu.model;

public abstract class Account {

    private double balance;
    private final String accountNumber;
    private final Customer owner;
    private static int counter = 1;

    public Account(Customer owner) {
        accountNumber = String.format("%011d", counter++);
        balance = 0.0;
        this.owner = owner;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(Account to, double amount) {
        if (withdraw(amount)) {
            if (to.deposit(amount)) {
                return true;
            } else {
                deposit(amount);
                return false;
            }
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
    }
}