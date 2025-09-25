package com.test.test.Model;

import org.springframework.stereotype.Component;

@Component("BR")
public class BrazilSale implements Sale{
    private static final double VAT = 0.12;
    @Override
    public double calculatePriceWithVAT(double amount) {
        return amount * (1 + VAT);
    }
}
