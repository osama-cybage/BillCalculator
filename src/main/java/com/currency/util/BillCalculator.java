package com.currency.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.currency.model.Item;
import com.currency.model.ItemCategory;
import com.currency.model.User;
import org.springframework.stereotype.Component;

@Component
public class BillCalculator {

	public Double calculate(List<Item> items, User user) {

		Map<Boolean, Double> amounts = items.stream().collect(
				Collectors.partitioningBy(
						item -> item.category().name().equals(ItemCategory.GROCERY.name()),
						Collectors.summingDouble(item -> item.price() * item.quantity())
				)
		);

		double discountRate = switch (user.getUserType()) {
			case EMPLOYEE -> 0.3;
			case AFFILIATE -> 0.1;
			case CUSTOMER -> (user.getCustomerTenure() > 2) ? 0.05 : 0;
			default -> 0;
		};
		return getTotalBill(amounts.get(false), amounts.get(true), discountRate);
	}

	private double getTotalBill(double nonGroceryAmount, double groceryAmount, double discountRate) {
		double totalBill = (nonGroceryAmount * (1 - discountRate)) + groceryAmount;
		int multiple = (int) (totalBill / 100);
		totalBill -= multiple * 5;
		return totalBill;
	}
}