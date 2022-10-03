package persistence;

import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected void checkIncome(Income income, String name, double amount, LocalDate date) {
        assertEquals(income.getName(), name);
        assertEquals(income.getAmount(), amount);
        assertEquals(income.getDate(), date);
    }

    protected void checkExpense(Expense expense, String name, double amount, LocalDate date, BudgetCategory category) {
        assertEquals(expense.getName(), name);
        assertEquals(expense.getAmount(), amount);
        assertEquals(expense.getDate(), date);
        assertEquals(expense.getCategory(), category);
    }

    protected void checkCategory(BudgetCategory category, String name, double goal, double currentAmount, boolean hasGoal, boolean isOverGoal) {
        assertEquals(category.getName(), name);
        assertEquals(category.getGoal(), goal);
        assertEquals(category.getCurrentAmount(), currentAmount);
        assertEquals(category.hasGoal(), hasGoal);
        assertEquals(category.isOverGoal(), isOverGoal);
    }

    protected LocalDate makeDate(String date) {
        return LocalDate.parse(date, formatter);
    }
}
