package com.currency.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.currency.model.*;
import com.currency.util.BillCalculator;
import com.currency.util.CurrencyParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class CurrencyServiceImplTest {

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Mock
    CurrencyParser currencyParser;

    @Mock
    BillCalculator billCalculator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertCurrency_Success() {
        Bill bill = mock(Bill.class);
        Double expectedAmountInSourceCurrency = 100.0;
        Double expectedConvertedAmount = 85.0;

        when(billCalculator.calculate(bill.items(), bill.user()))
                    .thenReturn(expectedAmountInSourceCurrency);

            when(currencyParser.calculateBillInTargetCurrency(bill, expectedAmountInSourceCurrency))
                    .thenReturn(Optional.of(expectedConvertedAmount));

            Optional<Double> result = currencyService.convertAndCalculate(bill);

            assertTrue(result.isPresent());
            assertEquals(expectedConvertedAmount, result.get());
    }

    @Test
    public void testConvertCurrency_Failure() {
        Bill bill = mock(Bill.class);
        Double expectedAmountInSourceCurrency = 100.0;

        when(billCalculator.calculate(bill.items(), bill.user()))
                    .thenReturn(expectedAmountInSourceCurrency);

            when(currencyParser.calculateBillInTargetCurrency(bill, expectedAmountInSourceCurrency))
                    .thenReturn(Optional.empty());

            Optional<Double> result = currencyService.convertAndCalculate(bill);

            assertFalse(result.isPresent());
        }
    }