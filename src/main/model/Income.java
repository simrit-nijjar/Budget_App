package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.NumberFormat;
import java.time.LocalDate;

// A class representing an income stream with a description, occurrence rate, date, amount, etc.
public class Income extends BudgetItem implements Writable {

    public Income(String name, double amount, LocalDate date) {
        super(name, amount, date);
    }

    public String confirmCreation() {
        return "Successfully added " + this.name + " on " + this.date + " for " + currency.format(this.amount);
    }

}
