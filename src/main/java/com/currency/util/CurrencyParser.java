package com.currency.util;

import com.currency.model.Bill;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Component
public class CurrencyParser {

    @Value("${currency.exchange.apiKey}")
    private String apiKey;

    @Value("${currency.exchange.baseUrl}")
    private String baseUrl;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(CurrencyParser.class);

    @Cacheable(value = "rates")
    @HystrixCommand(fallbackMethod = "fallback")
    public Optional<Double> calculateBillInTargetCurrency(Bill bill, Double billInSourceCurrency) {
        log.info("Calling exchange rate api...");
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + bill.originalCurrency()+"?apikey=" + apiKey,
                    String.class);
            return parseResponse(response, bill, billInSourceCurrency);
        } catch(Exception ex) {
            log.error("Exception occurred while calling Exchange rate API : {}", ex.getMessage());
        }
        return Double.valueOf(0.00d).describeConstable();
    }

    private static Optional<Double> parseResponse(ResponseEntity<String> response, Bill bill, Double billInSourceCurrency) {
        if (Objects.nonNull(response)) {
            JsonObject jsonResponse = new Gson().fromJson(response.getBody(), JsonObject.class);
            if (jsonResponse != null && jsonResponse.get("result").getAsString().equals("success")) {
                float conversionRate = jsonResponse.getAsJsonObject("rates").get(bill.targetCurrency()).getAsFloat();
                return Optional.of((double) Math.round(billInSourceCurrency * conversionRate * 100) / 100);
            }
        }
        return Optional.empty();
    }

    public String fallback() {
        return "Default Response";
    }
}
