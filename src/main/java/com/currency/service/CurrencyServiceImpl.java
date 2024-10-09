package com.currency.service;

import java.util.Objects;
import java.util.Optional;

import com.currency.util.CurrencyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.currency.model.Bill;
import com.currency.util.BillCalculator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyParser currencyParser;

	private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

	@Override
	public Optional<Double> convertCurrency(Bill bill) {

		Double billInSourceCurrency = BillCalculator.calculate(bill.items(), bill.user());
		return currencyParser.calculateBillInTargetCurrency(bill, billInSourceCurrency);
	}
}
