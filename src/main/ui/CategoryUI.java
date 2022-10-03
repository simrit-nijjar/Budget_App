package ui;

import model.Budget;
import model.BudgetCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CategoryUI extends ItemUI implements ActionListener {

    BudgetCategory activeCategory;

    public CategoryUI(ActionListener actionListener, Budget budget) {
        this.actionListener = actionListener;
        this.budget = budget;
        init();
    }

    @Override
    protected void init() {
        initializeButtons("Category");
        itemListView = generateListView();
        buttonOptionPanel = generateButtons();

        itemPanel.setLayout(new BorderLayout());
        itemPanel.setMinimumSize(new Dimension(MyBudgetGUI.WIDTH, MyBudgetGUI.HEIGHT));
        header(itemPanel, "Category");

        itemPanel.add(backButton, BorderLayout.NORTH);
        itemPanel.add(buttonOptionPanel, BorderLayout.SOUTH);
        itemPanel.add(new JScrollPane(itemListView), BorderLayout.CENTER);
    }

    // EFFECTS: generates buttons according to specification
    protected JPanel generateButtons() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(2,1));

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(actionListener);

        return panel;
    }

    @Override
    protected JPanel generateListView() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));
        JButton listItem;
        ArrayList<BudgetCategory> categoryList = budget.getCategoryList();

        for (int i = 0; i < categoryList.size(); i++) {
            BudgetCategory category = categoryList.get(i);

            listItem = new JButton(category.printDetails());
            listItem.setActionCommand("index" + i);
            listItem.addActionListener(this);
            listItem.setPreferredSize(new Dimension(MyBudgetGUI.WIDTH - 40, 25));
            panel.add(listItem);
        }

        return panel;
    }

    @Override
    protected BudgetCategory getItemFromIndex(String actionCommand) {
        int index = Integer.parseInt(actionCommand.substring(5));
        return budget.getCategoryList().get(index);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.contains("index")) {
            activeCategory = getItemFromIndex(command);
            if (activeOption == "edit") {
                itemPanel.removeAll();
                itemPanel.add(new AddEditCategoryUI(activeCategory, budget, this));
                itemPanel.updateUI();
            } else if (activeOption == "delete") {
                budget.deleteCategory(activeCategory);
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
