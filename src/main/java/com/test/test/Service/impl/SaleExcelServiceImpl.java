package com.test.test.Service.impl;

import com.test.test.DTOs.SaleProcessError;
import com.test.test.DTOs.SaleProcessResult;
import com.test.test.DTOs.SaleUploadResponse;
import com.test.test.Exception.InvalidExcelFormatException;
import com.test.test.Exception.UnsupportedCountryException;
import com.test.test.Factory.SaleAmountFactory;
import com.test.test.Service.SaleExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleExcelServiceImpl implements SaleExcelService {

    private final SaleAmountFactory saleAmountFactory;

    @Override
    public ByteArrayOutputStream generateTemplate() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        
        Sheet salesDataSheet = workbook.createSheet("SalesData");
        Row headerRow = salesDataSheet.createRow(0);
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Cell countryHeader = headerRow.createCell(0);
        countryHeader.setCellValue("country");
        countryHeader.setCellStyle(headerStyle);
        
        Cell amountHeader = headerRow.createCell(1);
        amountHeader.setCellValue("amount");
        amountHeader.setCellStyle(headerStyle);
        
        salesDataSheet.setColumnWidth(0, 3000);
        salesDataSheet.setColumnWidth(1, 3000);
        
        Sheet versionSheet = workbook.createSheet("Version");
        Row versionHeaderRow = versionSheet.createRow(0);
        
        Cell versionHeader = versionHeaderRow.createCell(0);
        versionHeader.setCellValue("version");
        versionHeader.setCellStyle(headerStyle);
        
        Cell dateHeader = versionHeaderRow.createCell(1);
        dateHeader.setCellValue("date");
        dateHeader.setCellStyle(headerStyle);
        
        Row versionDataRow = versionSheet.createRow(1);
        versionDataRow.createCell(0).setCellValue("1.0");
        versionDataRow.createCell(1).setCellValue(LocalDate.now().toString());
        
        versionSheet.setColumnWidth(0, 3000);
        versionSheet.setColumnWidth(1, 3000);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        return outputStream;
    }

    @Override
    public SaleUploadResponse processExcelFile(MultipartFile file) throws IOException, InvalidExcelFormatException {
        if (file.isEmpty()) {
            throw new InvalidExcelFormatException("El archivo está vacío");
        }
        
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        
        Sheet salesDataSheet = workbook.getSheet("SalesData");
        if (salesDataSheet == null) {
            workbook.close();
            throw new InvalidExcelFormatException("El archivo no contiene la hoja 'SalesData'");
        }
        
        Row headerRow = salesDataSheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            throw new InvalidExcelFormatException("La hoja SalesData no tiene encabezados");
        }
        
        Cell countryHeaderCell = headerRow.getCell(0);
        Cell amountHeaderCell = headerRow.getCell(1);
        
        if (countryHeaderCell == null || amountHeaderCell == null ||
            !countryHeaderCell.getStringCellValue().equalsIgnoreCase("country") ||
            !amountHeaderCell.getStringCellValue().equalsIgnoreCase("amount")) {
            workbook.close();
            throw new InvalidExcelFormatException("Las columnas deben ser 'country' y 'amount'");
        }
        
        List<SaleProcessResult> results = new ArrayList<>();
        List<SaleProcessError> errors = new ArrayList<>();
        int processed = 0;
        int success = 0;
        
        for (int i = 1; i <= salesDataSheet.getLastRowNum(); i++) {
            Row row = salesDataSheet.getRow(i);
            
            if (row == null) {
                continue;
            }
            
            processed++;
            
            Cell countryCell = row.getCell(0);
            Cell amountCell = row.getCell(1);
            
            if (countryCell == null || amountCell == null) {
                errors.add(new SaleProcessError(i + 1, "", "Fila con celdas vacías"));
                continue;
            }
            
            String country;
            double amount;
            
            try {
                if (countryCell.getCellType() == CellType.STRING) {
                    country = countryCell.getStringCellValue().trim();
                } else {
                    errors.add(new SaleProcessError(i + 1, "", "El país debe ser texto"));
                    continue;
                }
                
                if (country.isEmpty()) {
                    errors.add(new SaleProcessError(i + 1, country, "El país no puede estar vacío"));
                    continue;
                }
                
                if (amountCell.getCellType() == CellType.NUMERIC) {
                    amount = amountCell.getNumericCellValue();
                } else if (amountCell.getCellType() == CellType.STRING) {
                    try {
                        amount = Double.parseDouble(amountCell.getStringCellValue());
                    } catch (NumberFormatException e) {
                        errors.add(new SaleProcessError(i + 1, country, "El monto debe ser numérico"));
                        continue;
                    }
                } else {
                    errors.add(new SaleProcessError(i + 1, country, "El monto debe ser numérico"));
                    continue;
                }
                
                if (!saleAmountFactory.isCountrySupported(country)) {
                    errors.add(new SaleProcessError(i + 1, country, "Country not supported"));
                    continue;
                }
                
                BigDecimal finalAmount = saleAmountFactory.calculateFinalAmount(country, amount);
                BigDecimal baseAmount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
                
                results.add(new SaleProcessResult(country.toUpperCase(), baseAmount, finalAmount));
                success++;
                
            } catch (UnsupportedCountryException e) {
                errors.add(new SaleProcessError(i + 1, countryCell.getStringCellValue(), e.getMessage()));
            } catch (Exception e) {
                errors.add(new SaleProcessError(i + 1, "", "Error procesando fila: " + e.getMessage()));
            }
        }
        
        workbook.close();
        
        return new SaleUploadResponse(processed, success, errors, results);
    }
}
