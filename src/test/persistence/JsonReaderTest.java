
// CREDIT: JsonSerializationDemo - CPSC210 - UBC

package persistence;

import model.Budget;
import model.BudgetCategory;
import model.Expense;
import model.Income;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Budget budget = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBudget.json");
        try {
            Budget budget = reader.read();
            assertEquals(0, budget.getNumberOfIncomes());
            assertEquals(0, budget.getNumberOfExpenses());
            assertEquals(0, budget.getNumberOfCategories());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudget.json");

        try {
            Budget budget = reader.read();

            List<Income> incomeList = budget.getIncomeList();
            List<Expense> expenseList = budget.getExpenseList();
            List<BudgetCategory> categoryList = budget.getCategoryList();

            assertEquals(2, incomeList.size());
            assertEquals(2, expenseList.size());
            assertEquals(2, categoryList.size());

            BudgetCategory bc1 = categoryList.get(0);
            BudgetCategory bc2 = categoryList.get(1);

            checkIncome(incomeList.get(0), "Income 1", 1, makeDate("2022-01-01"));
            checkIncome(incomeList.get(1), "Income 2", 2, makeDate("2022-04-17"));

            checkExpense(expenseList.get(0), "Expense 1", 1, makeDate("2022-01-01"), bc1);
            checkExpense(expenseList.get(1), "Expense 2", 100, makeDate("2002-05-15"), bc2);

            checkCategory(categoryList.get(0), "Category 1", 0, 1, false, false);
            checkCategory(categoryList.get(1), "Category 2", 50, 100, true, true);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
