package com.currency.service;

import java.util.Optional;

import com.currency.model.Bill;

public interface CurrencyService {
	
	Optional<Double> convertAndCalculate(Bill bill);

}
