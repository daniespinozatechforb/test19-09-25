package com.test.test.Factory;

import com.test.test.Exception.UnsupportedCountryException;
import com.test.test.Model.Sale;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class SaleAmountFactory {
    private final SaleFactory saleFactory;
    
    private static final List<String> SUPPORTED_COUNTRIES = Arrays.asList("CL", "BR", "MX");

    public BigDecimal calculateFinalAmount(String country, double amount) throws UnsupportedCountryException {
        String countryCode = country.toUpperCase();
        
        if (!SUPPORTED_COUNTRIES.contains(countryCode)) {
            throw new UnsupportedCountryException("Country not supported");
        }
        
        try {
            Sale sale = saleFactory.saleByCountry(countryCode);
            return BigDecimal.valueOf(sale.calculatePriceWithVAT(amount))
                    .setScale(2, RoundingMode.HALF_UP);
        } catch (NoSuchFieldException e) {
            throw new UnsupportedCountryException("Country not supported");
        }
    }
    
    public boolean isCountrySupported(String country) {
        return SUPPORTED_COUNTRIES.contains(country.toUpperCase());
    }
}
