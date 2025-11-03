package com.test.test.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SaleProcessResult {
    private String country;
    private BigDecimal baseAmount;
    private BigDecimal finalAmount;
}
