package com.test.test.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SaleResponse {
    private String country;
    private BigDecimal amount;
    private BigDecimal vatRate;
    private BigDecimal finalAmount;
}
