package ru.bmstu.model;

public class Customer {
    private final int id;
    private final String fullName;
    private static int counter = 1;

    public Customer(String fullName) {
        this.id = counter++;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return String.format("Клиент {ID=%d; ФИО=%s}", id, fullName);
    }
}