package com.currency.util;

import static org.junit.jupiter.api.Assertions.*;

import com.currency.model.Item;
import com.currency.model.ItemCategory;
import com.currency.model.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BillCalculatorTest {

    @Test
    public void testCalculate_WithEmployeeDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User customerType = User.EMPLOYEE;
        Integer customerTenure = 1;

        Double total = BillCalculator.calculate(items, customerType, customerTenure);

        assertEquals(525.0, total, 0.01);
    }

    @Test
    public void testCalculate_WithAffiliateDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User customerType = User.AFFILIATE;
        Integer customerTenure = 1;

        Double total = BillCalculator.calculate(items, customerType, customerTenure);

        assertEquals(620.0, total, 0.01);
    }

    @Test
    public void testCalculate_WithCustomerDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User customerType = User.CUSTOMER;
        Integer customerTenure = 3;

        Double total = BillCalculator.calculate(items, customerType, customerTenure);

        assertEquals(645.0, total, 0.01);
    }

    @Test
    public void testCalculate_NoDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User customerType = User.CUSTOMER;
        Integer customerTenure = 1; // Less than 2 years

        Double total = BillCalculator.calculate(items, customerType, customerTenure);

        assertEquals(665.0, total, 0.01); // No discount applied
    }
}