package com.currency.util;

import com.currency.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyParserTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyParser currencyParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateBillInTargetCurrency_Success() {
        Bill bill = mock(Bill.class);
        when(bill.originalCurrency()).thenReturn("USD");
        when(bill.targetCurrency()).thenReturn("EUR");

        Double billInSourceCurrency = 100.0;
        String jsonResponse = "{\"result\":\"success\", \"rates\":{\"EUR\":0.85}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        Optional<Double> result = currencyParser.calculateBillInTargetCurrency(bill, billInSourceCurrency);

        assertTrue(result.isPresent());
        assertEquals(85.0, result.get());
    }

    @Test
    public void testCalculateBillInTargetCurrency_Failure() {
        Bill bill = mock(Bill.class);
        when(bill.originalCurrency()).thenReturn("USD");
        when(bill.targetCurrency()).thenReturn("EUR");

        String jsonResponse = "{\"result\":\"error\"}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        Optional<Double> result = currencyParser.calculateBillInTargetCurrency(bill, 100.0);

        assertFalse(result.isPresent());
    }
}
