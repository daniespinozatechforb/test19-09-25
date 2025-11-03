package com.test.test.Controller;

import com.test.test.DTOs.SaleUploadResponse;
import com.test.test.Exception.InvalidExcelFormatException;
import com.test.test.Service.SaleExcelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/sales")
@AllArgsConstructor
@CrossOrigin
public class SaleExcelController {
    
    private final SaleExcelService saleExcelService;

    @GetMapping("/template")
    public ResponseEntity<byte[]> downloadTemplate() {
        try {
            ByteArrayOutputStream outputStream = saleExcelService.generateTemplate();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "sales_template.xlsx");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
                    
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }
            
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.badRequest().body("El archivo debe ser formato .xlsx");
            }
            
            SaleUploadResponse response = saleExcelService.processExcelFile(file);
            return ResponseEntity.ok(response);
            
        } catch (InvalidExcelFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al procesar el archivo");
        }
    }
}
