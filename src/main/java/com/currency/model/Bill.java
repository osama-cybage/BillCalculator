package com.currency.model;

import java.util.List;

public record Bill (
		List<Item> items,
		Integer customerTenure,
		User userType,
		String originalCurrency,
		String targetCurrency
		) {}
