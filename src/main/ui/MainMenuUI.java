package ui;

import model.Budget;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenuUI extends JPanel implements ActionListener {

    private Budget budget;

    private ItemUI incomeUI;
    private ItemUI expenseUI;
    private ItemUI categoryUI;
    private SummaryUI summaryUI;

    private JButton incomeButton;
    private JButton expenseButton;
    private JButton categoryButton;
    private JButton summaryButton;
    
    private JButton saveButton;
    private JButton loadButton;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/budget.json";


    public MainMenuUI(Budget budget) {
        this.budget = budget;
        this.setLayout(new BorderLayout());
        init();

        add(viewMainMenuOptions());
        updateUI();
    }

    public JPanel viewMainMenuOptions() {
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        header(panel,"Choose an option to expand:");

        panel.add(mainPagesButtons(), BorderLayout.CENTER);
        panel.add(saveLoadButtons(), BorderLayout.SOUTH);

        addMainEventHandlers();

        return panel;
    }

    private JPanel mainPagesButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,4));

        panel.add(incomeButton);
        panel.add(expenseButton);
        panel.add(categoryButton);
        panel.add(summaryButton);

        return panel;
    }

    // REQUIRES: txt must not be empty
    // EFFECTS: Creates header for panel
    public void header(JPanel panel, String txt) {
        panel.setBorder(BorderFactory.createTitledBorder(txt));
    }

    // EFFECTS: initializes all components of MainMenu
    private void init() {
        incomeButton = new JButton("Income");
        expenseButton = new JButton("Expense");
        categoryButton = new JButton("Category");
        summaryButton = new JButton("Summary");
        saveButton = new JButton("SAVE");
        loadButton = new JButton("LOAD");

        incomeUI = new IncomeUI(this::actionPerformed, budget);
        expenseUI = new ExpenseUI(this::actionPerformed, budget);
        categoryUI = new CategoryUI(this::actionPerformed, budget);
        summaryUI = new SummaryUI(this::actionPerformed, budget);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: initializes event handlers for main buttons
    public void addMainEventHandlers() {
        incomeButton.addActionListener(this);
        incomeButton.setActionCommand("income");

        expenseButton.addActionListener(this);
        expenseButton.setActionCommand("expense");

        categoryButton.addActionListener(this);
        categoryButton.setActionCommand("category");

        summaryButton.addActionListener(this);
        summaryButton.setActionCommand("summary");
        
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        
        loadButton.addActionListener(this);
        loadButton.setActionCommand("load");
    }

    private void reset() {
        removeAll();
        init();
        updateUI();
    }

    private void nextPage(JPanel panel) {
        removeAll();
        add(panel);
        updateUI();
    }

    private JPanel saveLoadButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2));
        panel.add(saveButton);
        panel.add(loadButton);

        return panel;
    }

    // EFFECTS: saves the workroom to file
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved to budget!!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save Budget.");
        } finally {
            updateUI();
        }
    }

    // EFFECTS: saves the workroom to file
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Loaded budget from" + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        } finally {
            updateUI();
        }
    }

    // EFFECTS: handles internal class actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            reset();
            add(viewMainMenuOptions());
            updateUI();
        } else if (e.getActionCommand().equals("income")) {
            nextPage(incomeUI.getPanel());
        } else if (e.getActionCommand().equals("expense")) {
            nextPage(expenseUI.getPanel());
        } else if (e.getActionCommand().equals("category")) {
            nextPage(categoryUI.getPanel());
        } else if (e.getActionCommand().equals("summary")) {
            nextPage(summaryUI);
        } else if (e.getActionCommand().equals("save")) {
            saveBudget();
        } else if (e.getActionCommand().equals("load")) {
            loadBudget();
        }
    }
}
