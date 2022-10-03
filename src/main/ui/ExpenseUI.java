package ui;

import model.Budget;
import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExpenseUI extends ItemUI implements ActionListener {

    Expense activeExpense;

    public ExpenseUI(ActionListener actionListener, Budget budget) {
        this.actionListener = actionListener;
        this.budget = budget;

        init();
    }

    @Override
    protected void init() {
        initializeButtons("Expense");
        itemListView = generateListView();
        buttonOptionPanel = generateButtons();

        itemPanel.setLayout(new BorderLayout());
        itemPanel.setMinimumSize(new Dimension(MyBudgetGUI.WIDTH, MyBudgetGUI.HEIGHT));
        header(itemPanel, "Income");

        itemPanel.add(backButton, BorderLayout.NORTH);
        itemPanel.add(buttonOptionPanel, BorderLayout.SOUTH);
        itemPanel.add(new JScrollPane(itemListView), BorderLayout.CENTER);
    }

    // EFFECTS: generates buttons according to specification
    protected JPanel generateButtons() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3,0));

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(actionListener);

        return panel;
    }

    @Override
    protected JPanel generateListView() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,1));
        JButton listItem;
        ArrayList<Expense> expenseList = (ArrayList<Expense>) budget.getExpenseList();

        for (int i = 0; i < expenseList.size(); i++) {
            Expense expense = expenseList.get(i);

            listItem = new JButton(expense.getName() + " - " + expense.getDate()
                    + " - " + currencyFormatter.format(expense.getAmount()) + " - " + expense.getCategory().getName());
            listItem.setActionCommand("index" + i);
            listItem.addActionListener(this);
            listItem.setPreferredSize(new Dimension(MyBudgetGUI.WIDTH - 40, 25));
            panel.add(listItem);
        }

        return panel;
    }

    // EFFECTS: returns item from list based on index provided in the actionCommand
    @Override
    protected Expense getItemFromIndex(String actionCommand) {
        int index = Integer.parseInt(actionCommand.substring(5));

        return budget.getExpenseList().get(index);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.contains("index")) {
            activeExpense = getItemFromIndex(command);
            if (activeOption == "edit") {
                itemPanel.removeAll();

                itemPanel.add(new AddEditExpenseUI(activeExpense, budget, this));
                itemPanel.updateUI();
            } else if (activeOption == "delete") {
                budget.deleteExpense(activeExpense);
                reset();
            }
        }

        if (command.equals("add")) {
            itemPanel.removeAll();
            itemPanel.add(new AddEditExpenseUI(budget, this));
            itemPanel.updateUI();
        } else if (command.equals("edit")) {
            activeOption = "edit";
        } else if (command.equals("delete")) {
            activeOption = "delete";
        } else if (command.equals("back")) {
            reset();
        }
    }
}
