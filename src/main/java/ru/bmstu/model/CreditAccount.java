package ru.bmstu.model;

public class CreditAccount extends Account {
    private double creditLimit;

    public CreditAccount(Customer owner, double creditLimit) {
        super(owner);
        this.creditLimit = creditLimit >= 0 ? creditLimit : 0;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public boolean setCreditLimit(double creditLimit) {
        if (creditLimit >= 0) {
            this.creditLimit = creditLimit;
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        if (this.getBalance() + creditLimit >= amount) {
            return super.withdraw(amount);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Кредитный счет {Номер=%s; Баланс=%.2f; Кредитный лимит=%.2f; Клиент ФИО=%s; Клиент ID=%d}",
                getAccountNumber(), getBalance(), getCreditLimit(), getOwner().getFullName(), getOwner().getId());
    }
}
