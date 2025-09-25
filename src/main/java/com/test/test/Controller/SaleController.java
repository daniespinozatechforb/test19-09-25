package com.test.test.Controller;

import com.test.test.DTOs.SaleRequest;
import com.test.test.DTOs.SaleResponse;
import com.test.test.Service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
@AllArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/price")
    public ResponseEntity<?> getTotalPrice (@RequestBody SaleRequest saleRequest) throws NoSuchFieldException{
        try{
            SaleResponse response = saleService.getTotalPrice(saleRequest);
            return ResponseEntity.ok(response);
        }catch (NoSuchFieldException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
