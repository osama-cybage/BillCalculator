package com.currency.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.currency.model.Item;
import com.currency.model.ItemCategory;
import com.currency.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillCalculator {

	private static final Logger log = LoggerFactory.getLogger(BillCalculator.class);

	public static Double calculate(List<Item> items, User customerType, Integer customerTenure) {
		
		Map<Boolean, List<Item>> map = items.stream().collect(Collectors.partitioningBy(item -> item.category().name().equals(ItemCategory.GROCERY.name())));
		List<Item> groceryItems = map.get(true);
		List<Item> nonGroceryItems =  map.get(false);
		
		double groceryAmount = groceryItems.stream().mapToDouble(item -> item.price() * item.quantity()).sum();
		
		double nonGroceryAmount = nonGroceryItems.stream().mapToDouble(item -> item.price() * item.quantity()).sum();

		double totalBill;
		if(customerType.name().equals(User.EMPLOYEE.name())) {
			totalBill = (nonGroceryAmount - (0.3 * nonGroceryAmount)) + groceryAmount;
		} else if(customerType.name().equals(User.AFFILIATE.name())) {
			totalBill = (nonGroceryAmount - (0.1 * nonGroceryAmount)) + groceryAmount;
		} else if(customerType.name().equals(User.CUSTOMER.name()) && customerTenure > 2) {
			totalBill = (nonGroceryAmount - (0.05 * nonGroceryAmount)) + groceryAmount;
		} else {
			totalBill = nonGroceryAmount + groceryAmount;
		}
		int multiple = (int) (totalBill / 100);
		totalBill -= multiple * 5;
		return totalBill;
	}
}