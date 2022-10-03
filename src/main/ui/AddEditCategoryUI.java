package ui;

import exception.BelowZeroAmountException;
import exception.EmptyStringException;
import model.Budget;
import model.BudgetCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditCategoryUI extends AddEditBudgetItemUI {

    JLabel hasGoalLabel;
    JToggleButton hasGoalToggleButton;

    JLabel goalLabel;
    JFormattedTextField goalField;

    public AddEditCategoryUI(BudgetCategory activeCategory, Budget budget, ActionListener actionListener) {
        super(activeCategory, budget, actionListener);

    }

    void updateItem() {
        category.setName(name);
        if (hasGoal) {
            category.setHasGoalTrue(goal);
        } else {
            category.setHasGoalFalse();
        }
    }

    @Override
    void initializeFields() {
        nameField = new JFormattedTextField();
        amountField = new JFormattedTextField(amountFieldFormatter);

        amountLabel.setText("Current Amount: ");
        amountField.setEditable(false);
        amountField.setText(currency.format(amount));

        hasGoalLabel = new JLabel("Enable Goal: ");
        hasGoalToggleButton = new JToggleButton("DISABLED", hasGoal);
        if (hasGoal) {
            hasGoalToggleButton.setText("ENABLED");
        }
        hasGoalToggleButton.setActionCommand("toggle");
        hasGoalToggleButton.addActionListener(this);

        goalLabel = new JLabel("Goal: ");
        goalField = new JFormattedTextField(amountFieldFormatter);

        if (!isNew) {
            nameField.setText(name);
            amountField.setText(amount.toString());
            goalField.setText(goal.toString());
        }
    }

    @Override
    JPanel generateFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        header(panel, "Fill in the fields below");

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(amountLabel);
        panel.add(amountField);

        panel.add(hasGoalLabel);
        panel.add(hasGoalToggleButton);

        panel.add(goalLabel);
        panel.add(goalField);

        if (!hasGoal) {
            goalField.setEditable(false);
        }

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: handles toggle behaviour of hasGoalToggleButton
    //          enables goalField if true
    //          disables goalField if false
    private void handleToggle(boolean selected) {
        if (selected) {
            hasGoalToggleButton.setText("ENABLED");
            goalField.setEditable(true);
        } else {
            hasGoalToggleButton.setText("DISABLED");
            goalField.setEditable(false);
            goalField.setText("");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            this.name = nameField.getText();

            try {
                if (hasGoal) {
                    this.goal = Double.parseDouble(goalField.getText());
                    checkBelowZeroAmount(goal);
                }
                checkEmptyString(name);

            } catch (EmptyStringException exc) {
                JOptionPane.showMessageDialog(this, "Invalid name! Please enter a non-empty value.");
                return;
            } catch (BelowZeroAmountException | NumberFormatException exc) {
                JOptionPane.showMessageDialog(this, "Invalid amount! Please enter a value above 0");
                return;
            }

            updateItem();
            JOptionPane.showMessageDialog(this, "Success!");
        } else if (e.getActionCommand().equals("toggle")) {
            this.hasGoal = ((AbstractButton) e.getSource()).isSelected();
            handleToggle(hasGoal);
        }
    }
}
