package ui;

import model.Budget;
import model.Income;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IncomeUI extends ItemUI implements ActionListener {

    Income activeIncome;

    public IncomeUI(ActionListener actionListener, Budget budget) {
        this.actionListener = actionListener;
        this.budget = budget;

        init();
    }

    protected void init() {
        initializeButtons("Income");
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
        ArrayList<Income> incomeList = budget.getIncomeList();

        for (int i = 0; i < incomeList.size(); i++) {
            Income income = incomeList.get(i);

            listItem = new JButton(income.getName() + " - " + income.getDate()
                    + " - " + currencyFormatter.format(income.getAmount()));
            listItem.setActionCommand("index" + i);
            listItem.addActionListener(this);
            listItem.setPreferredSize(new Dimension(MyBudgetGUI.WIDTH - 40, 25));
            panel.add(listItem);
        }

        return panel;
    }

    // EFFECTS: returns item from list based on index provided in the actionCommand
    @Override
    protected Income getItemFromIndex(String actionCommand) {
        int index = Integer.parseInt(actionCommand.substring(5));
        return budget.getIncomeList().get(index);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.contains("index")) {
            activeIncome = getItemFromIndex(command);
            if (activeOption == "edit") {
                itemPanel.removeAll();
                itemPanel.add(new AddEditIncomeUI(activeIncome, budget, this));
                itemPanel.updateUI();
            } else if (activeOption == "delete") {
                budget.deleteIncome(activeIncome);
                reset();
            }
        }

        if (command.equals("add")) {
            itemPanel.removeAll();
            itemPanel.add(new AddEditIncomeUI(budget, this));
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
