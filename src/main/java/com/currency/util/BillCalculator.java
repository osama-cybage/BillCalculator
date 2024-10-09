package com.currency.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.currency.model.Item;
import com.currency.model.ItemCategory;
import com.currency.model.User;

public class BillCalculator {

	public static Double calculate(List<Item> items, User user) {
		
		Map<Boolean, List<Item>> map = items.stream().collect(Collectors.partitioningBy(item -> item.category().name().equals(ItemCategory.GROCERY.name())));
		List<Item> groceryItems = map.get(true);
		List<Item> nonGroceryItems =  map.get(false);
		
		double groceryAmount = groceryItems.stream().mapToDouble(item -> item.price() * item.quantity()).sum();
		
		double nonGroceryAmount = nonGroceryItems.stream().mapToDouble(item -> item.price() * item.quantity()).sum();

		double discountRate = switch (user.getUserType()) {
			case EMPLOYEE -> 0.3;
			case AFFILIATE -> 0.1;
			case CUSTOMER -> (user.getCustomerTenure() > 2) ? 0.05 : 0;
			default -> 0;
		};
		return getTotalBill(nonGroceryAmount, groceryAmount, discountRate);
	}

	private static double getTotalBill(double nonGroceryAmount, double groceryAmount, double discountRate) {
		double totalBill = (nonGroceryAmount * (1 - discountRate)) + groceryAmount;
		int multiple = (int) (totalBill / 100);
		totalBill -= multiple * 5;
		return totalBill;
	}
}