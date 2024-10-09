package com.currency.model;

import java.util.List;

public record Bill (
		List<Item> items,
		User user,
		String originalCurrency,
		String targetCurrency
		) {}
