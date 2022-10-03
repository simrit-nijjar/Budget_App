
// CREDIT: JsonSerializationDemo - CPSC210 - UBC

package persistence;

import model.Budget;
import model.BudgetCategory;
import model.Expense;
import model.Income;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFileName() {
        try {
            Budget budget = new Budget();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Budget budget = new Budget();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
            budget = reader.read();
            assertEquals(0, budget.getNumberOfIncomes());
            assertEquals(0, budget.getNumberOfExpenses());
            assertEquals(0, budget.getNumberOfCategories());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Budget budget = new Budget();
            LocalDate date1 = makeDate("2022-04-05");
            BudgetCategory bc1 = new BudgetCategory("Category 1");
            Income income = new Income("Income 1", 10, date1);
            Expense expense = new Expense("Expense 1", 25, date1, bc1);

            budget.addCategory(bc1);
            budget.addIncome(income);
            budget.addExpense(expense);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudget.json");
            budget = reader.read();

            List<BudgetCategory> categoryList = budget.getCategoryList();
            List<Income> incomeList = budget.getIncomeList();
            List<Expense> expenseList = budget.getExpenseList();

            assertEquals(1, incomeList.size());
            assertEquals(1, expenseList.size());
            assertEquals(1, categoryList.size());

            checkCategory(categoryList.get(0), "Category 1", 0, 25, false, false);
            checkIncome(incomeList.get(0), "Income 1", 10, date1);
            checkExpense(expenseList.get(0), "Expense 1", 25, date1, categoryList.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
