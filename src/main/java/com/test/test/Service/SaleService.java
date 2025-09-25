package com.test.test.Service;

import com.test.test.DTOs.SaleRequest;
import com.test.test.DTOs.SaleResponse;

public interface SaleService {
    SaleResponse getTotalPrice(SaleRequest saleRequest) throws NoSuchFieldException;
}


