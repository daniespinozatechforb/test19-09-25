package com.test.test.Model;

import org.springframework.stereotype.Component;

@Component("CL")
public class ChileSale implements Sale{
    private static final double VAT = 0.19;

    @Override
    public double calculatePriceWithVAT(double amount) {
        return amount * (1 + VAT);
    }
}
