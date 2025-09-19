package com.test.test.Model;

import org.springframework.stereotype.Component;

@Component("MX")
public class MexicoSale implements Sale{
    private static final double VAT = 0.16;

    @Override
    public double calculatePriceWithVAT(double amount) {
        return amount * (1 + VAT);
    }
}
