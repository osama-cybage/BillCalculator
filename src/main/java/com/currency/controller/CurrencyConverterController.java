package com.currency.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.currency.model.Bill;
import com.currency.service.CurrencyService;

@RestController
@RequestMapping("/api")
public class CurrencyConverterController {

	private static final Logger log = LoggerFactory.getLogger(CurrencyConverterController.class);

	@Autowired
	CurrencyService currencyService;
	
	@PostMapping("/calculate")
	public ResponseEntity<Double> calculateBill(@RequestBody Bill bill) {
	   Optional<Double> result = currencyService.convertAndCalculate(bill);
       return result.map(res -> new ResponseEntity<>(res, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("Hello", HttpStatus.OK);
	}
}
