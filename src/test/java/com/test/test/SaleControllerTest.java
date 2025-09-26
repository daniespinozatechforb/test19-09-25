package com.test.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.test.DTOs.SaleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnResponseBrazil() throws Exception {
        SaleRequest request = new SaleRequest();
        request.setCountry("BR");
        request.setAmount(100.0);

        mockMvc.perform(post("/api/sales/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("BR"))
                .andExpect(jsonPath("$.amount").value("100.0"))
                .andExpect(jsonPath("$.finalAmount").value("112.0"));
    }

    @Test
    void shouldReturnResponseChile() throws Exception {
        SaleRequest request = new SaleRequest();
        request.setCountry("CL");
        request.setAmount(100.0);

        mockMvc.perform(post("/api/sales/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("CL"))
                .andExpect(jsonPath("$.amount").value("100.0"))
                .andExpect(jsonPath("$.finalAmount").value("119.0"));
    }

    @Test
    void shouldReturnResponseMexico() throws Exception {
        SaleRequest request = new SaleRequest();
        request.setCountry("MX");
        request.setAmount(100.0);

        mockMvc.perform(post("/api/sales/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("MX"))
                .andExpect(jsonPath("$.amount").value("100.0"))
                .andExpect(jsonPath("$.finalAmount").value("116.0"));
    }

    @Test
    void shouldReturnBadRequest() throws Exception {
        SaleRequest request = new SaleRequest();
        request.setCountry("XX");
        request.setAmount(100.0);

        mockMvc.perform(post("/api/sales/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}


