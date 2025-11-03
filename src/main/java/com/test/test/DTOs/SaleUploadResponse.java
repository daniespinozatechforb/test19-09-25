package com.test.test.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SaleUploadResponse {
    private int processed;
    private int success;
    private List<SaleProcessError> errors;
    private List<SaleProcessResult> results;
}
