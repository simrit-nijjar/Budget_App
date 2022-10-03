package ui;

import exception.*;
import model.Budget;
import model.BudgetCategory;
import model.BudgetItem;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class AddEditBudgetItemUI extends JPanel implements ActionListener {

    Budget budget;
    BudgetItem item;
    String name;
    Double amount;
    LocalDate date;

    BudgetCategory category;
    Double goal;
    boolean hasGoal;
    boolean isOverGoal;

    boolean isNew;
    ActionListener actionListener;

    JButton backButton = new JButton("Back");
    JButton submitButton = new JButton("Submit");

    JLabel nameLabel = new JLabel("Name: ");
    JLabel amountLabel = new JLabel("Amount: ");
    JLabel dateLabel = new JLabel("Date (yyyy-mm-dd): ");

    JFormattedTextField nameField;
    JFormattedTextField amountField;
    JFormattedTextField dateField;

    DateFormat dateFieldFormatter = new SimpleDateFormat("yyyy-mm-dd");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    NumberFormat amountFieldFormatter = new DecimalFormat(".00");
    NumberFormat currency = NumberFormat.getCurrencyInstance();

    public AddEditBudgetItemUI(Budget budget, ActionListener actionListener) {
        isNew = true;
        constructor(actionListener, budget);
    }

    public AddEditBudgetItemUI(BudgetItem item, Budget budget, ActionListener actionListener) {
        isNew = false;
        this.item = item;
        this.name = item.getName();
        this.date = item.getDate();
        this.amount = item.getAmount();

        constructor(actionListener, budget);
    }

    public AddEditBudgetItemUI(BudgetCategory category, Budget budget, ActionListener actionListener) {
        isNew = false;
        this.category = category;
        this.name = category.getName();
        this.goal = category.getGoal();
        this.amount = category.getCurrentAmount();
        this.isOverGoal = category.isOverGoal();
        this.hasGoal = category.hasGoal();

        constructor(actionListener, budget);
    }

    // MODIFIES: this
    // EFFECTS: runs the default constructor arguments
    protected void constructor(ActionListener actionListener, Budget budget) {
        this.actionListener = actionListener;
        this.budget = budget;

        init();
        updateUI();
    }

    // MODIFIES: this
    // EFFECTS: initiates addeditmenu
    void init() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        if (isNew) {
            header(this, "Add New Item");
        } else {
            header(this, "Edit " + name);
        }

        initializeFields();
        initializeButtons();

        add(generateFields(), BorderLayout.CENTER);
        add(backButton, BorderLayout.NORTH);
        add(submitButton, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initiates fields
    void initializeFields() {
        nameField = new JFormattedTextField();
        amountField = new JFormattedTextField(amountFieldFormatter);
        dateField = new JFormattedTextField(dateFieldFormatter);

        if (!isNew) {
            nameField.setText(name);
            amountField.setText(amount.toString());
            dateField.setText(date.toString());

        }
    }

    // MODIFIES: this
    // EFFECTS: initiates buttons
    void initializeButtons() {
        backButton.setActionCommand("back");
        backButton.addActionListener(actionListener);

        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        submitButton.addActionListener(actionListener);
    }

    // MODIFIES: this
    // EFFECTS: creates fields for input
    JPanel generateFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));
        header(panel, "Fill in the fields below");

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(amountLabel);
        panel.add(amountField);

        panel.add(dateLabel);
        panel.add(dateField);

        return panel;
    }

    abstract void updateItem();

    // REQUIRES: txt must not be empty
    // EFFECTS: Creates header for panel
    protected void header(JPanel panel, String txt) {
        panel.setBorder(BorderFactory.createTitledBorder(txt));
    }

    protected void checkEmptyString(String name) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException();
        }
    }

    protected void checkBelowZeroAmount(Double amount) throws BelowZeroAmountException {
        if (amount <= 0) {
            throw new BelowZeroAmountException();
        }
    }

    protected void checkIllegalDate(LocalDate date) throws IllegalDateException {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalDateException();
        }
    }

}
