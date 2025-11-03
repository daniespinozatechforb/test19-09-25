package com.test.test.Service;

import com.test.test.DTOs.SaleUploadResponse;
import com.test.test.Exception.InvalidExcelFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface SaleExcelService {
    ByteArrayOutputStream generateTemplate() throws IOException;
    SaleUploadResponse processExcelFile(MultipartFile file) throws IOException, InvalidExcelFormatException;
}
