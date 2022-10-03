package ui;

import exception.BelowZeroAmountException;
import exception.EmptyStringException;
import exception.IllegalDateException;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddEditExpenseUI extends AddEditBudgetItemUI {

    Expense expense;
    JLabel categoryLabel;
    JComboBox categoryDropDown;

    public AddEditExpenseUI(BudgetItem item, Budget budget, ActionListener actionListener) {
        super(item, budget, actionListener);
        this.expense = (Expense) this.item;
        this.category = item.getCategory();
    }

    public AddEditExpenseUI(Budget budget, ActionListener actionListener) {
        super(budget, actionListener);
        expense = new Expense("", 0, LocalDate.now(), new BudgetCategory(""));
    }

    // MODIFIES: this
    // EFFECTS: generates a list of category names and adds them to categoryDropDown
    protected void generateCategoryList(JComboBox jcb) {
        for (BudgetCategory category : budget.getCategoryList()) {
            jcb.addItem(category.getName());
        }
    }

    @Override
    void initializeFields() {
        super.initializeFields();

        categoryDropDown = new JComboBox();
        generateCategoryList(categoryDropDown);
        categoryDropDown.setBounds(80, 50, 140, 20);

        categoryLabel = new JLabel("Category: ");
    }

    @Override
    JPanel generateFields() {
        JPanel panel = super.generateFields();

        panel.add(categoryLabel);
        panel.add(categoryDropDown);

        return panel;
    }

    @Override
    void updateItem() {
        if (!isNew) {
            budget.deleteExpense(expense);
        }

        budget.addExpense(new Expense(name, amount, date, category));

        if (category.isOverGoal()) {
            JOptionPane.showMessageDialog(this, "You have exceeded your goal of "
                    + currency.format(category.getGoal()) + " for " + category.getName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "submit") {
            this.name = nameField.getText();
            this.category = budget.findCategory(
                    (String) categoryDropDown.getItemAt(categoryDropDown.getSelectedIndex()));

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
