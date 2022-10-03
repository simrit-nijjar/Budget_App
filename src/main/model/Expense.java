package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// A class representing an expense that would be added to a budget with a name, amount, and other relevant information.
public class Expense extends BudgetItem implements Writable {

    // REQUIRE: date must be before or equal to the current date
    public Expense(String name, double amount, LocalDate date, BudgetCategory category) {
        super(name, amount, date, category);
    }

    public String confirmCreation() {
        return "Successfully added " + this.name + " in " + this.category.getName()
                + " on " + this.date + " for " + currency.format(this.amount);
    }

    // CREDIT: Thingy.java - JsonSerializationDemo - CPSC210 - UBC
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("category", category.getName());

        return json;
    }
}
