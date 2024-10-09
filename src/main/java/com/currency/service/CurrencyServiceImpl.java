package com.currency.service;

import java.util.Optional;

import com.currency.util.CurrencyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.model.Bill;
import com.currency.util.BillCalculator;
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
