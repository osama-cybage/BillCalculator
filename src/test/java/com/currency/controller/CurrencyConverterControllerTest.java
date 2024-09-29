package com.currency.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.currency.model.Bill;
import com.currency.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

public class CurrencyConverterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyConverterController currencyConverterController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(currencyConverterController).build();
    }

    @Test
    public void testCalculateBill_Success() throws Exception {
        Bill bill = mock(Bill.class);
        Double expectedAmount = 100.0;
        when(currencyService.convertCurrency(bill)).thenReturn(Optional.of(expectedAmount));

        mockMvc.perform(post("/api/calculate")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(bill)));
    }

    @Test
    public void testCalculateBill_Failure() throws Exception {
        Bill bill = mock(Bill.class);
        when(currencyService.convertCurrency(bill)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/calculate")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(bill)))
                .andExpect(status().isInternalServerError());
    }
}