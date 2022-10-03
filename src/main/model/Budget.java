package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// A class that represents a budget with all the features listed in the User Story section of the README.md
public class Budget implements Writable {
    private final Collection<Expense> expenseList;
    private final Collection<Income> incomeList;
    private final Collection<BudgetCategory> categoryList;

    private double totalExpense;
    private double totalIncome;

    // EFFECTS: initialized Budget with empty expenseList, incomeList
    public Budget() {
        expenseList = new ArrayList<>();
        incomeList = new ArrayList<>();
        categoryList = new ArrayList<>();
    }

    // REQUIRES: must not already be in incomeList
    // MODIFIES: this, BudgetCategory
    // EFFECTS: Add new expense to expenseList and
    //          increase BudgetCategory's currentAmount by expense amount and
    //          increase totalExpense by expense amount
    public void addExpense(Expense expense) {
        if (!expenseList.contains(expense)) {
            double amount = expense.getAmount();
            expenseList.add(expense);
            expense.getCategory().addToCurrentAmount(amount);
            totalExpense += amount;

            EventLog.getInstance().logEvent(new Event("Added Expense '" + expense.getName() + "' to Budget"));
        }
    }

    // REQUIRES: must already be in expenseList, name can not be empty
    // MODIFIES: expense
    // EFFECTS: edits an expense's name
    public void editExpenseName(Expense expense, String name) {
        expense.setName(name);
    }

    // REQUIRES: must already be in expenseList, amount > 0
    // MODIFIES: expense
    // EFFECTS: edits an expense's amount
    public void editExpenseAmount(Expense expense, double amount) {
        expense.setAmount(amount);
    }

    // REQUIRES: must already be in expenseList, date must be before or equal to current date
    // MODIFIES: expense
    // EFFECTS: edits an expense's date
    public void editExpenseDate(Expense expense, LocalDate date) {
        expense.setDate(date);
    }

    // REQUIRES: must already be in expenseList
    // MODIFIES: expense
    // EFFECTS: edits an expense's category
    public void editExpenseCategory(Expense expense, BudgetCategory category) {
        expense.setCategory(category);
    }

    // REQUIRES: must already be in expenseList
    // MODIFIES: this, BudgetCategory
    // EFFECTS: deletes an expense currently in expenseList and
    //          decreases BudgetCategory's currentAmount by expense amount and
    //          decreases totalExpense by expense amount
    public void deleteExpense(Expense expense) {
        if (expenseList.contains(expense)) {
            double amount = expense.getAmount();

            expenseList.remove(expense);
            expense.getCategory().subtractFromCurrentAmount(amount);
            totalExpense -= amount;

            EventLog.getInstance().logEvent(new Event("Deleted Expense '" + expense.getName() + "' from Budget"));
        }
    }

    // REQUIRES: must not already be in incomeList
    // MODIFIES: this
    // EFFECTS: Add new income to incomeList and
    //          increase totalIncome by income amount
    public void addIncome(Income income) {
        if (!incomeList.contains(income)) {
            double amount = income.getAmount();

            incomeList.add(income);
            totalIncome += amount;

            EventLog.getInstance().logEvent(new Event("Added Income '" + income.getName() + "' to Budget"));
        }
    }

    // REQUIRES: income must be in incomeList, description can not be empty
    // MODIFIES: income
    // EFFECT: edits an income's name
    public void editIncomeDescription(Income income, String description) {
        income.setName(description);
    }

    // REQUIRES: income must be in incomeList, amount > 0
    // MODIFIES: income
    // EFFECT: edits an income's amount
    public void editIncomeAmount(Income income, Double amount) {
        income.setAmount(amount);
    }

    // REQUIRES: income must be in incomeList, date must be before or equal to today's date
    // MODIFIES: income
    // EFFECT: edits an income's date
    public void editIncomeDate(Income income, LocalDate date) {
        income.setDate(date);
    }

    // REQUIRES: must already be in incomeList
    // MODIFIES: this
    // EFFECTS: deletes an income currently in incomeList and
    //          decrease totalIncome by income amount
    public void deleteIncome(Income income) {
        if (incomeList.contains(income)) {
            double amount = income.getAmount();

            incomeList.remove(income);
            totalIncome -= amount;

            EventLog.getInstance().logEvent(new Event("Deleted Income '" + income.getName() + "' from Budget"));
        }
    }

    // REQUIRES: must not already be in categoryList
    // MODIFIES: this
    // EFFECTS: Add new category to categoryList
    public void addCategory(BudgetCategory category) {
        if (!categoryList.contains(category)) {
            categoryList.add(category);
        }
    }

    // REQUIRES: must be in categoryList
    // MODIFIES: this
    // EFFECTS: edits category name to newName
    public void editCategoryName(BudgetCategory category, String newName) {
        if (categoryList.contains(category)) {
            category.setName(newName);
        }
    }

    // REQUIRES: must be in categoryList, hasGoal == true, newGoal > 0
    // MODIFIES: this
    // EFFECTS: edits category goal to newGoal
    public void editCategoryGoal(BudgetCategory category, double newGoal) {
        if (categoryList.contains(category) && category.hasGoal()) {
            category.setGoal(newGoal);
        }
    }

    // REQUIRES: must be in categoryList, hasGoal == false
    // MODIFIES: this
    // EFFECTS: sets category's hasGoal to true and sets goal to entered amount
    public void editCategoryHasGoalTrue(BudgetCategory category, double goal) {
        if (categoryList.contains(category) && !category.hasGoal() && goal > 0) {
            category.setHasGoalTrue(goal);
        }
    }

    // REQUIRES: must be in categoryList, hasGoal == true
    // MODIFIES: this
    // EFFECTS: sets category's hasGoal to false
    public void editCategoryHasGoalFalse(BudgetCategory category) {
        if (categoryList.contains(category) && category.hasGoal()) {
            category.setHasGoalFalse();
        }
    }

    // REQUIRES: must be in categoryList
    // MODIFIES: this
    // EFFECTS: deletes category from categoryList
    public void deleteCategory(BudgetCategory category) {
        categoryList.remove(category);
    }

    // EFFECTS: returns number of expenses currently in the list
    public int getNumberOfExpenses() {
        return expenseList.size();
    }

    // EFFECTS: returns number of incomes currently in the list
    public int getNumberOfIncomes() {
        return incomeList.size();
    }

    // EFFECTS: returns number of categories currently in the list
    public int getNumberOfCategories() {
        return categoryList.size();
    }

    public BudgetCategory findCategory(String name) {
        for (BudgetCategory category : categoryList) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    // GETTERS

    public ArrayList<Expense> getExpenseList() {
        return (ArrayList<Expense>) expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return (ArrayList<Income>) incomeList;
    }

    public ArrayList<BudgetCategory> getCategoryList() {
        return (ArrayList<BudgetCategory>) categoryList;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    // CREDIT: WorkRoom.java - JsonSerializationDemo - CPSC210 - UBC
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: returns Budget as JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("incomeList", incomeListToJson());
        json.put("expenseList", expenseListToJson());
        json.put("categoryList", categoryListToJson());
        return json;
    }

    // EFFECTS: returns income in this budget as a JSON array
    private JSONArray incomeListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Income income : incomeList) {
            jsonArray.put(income.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns expense in this budget as a JSON array
    private JSONArray expenseListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense expense : expenseList) {
            jsonArray.put(expense.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns category in this budget as a JSON array
    private JSONArray categoryListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BudgetCategory category : categoryList) {
            jsonArray.put(category.toJson());
        }

        return jsonArray;
    }
}
