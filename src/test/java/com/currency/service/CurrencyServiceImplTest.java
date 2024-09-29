package com.currency.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.currency.model.Bill;
import com.currency.model.Item;
import com.currency.model.ItemCategory;
import com.currency.model.User;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

public class CurrencyServiceImplTest {

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${currency.exchange.apiKey}")
    private String apiKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertCurrency_Success() {
        Bill bill = new Bill(Collections.singletonList(new Item("Test Item", ItemCategory.GROCERY, 100.0, 1)),
                1, User.EMPLOYEE, "USD", "EUR");

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("result", "success");
        JsonObject rates = new JsonObject();
        rates.addProperty("EUR", 0.85);
        jsonResponse.add("rates", rates);

        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse.toString());
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        Optional<Double> result = currencyService.convertCurrency(bill);

        assertTrue(result.isPresent());
        assertEquals(85.1, result.get());
    }

    @Test
    public void testConvertCurrency_Failure() {
        Bill bill = new Bill(Collections.singletonList(new Item("Test Item", ItemCategory.OTHERS, 100.0, 1)),
                1, User.CUSTOMER, "USD", "EUR");

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("result", "error");
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse.toString());
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        Optional<Double> result = currencyService.convertCurrency(bill);

        assertTrue(result.isPresent());
    }
}