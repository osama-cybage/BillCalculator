package com.currency.util;

import static org.junit.jupiter.api.Assertions.*;

import com.currency.model.Item;
import com.currency.model.ItemCategory;
import com.currency.model.User;
import com.currency.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class BillCalculatorTest {

    @InjectMocks
    BillCalculator billCalculator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculate_WithEmployeeDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User user = new User(1, UserType.EMPLOYEE);

        Double total = billCalculator.calculate(items, user);

        assertEquals(525.0, total, 0.01);
    }

    @Test
    public void testCalculate_WithAffiliateDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User user = new User(1, UserType.AFFILIATE);

        Double total = billCalculator.calculate(items, user);

        assertEquals(620.0, total, 0.01);
    }

    @Test
    public void testCalculate_WithCustomerDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User user = new User(3, UserType.CUSTOMER);


        Double total = billCalculator.calculate(items, user);

        assertEquals(645.0, total, 0.01);
    }

    @Test
    public void testCalculate_NoDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Item1", ItemCategory.OTHERS, 200.0, 1),
                new Item("Item2", ItemCategory.OTHERS, 300.0, 1),
                new Item("Item3", ItemCategory.GROCERY, 100.0, 2)
        );
        User user = new User(1, UserType.CUSTOMER);


        Double total = billCalculator.calculate(items, user);

        assertEquals(665.0, total, 0.01);
    }
}