package com.test.test.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleProcessError {
    private int row;
    private String country;
    private String error;
}
