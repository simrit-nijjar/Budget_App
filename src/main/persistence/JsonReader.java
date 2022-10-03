package persistence;

// CREDIT: JsonSerializationDemo - CPSC210 - UBC
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Budget;
import model.BudgetCategory;
import model.Expense;
import model.Income;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class JsonReader {
    private final String source;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads budget from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudget(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        Budget budget = new Budget();


        JSONArray jsonCategoryList = jsonObject.getJSONArray("categoryList");
        JSONArray jsonIncomeList = jsonObject.getJSONArray("incomeList");
        JSONArray jsonExpenseList = jsonObject.getJSONArray("expenseList");

        addCategoryList(budget, jsonCategoryList);
        addIncomeList(budget, jsonIncomeList);
        addExpenseList(budget, jsonExpenseList);
        return budget;
    }

    // MODIFIES: budget
    // EFFECTS: parses list of incomes from JSON array and adds them to budget
    private void addIncomeList(Budget budget, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextIncome = (JSONObject) json;
            addIncome(budget, nextIncome);
        }
    }

    // MODIFIES: budget
    // EFFECTS: parses list of categories from JSON array and adds them to budget
    private void addExpenseList(Budget budget, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(budget, nextExpense);
        }
    }

    // MODIFIES: budget
    // EFFECTS: parses list of expenses from JSON array and adds them to budget
    private void addCategoryList(Budget budget, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(budget, nextCategory);
        }
    }


    // MODIFIES: budget
    // EFFECTS: parses income from JSON object and adds it to budget
    private void addIncome(Budget budget, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("amount");
        LocalDate date = LocalDate.parse(jsonObject.getString("date"), dateFormatter);
        Income income = new Income(name, amount, date);
        budget.addIncome(income);
    }

    // MODIFIES: budget
    // EFFECTS: parses income from JSON object and adds it to budget
    private void addExpense(Budget budget, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("amount");
        LocalDate date = LocalDate.parse(jsonObject.getString("date"), dateFormatter);
        BudgetCategory category = budget.findCategory(jsonObject.getString("category"));
        Expense expense = new Expense(name, amount, date, category);
        budget.addExpense(expense);
    }

    // MODIFIES: budget
    // EFFECTS: parses category from JSON object and adds it to budget
    private void addCategory(Budget budget, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double goal = jsonObject.getDouble("goal");
        double currentAmount = jsonObject.getDouble("currentAmount");
        Boolean hasGoal = jsonObject.getBoolean("hasGoal");
        BudgetCategory category = new BudgetCategory(name);
        if (hasGoal) {
            category.setHasGoalTrue(goal);
        }
        budget.addCategory(category);
    }
}
