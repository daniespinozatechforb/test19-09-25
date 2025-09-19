package com.test.test.Model;

public interface Sale {
    double calculatePriceWithVAT(double amount);

    default double calculatePrice(double amount) {
        return amount;
    }
}
