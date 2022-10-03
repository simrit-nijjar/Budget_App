package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ExpenseTest {

    Expense exp1;

    LocalDate date1;
    LocalDate date2;

    BudgetCategory bc1;
    BudgetCategory bc2;

    @BeforeEach
    public void setUp() {
        date1 = LocalDate.of(2022,1,1);
        date2 = LocalDate.of(2022,2,2);

        bc1 = new BudgetCategory("Category 1");
        bc2 = new BudgetCategory("Category 2");

        exp1 = new Expense("Expense 1", 1, date1, bc1);

    }

    @Test
    public void testConstructor() {
        assertEquals(exp1.getName(), "Expense 1");
        assertEquals(exp1.getAmount(), 1);
        assertEquals(exp1.getDate(), date1);
        assertEquals(exp1.getCategory(), bc1);
    }

    @Test
    public void testSetCategoryDifferentCategory() {
        bc1.addToCurrentAmount(1); // To adjust for the code that resides in Budget.java
        assertEquals(bc1.getCurrentAmount(), 1);
        exp1.setCategory(bc2);
        assertEquals(exp1.getCategory(),bc2);
        assertEquals(bc1.getCurrentAmount(), 0);
        assertEquals(bc2.getCurrentAmount(), 1);
    }

    @Test
    public void testSetCategorySameCategory() {
        bc1.addToCurrentAmount(1); // To adjust for the code that resides in Budget.java
        assertEquals(bc1.getCurrentAmount(), 1);
        exp1.setCategory(bc1);
        assertEquals(exp1.getCategory(),bc1);
        assertEquals(bc1.getCurrentAmount(), 1);
    }

    @Test
    public void testSetDate() {
        exp1.setDate(date2);
        assertEquals(exp1.getDate(), date2);
    }

    @Test
    public void testSetDescription() {
        exp1.setName("name 1 edit");
        assertEquals(exp1.getName(), "name 1 edit");
    }

    @Test
    public void testSetAmount() {
        exp1.setAmount(500);
        assertEquals(exp1.getAmount(), 500);
    }

    @Test
    public void testConfirmCreation() {
        assertEquals("Successfully added Expense 1 in Category 1 on 2022-01-01 for $1.00", exp1.confirmCreation());
    }
}
