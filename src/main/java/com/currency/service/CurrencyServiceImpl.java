package com.currency.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
	
	@Value("${currency.exchange.apiKey}")
	private String apiKey;

	@Override
	public Optional<Double> convertCurrency(Bill bill) {		
		
		Double billInSourceCurrency = BillCalculator.calculate(bill.items(), bill.userType(), bill.customerTenure());
		ResponseEntity<String> response = exchangeRateApiCall(bill.originalCurrency());
		if(Objects.nonNull(response)) {
			JsonObject jsonResponse = new Gson().fromJson(response.getBody(), JsonObject.class);
			if(jsonResponse.get("result").getAsString().equals("success")) {
				Float conversionRate = jsonResponse.getAsJsonObject("rates").get(bill.targetCurrency()).getAsFloat();
				return Optional.of(billInSourceCurrency * conversionRate);
			}
		}
		return Optional.empty();
	}
	
	@Cacheable(value="rates", condition = "#originalCurrency=USD")
	private ResponseEntity<String> exchangeRateApiCall(String originalCurrency) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.getForEntity("https://open.er-api.com/v6/latest/"+originalCurrency+"?apikey="+apiKey,
					String.class);
		} catch(Exception ex) {
			log.error("Exception occurred while calling Exchange rate API : {}", ex.getMessage());
		}
		return response;
	}
}
