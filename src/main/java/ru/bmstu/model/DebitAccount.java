package ru.bmstu.model;

public class DebitAccount extends Account {

    public DebitAccount(Customer owner) {
        super(owner);
    }

    public boolean withdraw(double amount) {
        if (amount <= getBalance()) {
            return super.withdraw(amount);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Дебетовый счет {Номер=%s; Баланс=%.2f; Клиент ФИО=%s; Клиент ID=%d}",
                getAccountNumber(), getBalance(), getOwner().getFullName(), getOwner().getId());
    }
}
