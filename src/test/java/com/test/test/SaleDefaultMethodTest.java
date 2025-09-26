package com.test.test;

import com.test.test.Model.BrazilSale;
import com.test.test.Model.Sale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SaleDefaultMethodTest {

    @Test
    void defaultCalculatePriceReturnsSameAmount() {
        Sale sale = new BrazilSale();
        Assertions.assertEquals(100.0, sale.calculatePrice(100.0), 0.001);
    }
}


