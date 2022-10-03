package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class MyBudgetGUI extends JFrame {
    public static final int HEIGHT = 400;
    public static final int WIDTH = 600;

    private MainMenuUI mainMenuUI;
    private LogoUI logoUI;

    private Budget budget;

    public MyBudgetGUI() {
        super("Budget");
        init();
        add(logoUI);
        pack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return;
        }
        remove(logoUI);
        add(mainMenuUI);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: initialized the JFrame and all JPanels
    public void init() {
        this.setVisible(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        budget = new Budget();

        // >>>> USED FOR TESTING <<<<
        // >>>> NOTE TO GRADER: feel free to comment out for easier testing <<<<
        testingInitiator();

        mainMenuUI = new MainMenuUI(budget);
        logoUI = new LogoUI();
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString() + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a full budget for testing
    private void testingInitiator() {
        budget.addCategory(new BudgetCategory("Entertainment"));
        budget.addCategory(new BudgetCategory("Shopping"));
        budget.addCategory(new BudgetCategory("Health & Fitness"));
        budget.addCategory(new BudgetCategory("Food & Dining"));
        budget.addCategory(new BudgetCategory("Investment"));
        budget.addCategory(new BudgetCategory("Fees & Charges"));
        budget.addCategory(new BudgetCategory("Taxes"));
        budget.addCategory(new BudgetCategory("Education"));
        budget.addCategory(new BudgetCategory("Personal Care"));
        budget.addCategory(new BudgetCategory("Travel"));
        budget.addCategory(new BudgetCategory("Business Services"));

        LocalDate date1 = LocalDate.of(2022,1,1);
        budget.addIncome(new Income("Income 1", 1, date1));
        budget.addIncome(new Income("Income 2", 1, date1));
        budget.addIncome(new Income("Income 4", 1, date1));
        budget.addIncome(new Income("Income 5", 1, date1));
        budget.addExpense(new Expense("Expense 1", 1, date1, budget.getCategoryList().get(0)));
    }

}
