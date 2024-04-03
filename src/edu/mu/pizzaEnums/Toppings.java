package edu.mu.pizzaEnums;

public enum Toppings {
    TOMATO(1.50),
    CHEESE(2.00),
    PINEAPPLE(2.50),
    BLACK_OLIVE(1.25),
    ITALIAN_SAUSAGE(3.50),
    PEPPERONI(3.00),
    BELL_PEPPER(1.00),
    MUSHROOM(1.50),
    CANADIAN_BACON(4.00);

    private final double price;

    Toppings(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
}