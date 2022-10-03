package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Objects;

public abstract class BudgetItem implements Item, Writable {

    String name;
    double amount;
    LocalDate date;
    BudgetCategory category;

    NumberFormat currency = NumberFormat.getCurrencyInstance();

    public BudgetItem(String name, Double amount, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public BudgetItem(String name, Double amount, LocalDate date, BudgetCategory category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public BudgetCategory getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // REQUIRE: category must be different from this.category
    // MODIFIES: this
    // EFFECT: changes current category to given category and transfers amount to new category
    public void setCategory(BudgetCategory newCategory) {
        if (newCategory != category) {
            category.subtractFromCurrentAmount(amount);
            newCategory.addToCurrentAmount(amount);
            category = newCategory;
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", amount);
        json.put("date", date);
        return json;
    }
}
