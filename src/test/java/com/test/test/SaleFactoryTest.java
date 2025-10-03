package com.test.test;

import com.test.test.Factory.SaleFactory;
import com.test.test.Model.BrazilSale;
import com.test.test.Model.ChileSale;
import com.test.test.Model.MexicoSale;
import com.test.test.Model.Sale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SaleFactoryTest {

    @Autowired
    private SaleFactory saleFactory;

    @Test
    void shouldReturnBrazilSaleAndCalculateVAT() throws NoSuchFieldException {
        Sale sale = saleFactory.saleByCountry("BR");
        Assertions.assertTrue(sale instanceof BrazilSale);
        Assertions.assertEquals(112.0, sale.calculatePriceWithVAT(100.0), 0.001);
    }

    @Test
    void shouldReturnChileSaleAndCalculateVAT() throws NoSuchFieldException {
        Sale sale = saleFactory.saleByCountry("CL");
        Assertions.assertTrue(sale instanceof ChileSale);
        Assertions.assertEquals(119.0, sale.calculatePriceWithVAT(100.0), 0.001);
    }

    @Test
    void shouldReturnMexicoSaleAndCalculateVAT() throws NoSuchFieldException {
        Sale sale = saleFactory.saleByCountry("MX");
        Assertions.assertTrue(sale instanceof MexicoSale);
        Assertions.assertEquals(116.0, sale.calculatePriceWithVAT(100.0), 0.001);
    }

    @Test
    void shouldThrowWhenUnsupportedCountry() {
        Assertions.assertThrows(NoSuchFieldException.class, () -> saleFactory.saleByCountry("XX"));
    }
}


