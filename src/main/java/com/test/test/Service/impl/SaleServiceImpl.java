package com.test.test.Service.impl;

import com.test.test.DTOs.SaleRequest;
import com.test.test.DTOs.SaleResponse;
import com.test.test.Factory.SaleFactory;
import com.test.test.Model.Sale;
import com.test.test.Service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleFactory saleFactory;

    @Override
    public SaleResponse getTotalPrice(SaleRequest saleRequest) throws NoSuchFieldException {
        Sale sale = saleFactory.saleByCountr(saleRequest.getCountry());

        BigDecimal amount = BigDecimal.valueOf(saleRequest.getAmount()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalAmount = BigDecimal
                .valueOf(sale.calculatePriceWithVAT(saleRequest.getAmount()))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal vatRate = finalAmount.divide(amount, 2, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);

        return new SaleResponse(
                saleRequest.getCountry().toUpperCase(),
                amount,
                vatRate,
                finalAmount
        );
    }
}


