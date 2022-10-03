package ui;

import model.Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class SummaryUI extends JPanel implements ActionListener {

    Budget budget;
    ActionListener actionListener;

    JButton backButton;

    JLabel totalIncomeLabel;
    JLabel totalIncomeValue = new JLabel();

    JLabel totalExpenseLabel;
    JLabel totalExpenseValue = new JLabel();

    JLabel totalNumberOfIncomeLabel;
    JLabel totalNumberOfIncomeValue = new JLabel();

    JLabel totalNumberOfExpenseLabel;
    JLabel totalNumberOfExpenseValue = new JLabel();

    NumberFormat currency = NumberFormat.getCurrencyInstance();


    public SummaryUI(ActionListener actionListener, Budget budget) {
        this.budget = budget;
        this.actionListener = actionListener;

        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        header(this, "Summary");

        initializeFields();
        initializeButtons();

        add(summaryPanel(), BorderLayout.CENTER);
        add(backButton, BorderLayout.NORTH);

    }

    private void initializeFields() {
        totalExpenseLabel = new JLabel("Total Expense: ");
        totalIncomeLabel = new JLabel("Total Income: ");

        totalNumberOfExpenseLabel = new JLabel("Total Number of Expenses: ");
        totalNumberOfIncomeLabel = new JLabel("Total Number of Incomes: ");

        totalIncomeValue.setText(currency.format(budget.getTotalIncome()));
        totalExpenseValue.setText(currency.format(budget.getTotalExpense()));

        totalNumberOfIncomeValue.setText(String.valueOf(budget.getNumberOfIncomes()));
        totalNumberOfExpenseValue.setText(String.valueOf(budget.getNumberOfExpenses()));
    }

    private void initializeButtons() {
        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(actionListener);
    }

    private JPanel summaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        panel.add(totalIncomeLabel);
        panel.add(totalIncomeValue);

        panel.add(totalExpenseLabel);
        panel.add(totalExpenseValue);

        panel.add(totalNumberOfIncomeLabel);
        panel.add(totalNumberOfIncomeValue);

        panel.add(totalNumberOfExpenseLabel);
        panel.add(totalNumberOfExpenseValue);

        return panel;
    }

    // REQUIRES: txt must not be empty
    // EFFECTS: Creates header for panel
    protected void header(JPanel panel, String txt) {
        panel.setBorder(BorderFactory.createTitledBorder(txt));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
