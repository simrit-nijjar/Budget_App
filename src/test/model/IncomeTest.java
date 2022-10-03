package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeTest {
    Income inc1;

    LocalDate date1;

    BudgetCategory bc1;

    @BeforeEach
    public void setUp() {
        date1 = LocalDate.of(2022,1,1);

        bc1 = new BudgetCategory("Category 1");

        inc1 = new Income("Income 1", 1, date1);

    }

    @Test
    public void testConstructor() {
        assertEquals(inc1.getName(), "Income 1");
        assertEquals(inc1.getAmount(), 1);
        assertEquals(inc1.getDate(), date1);
    }

    @Test
    public void testConfirmCreation() {
        assertEquals("Successfully added Income 1 on 2022-01-01 for $1.00", inc1.confirmCreation());
    }
}
