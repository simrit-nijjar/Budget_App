package model;

import org.json.JSONObject;
import persistence.Writable;

// A class representing a category for an expense with name, limit, etc.
public class BudgetCategory implements Item, Writable {
    private String name;
    private double goal;
    private double currentAmount;
    private boolean hasGoal;
    private boolean isOverGoal;

    // EFFECT: initializes with given name and all variables at default settings (false, 0)
    public BudgetCategory(String name) {
        this.name = name;
        this.goal = 0;
        this.currentAmount = 0;
        this.hasGoal = false;
        this.isOverGoal = false;
    }

    // REQUIRES: newName must not be empty
    // MODIFIES: this
    // EFFECT: changes name of category
    public void setName(String newName) {
        if (!newName.isEmpty()) {
            this.name = newName;
        }
    }

    // REQUIRES: newGoal > 0 and hasGoal must be true
    // MODIFIES: this
    // EFFECT: changes goal of category
    public void setGoal(double newGoal) {
        if (hasGoal && newGoal > 0) {
            this.goal = newGoal;
        }
    }

    // REQUIRES: hasGoal == false
    // MODIFIES: this
    // EFFECT: sets hasGoal to true and sets goal to given amount
    public void setHasGoalTrue(double goal) {
        if (!hasGoal) {
            hasGoal = true;
            this.goal = goal;
        }
    }

    // REQUIRE: hasGoal == true
    // MODIFIES: this
    // EFFECT: sets hasGoal to false and sets goal to 0
    public void setHasGoalFalse() {
        if (hasGoal) {
            hasGoal = false;
            this.goal = 0;
        }
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECT: adds amount to currentAmount
    //         sets isOverGoal to true if currentAmount > goal
    public void addToCurrentAmount(double amount) {
        if (amount > 0) {
            this.currentAmount += amount;

            if (currentAmount > goal && hasGoal) {
                isOverGoal = true;
            }
        }
    }

    // REQUIRES: currentAmount - amount >= 0 and
    //           amount > 0
    // MODIFIES: this
    // EFFECT: subtracts amount from currentAmount
    //         sets isOverGoal to false if currentAmount <= goal
    public void subtractFromCurrentAmount(double amount) {
        if (amount > 0) {
            this.currentAmount -= amount;

            if (currentAmount <= goal && hasGoal) {
                isOverGoal = false;
            }
        }
    }

    public String printDetails() {
        String details = this.name;

        if (hasGoal) {
            details += " - " + currencyFormatter.format(this.goal);
        } else {
            details += " - No goal added";
        }

        return details;
    }

    // GETTERS

    public String getName() {
        return this.name;
    }

    public double getGoal() {
        return this.goal;
    }

    public double getCurrentAmount() {
        return this.currentAmount;
    }

    public Boolean hasGoal() {
        return this.hasGoal;
    }

    public Boolean isOverGoal() {
        return this.isOverGoal;
    }


    // CREDIT: Thingy.java - JsonSerializationDemo - CPSC210 - UBC
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: converts Category object to JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("goal", goal);
        json.put("currentAmount", currentAmount);
        json.put("hasGoal", hasGoal);
        return json;
    }
}
