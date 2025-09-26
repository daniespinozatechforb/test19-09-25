package com.test.test.Controller;

import com.test.test.DTOs.SaleRequest;
import com.test.test.DTOs.SaleResponse;
import com.test.test.Factory.SaleFactory;
import com.test.test.Model.Sale;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api/sales")
@AllArgsConstructor
public class SaleController {
    private final SaleFactory saleFactory;

    @PostMapping("/price")
    public ResponseEntity<?> getTotalPrice (@RequestBody SaleRequest saleRequest) throws NoSuchFieldException{
        try{
            Sale sale = saleFactory.saleByCountr(saleRequest.getCountry());
            BigDecimal amount = BigDecimal.valueOf(saleRequest.getAmount()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal finalAmount = BigDecimal.valueOf(sale.calculatePriceWithVAT(saleRequest.getAmount())).setScale(2, RoundingMode.HALF_UP);
            BigDecimal vatRate = finalAmount.divide(amount, 2, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);


            SaleResponse response = new SaleResponse(
                    saleRequest.getCountry().toUpperCase(),
                    amount,
                    vatRate,
                    finalAmount
            );

            return ResponseEntity.ok(response);
        }catch (NoSuchFieldException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
