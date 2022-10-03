package ui;

import exception.BelowZeroAmountException;
import exception.EmptyStringException;
import exception.IllegalDateException;
import model.Budget;
import model.BudgetItem;
import model.Income;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddEditIncomeUI extends AddEditBudgetItemUI {

    Income income;

    public AddEditIncomeUI(BudgetItem income, Budget budget, ActionListener actionListener) {
        super(income, budget, actionListener);
        item = income;
        this.income = (Income) income;
    }

    public AddEditIncomeUI(Budget budget, ActionListener actionListener) {
        super(budget, actionListener);
        item = new Income("", 0, LocalDate.now());
    }

    void updateItem() {
        item.setName(name);
        item.setAmount(amount);
        item.setDate(date);

        if (!isNew) {
            budget.deleteIncome(income);
        }

        budget.addIncome(new Income(name, amount, date));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "submit") {
            this.name = nameField.getText();

            try {
                this.amount = Double.parseDouble(amountField.getText());
                this.date = LocalDate.parse(dateField.getText(), dateFormatter);
                checkEmptyString(name);
                checkBelowZeroAmount(amount);
                checkIllegalDate(date);
            } catch (EmptyStringException exc) {
                JOptionPane.showMessageDialog(this, "Invalid name! Please enter a non-empty value.");
                return;
            } catch (BelowZeroAmountException | NumberFormatException exc) {
                JOptionPane.showMessageDialog(this, "Invalid amount! Please enter a value above 0");
                return;
            } catch (IllegalDateException | DateTimeParseException exc) {
                JOptionPane.showMessageDialog(this, "Invalid date! Please try again.");
                return;
            }

            updateItem();
            JOptionPane.showMessageDialog(this, "Success!");
        }
    }

}
