package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class BudgetTest {

    Budget budget;

    LocalDate date1;
    LocalDate date2;
    LocalDate date3;

    Expense exp1;
    Expense exp2;
    Expense exp3;
    Expense exp4;

    Income inc1;
    Income inc2;
    Income inc3;
    Income inc4;

    BudgetCategory bc1;
    BudgetCategory bc2;
    BudgetCategory bc3;

    @BeforeEach
    public void setUp() {
        date1 = LocalDate.of(2022,1,1);
        date2 = LocalDate.of(2022,1,2);
        date3 = LocalDate.of(2022,1,3);

        bc1 = new BudgetCategory("Category 1");
        bc2 = new BudgetCategory("Category 2");
        bc2.setHasGoalTrue(1);
        bc3 = new BudgetCategory("Category 3");
        bc3.setHasGoalTrue(100);

        exp1 = new Expense("Expense 1", 1, date1, bc1);
        exp2 = new Expense("Expense 2", 10, date2, bc2);
        exp3 = new Expense("Expense 3", 100, date3, bc3);
        exp4 = new Expense("Expense 4", 100, date2, bc1);

        inc1 = new Income("Income 1", 1, date1);
        inc2 = new Income("Income 2", 10, date2);
        inc3 = new Income("Income 3", 100, date3);
        inc4 = new Income("Income 4", 100, date2);

        budget = new Budget();
    }

    @Test
    public void testConstructor() {
        assertEquals(budget.getNumberOfExpenses(), 0);
        assertEquals(budget.getNumberOfIncomes(), 0);
        assertEquals(budget.getNumberOfCategories(), 0);
        assertEquals(budget.getTotalIncome(), 0);
        assertEquals(budget.getTotalExpense(), 0);
    }

    @Test
    public void testAddExpenseOnce() {
        budget.addExpense(exp1);
        assertEquals(budget.getNumberOfExpenses(), 1);
        assertEquals(budget.getExpenseList().get(0), exp1);
        assertEquals(budget.getExpenseList().get(0).getCategory().getCurrentAmount(), 1);
        assertEquals(budget.getTotalExpense(), 1);
    }

    @Test
    public void testAddExpenseSameExpenseTwice() {
        budget.addExpense(exp1);
        budget.addExpense(exp1);
        assertEquals(budget.getNumberOfExpenses(), 1);
        assertEquals(budget.getExpenseList().get(0), exp1);
        assertEquals(budget.getExpenseList().get(0).getCategory().getCurrentAmount(), 1);
        assertEquals(budget.getTotalExpense(), 1);
    }

    @Test
    public void testAddExpenseMultipleExpense() {
        budget.addExpense(exp1);
        budget.addExpense(exp4);
        assertEquals(budget.getNumberOfExpenses(), 2);
        assertEquals(budget.getExpenseList().get(0), exp1);
        assertEquals(budget.getExpenseList().get(1), exp4);
        assertEquals(budget.getExpenseList().get(0).getCategory().getCurrentAmount(), 101);
        assertEquals(budget.getTotalExpense(), 101);
    }

    @Test
    public void testEditExpenseName() {
        budget.addExpense(exp1);
        assertEquals(exp1.getName(), "Expense 1");
        budget.editExpenseName(exp1, "Expense 1 Edit");
        assertEquals(budget.getExpenseList().get(0).getName(), "Expense 1 Edit");
    }

    @Test
    public void editExpenseAmount() {
        budget.addExpense(exp1);
        assertEquals(exp1.getAmount(), 1);
        budget.editExpenseAmount(exp1, 100);
        assertEquals(exp1.getAmount(), 100);
    }

    @Test
    public void testEditExpenseDate() {
        budget.addExpense(exp1);
        assertEquals(exp1.getDate(), date1);
        budget.editExpenseDate(exp1, date2);
        assertEquals(exp1.getDate(), date2);
    }

    @Test
    public void testEditExpenseCategory() {
        budget.addExpense(exp1);
        assertEquals(exp1.getCategory(), bc1);
        budget.editExpenseCategory(exp1, bc2);
        assertEquals(exp1.getCategory(), bc2);
    }

    @Test
    public void testDeleteExpenseInBudget() {
        budget.addExpense(exp1);

        budget.deleteExpense(exp1);
        assertEquals(budget.getNumberOfExpenses(), 0);
        assertEquals(exp1.getCategory().getCurrentAmount(), 0);
        assertEquals(budget.getTotalExpense(), 0);
    }

    @Test
    public void testDeleteExpenseNotInBudget() {
        budget.deleteExpense(exp1);
        assertEquals(budget.getNumberOfExpenses(), 0);
        assertEquals(exp1.getCategory().getCurrentAmount(), 0);
        assertEquals(budget.getTotalExpense(), 0);
    }

    @Test
    public void testAddIncomeOnce() {
        budget.addIncome(inc1);
        assertEquals(budget.getIncomeList().size(), 1);
        assertEquals(budget.getTotalIncome(), 1);
    }

    @Test
    public void testAddIncomeSameIncomeTwice() {
        budget.addIncome(inc1);
        budget.addIncome(inc1);
        assertEquals(budget.getIncomeList().size(), 1);
        assertEquals(budget.getTotalIncome(), 1);
    }

    @Test
    public void testAddIncomeMultipleExpense() {
        budget.addIncome(inc1);
        budget.addIncome(inc4);
        assertEquals(budget.getIncomeList().size(), 2);
        assertEquals(budget.getTotalIncome(), 101);
    }

    @Test
    public void testEditIncomeDescription() {
        budget.addIncome(inc1);
        assertEquals(inc1.getName(), "Income 1");
        budget.editIncomeDescription(inc1, "Income 1 edit");
        assertEquals(inc1.getName(), "Income 1 edit");
    }

    @Test
    public void testEditIncomeAmount() {
        budget.addIncome(inc1);
        assertEquals(inc1.getAmount(), 1);
        budget.editIncomeAmount(inc1, 100.0);
        assertEquals(inc1.getAmount(), 100);
    }

    @Test
    public void testEditIncomeDate() {
        budget.addIncome(inc1);
        assertEquals(inc1.getDate(), date1);
        budget.editIncomeDate(inc1, date2);
        assertEquals(inc1.getDate(), date2);
    }

    @Test
    public void testDeleteIncomeInBudget() {
        budget.addIncome(inc1);

        budget.deleteIncome(inc1);
        assertEquals(budget.getIncomeList().size(), 0);
        assertEquals(budget.getTotalIncome(), 0);
    }

    @Test
    public void testDeleteIncomeNotInBudget() {
        budget.deleteIncome(inc1);
        assertEquals(budget.getIncomeList().size(), 0);
        assertEquals(budget.getTotalIncome(), 0);
    }

    @Test
    public void testAddCategoryOnce() {
        budget.addCategory(bc1);

        assertEquals(1, budget.getCategoryList().size());
        assertEquals(bc1, budget.getCategoryList().get(0));
    }

    @Test
    public void testAddCategorySameCategoryTwice() {
        budget.addCategory(bc1);
        budget.addCategory(bc1);

        assertEquals(1, budget.getCategoryList().size());
        assertEquals(bc1, budget.getCategoryList().get(0));
    }

    @Test
    public void testAddCategoryMultipleCategory() {
        budget.addCategory(bc1);
        budget.addCategory(bc2);

        assertEquals(2, budget.getCategoryList().size());
        assertEquals(bc1, budget.getCategoryList().get(0));
        assertEquals(bc2, budget.getCategoryList().get(1));
    }

    @Test
    public void testEditCategoryName() {
        budget.addCategory(bc1);
        budget.editCategoryName(bc1, "edit");

        assertEquals("edit", budget.getCategoryList().get(0).getName());
    }

    @Test
    public void testEditCategoryHasGoalTrueAlreadyTrue() {
        budget.addCategory(bc2);
        budget.editCategoryHasGoalTrue(bc2, 50);

        assertTrue(bc2.hasGoal());
        assertEquals(1, bc2.getGoal());
    }

    @Test
    public void testEditCategoryHasGoalTrueValidGoal() {
        budget.addCategory(bc1);
        budget.editCategoryHasGoalTrue(bc1, 50);

        assertTrue(bc1.hasGoal());
        assertEquals(50, bc1.getGoal());
    }

    @Test
    public void testEditCategoryHasGoalTrueNotValidGoal() {
        budget.addCategory(bc1);
        budget.editCategoryHasGoalTrue(bc1, 0);

        assertFalse(bc1.hasGoal());
        assertEquals(0, bc1.getGoal());
    }

    @Test
    public void testEditCategoryHasGoalFalseAlreadyFalse() {
        budget.addCategory(bc1);
        budget.editCategoryHasGoalFalse(bc1);

        assertFalse(bc1.hasGoal());
        assertEquals(0, bc1.getGoal());
    }

    @Test
    public void testEditCategoryHasGoalFalseNotFalse() {
        budget.addCategory(bc2);
        budget.editCategoryHasGoalFalse(bc2);

        assertFalse(bc2.hasGoal());
        assertEquals(0, bc2.getGoal());
    }

    @Test
    public void testEditCategoryGoalValidGoal() {
        budget.addCategory(bc2);
        budget.editCategoryGoal(bc2, 50);

        assertEquals(50, bc2.getGoal());
    }

    @Test
    public void testEditCategoryGoalNotValidGoal() {
        budget.addCategory(bc2);
        budget.editCategoryGoal(bc2, -50);

        assertEquals(1, bc2.getGoal());
    }

    @Test
    public void testDeleteCategory() {
        budget.addCategory(bc1);
        budget.deleteCategory(bc1);

        assertEquals(0, budget.getNumberOfCategories());
    }
}