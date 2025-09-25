package com.test.test.Factory;

import com.test.test.Model.Sale;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaleFactory {
    private ApplicationContext context;

    public Sale saleByCountr(String countryCode) throws NoSuchFieldException{
        try {
            return (Sale) context.getBean(countryCode.toUpperCase());
        } catch (Exception e) {
            throw new NoSuchFieldException("No se encontró el país con las siglas: " + countryCode.toUpperCase());
        }
    }
}
