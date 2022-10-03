package ui;

import exception.*;
import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MyBudgetConsole {
    private Budget budget;
    private Scanner input;
    boolean isEnabled = true;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/budget.json";

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    // EFFECTS: starts budget application
    public MyBudgetConsole() {
        runBudgetApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs, handles error exceptions
    private void runBudgetApp() {
        init();

        while (isEnabled) {
            displayMainMenu();

            try {
                processMainMenuCommand();
            } catch (NoItemsAvailableException e) {
                printError(4);
            }
        }

        saveBudget();
    }

    // EFFECTS: executes method corresponding with command
    private void processMainMenuCommand() throws NoItemsAvailableException {
        int command = chooseCommand(4);

        if (command == 0) {
            isEnabled = false;
        } else if (command == 1) {
            displayIncomeMainMenu();
            processIncomeMainMenuCommand();
        } else if (command == 2) {
            displayExpenseMainMenu();
            processExpenseMainMenuCommand();
        } else if (command == 3) {
            displayCategoryMainMenu();
            processCategoryMainMenuCommand();
        } else if (command == 4) {
            doShowSummary();
        } else {
            printError(-1);
        }
    }

    // EFFECTS: executes method corresponding with Income command
    private void processIncomeMainMenuCommand() throws NoItemsAvailableException {
        int command = chooseCommand(4);
        if (command == 0) {
            back();
        } else if (command == 1) {
            doAddIncome();
        } else if (command == 2) {
            doEditIncome();
        } else if (command == 3) {
            doDeleteIncome();
        } else if (command == 4) {
            displayIncomeItems();
        } else {
            printError(-1);
        }
    }

    // EFFECTS: executes method corresponding with Income command
    private void processExpenseMainMenuCommand() throws NoItemsAvailableException {
        int command = chooseCommand(4);
        if (command == 0) {
            back();
        } else if (command == 1) {
            doAddExpense();
        } else if (command == 2) {
            doEditExpense();
        } else if (command == 3) {
            doDeleteExpense();
        } else if (command == 4) {
            displayExpenseItems();
        } else {
            printError(-1);
        }
    }

    // EFFECTS: executes method corresponding with Income command
    private void processCategoryMainMenuCommand() throws NoItemsAvailableException {
        int command = chooseCommand(2);
        if (command == 0) {
            back();
        } else if (command == 1) {
            doEditCategories();
        } else if (command == 2) {
            displayCategoryItems();
        } else {
            printError(-1);
        }
    }

    // EFFECTS: adds income to budget based on user input
    private void doAddIncome() {
        Income income = interactAddIncomeMenu();

        budget.addIncome(income);
        System.out.println(income.confirmCreation());
        pressToContinue();
    }

    // MODIFIES: budget
    // EFFECTS: Adds expense to budget based on user input, informs user if they surpass goal
    private void doAddExpense() {
        Expense expense = interactAddExpenseMenu();

        budget.addExpense(expense);
        System.out.println(expense.confirmCreation());

        if (expense.getCategory().isOverGoal()) {
            System.out.println("You have surpassed your goal for " + expense.getCategory().getName());
        }

        pressToContinue();
    }

    // MODIFIES: budget
    // EFFECTS: deletes user selected income item, exception if no income in budget
    private void doDeleteIncome() throws NoItemsAvailableException {
        int command;

        checkNoAvailableItems(budget.getNumberOfIncomes());

        System.out.println("Select item to delete: ");
        displayIncomeItems();
        command = chooseCommand(budget.getNumberOfIncomes());

        Income income = budget.getIncomeList().get(command - 1);
        budget.deleteIncome(income);

        pressToContinue();
    }

    // MODIFIES: budget
    // EFFECTS: deletes user selected expense item, exception if no expense in budget
    private void doDeleteExpense() throws NoItemsAvailableException {
        int command;

        checkNoAvailableItems(budget.getNumberOfExpenses());

        System.out.println("Select item to delete: ");
        displayExpenseItems();
        command = chooseCommand(budget.getNumberOfExpenses());

        Expense expense = budget.getExpenseList().get(command - 1);
        budget.deleteExpense(expense);

        pressToContinue();
    }

    // MODIFIES: income
    // EFFECT: Edits user selected income in budget
    private void doEditIncome() throws NoItemsAvailableException {

        checkNoAvailableItems(budget.getNumberOfIncomes());

        System.out.println("Choose item to edit: ");
        displayIncomeItems();
        int command = chooseCommand(budget.getNumberOfExpenses());

        Income income = budget.getIncomeList().get(command - 1);

        System.out.println("Choose parameter to edit: ");
        displayEditOptions("income");
        command = chooseCommand(3);

        handleEditIncomeCommand(command, income);

        pressToContinue();
    }

    // MODIFIES: expense
    // EFFECT: Edits user selected expense in budget
    private void doEditExpense() throws NoItemsAvailableException {

        checkNoAvailableItems(budget.getNumberOfExpenses());

        System.out.println("Choose item to edit: ");
        displayExpenseItems();
        int command = chooseCommand(budget.getNumberOfExpenses());

        Expense expense = budget.getExpenseList().get(command - 1);

        System.out.println("Choose parameter to edit: ");
        displayEditOptions("expense");
        command = chooseCommand(4);

        try {
            handleEditExpenseCommand(command, expense);
        } finally {
            pressToContinue();
        }
    }

    // MODIFIES: Category
    // EFFECTS: edits user selected category
    private void doEditCategories() {
        int command;

        System.out.println("Choose item to edit:");
        displayCategoryItems();
        command = chooseCommand(budget.getNumberOfCategories());

        BudgetCategory category = budget.getCategoryList().get(command - 1);

        System.out.println("Choose parameter to edit: ");
        int topLimit = displayCategoryEditOptions(category);
        command = chooseCommand(topLimit);

        try {
            handleEditCategoryCommand(command, category, topLimit);
            System.out.println("Successfully edited category: " + category.printDetails());
        } finally {
            pressToContinue();
        }
    }

    // EFFECTS: prints summary of budget details
    private void doShowSummary() {
        System.out.println("Showing Summary of budget: \n");

        System.out.println("Total Income: $" + budget.getTotalIncome());
        System.out.println("Total Expenses: S" + budget.getTotalExpense() + "\n");

        System.out.println("Total number of Incomes to date: " + budget.getNumberOfIncomes());
        System.out.println("Total number of Expenses to date: " + budget.getNumberOfExpenses() + "\n");

        System.out.println("Current goal amounts: ");

        for (int i = 0; i < budget.getNumberOfCategories(); i++) {
            BudgetCategory category = budget.getCategoryList().get(i);
            if (category.hasGoal()) {
                System.out.println(category.getName() + " - Current Amount: $" + category.getCurrentAmount()
                        + " - Goal Amount: $ " + category.getGoal());
            }
        }

        pressToContinue();
    }

    // EFFECTS: returns a category selected by the user, throws exception if illegal command given
    private BudgetCategory selectCategory() {
        int command;

        displayCategoryItems();
        command = chooseCommand(budget.getNumberOfCategories());

        return budget.getCategoryList().get(command - 1);
    }

    // EFFECTS: displays user options on console
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Income");
        System.out.println("\t2 -> Expense");
        System.out.println("\t3 -> Categories");

        System.out.println("\t4 -> Show Summary");

        System.out.println("\t\n0 -> QUIT");
    }

    // EFFECTS: displays user options for income
    private void displayIncomeMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Add Income");
        System.out.println("\t2 -> Edit Income");
        System.out.println("\t3 -> Delete Income");
        System.out.println("\t4 -> View Incomes");

        System.out.println("\n\t0 -> Back");
    }

    // EFFECTS: displays user options for expense
    private void displayExpenseMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Add Expense");
        System.out.println("\t2 -> Edit Expense");
        System.out.println("\t3 -> Delete Expense");
        System.out.println("\t4 -> View Expense");

        System.out.println("\n\t0 -> Back");
    }

    // EFFECTS: displays user options for category
    private void displayCategoryMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Edit Category");
        System.out.println("\t2 -> View Expense");

        System.out.println("\n\t0 -> Back");
    }

    // EFFECTS: displays all items in budget.incomeList in a numbered list
    private void displayIncomeItems() {
        for (int i = 0; i < budget.getNumberOfIncomes(); i++) {
            String option = Integer.toString(i + 1);
            Income income = budget.getIncomeList().get(i);
            System.out.println(option + " -> " + income.getName() + " - " + income.getDate()
                    + " - " + currencyFormatter.format(income.getAmount()));
        }
    }

    // EFFECTS: displays all items in budget.expenseList in a numbered list
    private void displayExpenseItems() {
        for (int i = 0; i < budget.getNumberOfExpenses(); i++) {
            String option = Integer.toString(i + 1);
            Expense expense = budget.getExpenseList().get(i);
            System.out.println(option + " -> " + expense.getName() + ": " + expense.getDate()
                    + " - " + currencyFormatter.format(expense.getAmount()) + " - " + expense.getCategory().getName());
        }
    }

    // EFFECTS: displays all items in categoryList in a numbered list
    private void displayCategoryItems() {
        for (int i = 0; i < budget.getNumberOfCategories(); i++) {
            String option = Integer.toString(i + 1);
            System.out.println(option + " -> " + budget.getCategoryList().get(i).getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates new Income object from user input
    private Income interactAddIncomeMenu() {
        String name;
        Double amount;
        LocalDate date;

        System.out.print("Name: ");
        name = input.nextLine();

        System.out.print("Amount: ");
        amount = chooseAmount();

        date = chooseDate();

        return new Income(name, amount, date);
    }

    // MODIFIES: this
    // EFFECT: Creates new Expense Object from user input
    private Expense interactAddExpenseMenu() {
        String name;
        Double amount;
        LocalDate date;
        BudgetCategory category;

        System.out.print("\nName: ");
        name = chooseString();

        System.out.print("\nAmount: ");
        amount = chooseAmount();

        date = chooseDate();

        System.out.println("Choose category from below:");
        category = selectCategory();

        return new Expense(name, amount, date, category);
    }

    // EFFECTS: displays all options for user to edit depending on given item being edited
    private void displayEditOptions(String option) {
        System.out.println("Select from: \n");
        System.out.println("1 -> Name");
        System.out.println("2 -> Amount");
        System.out.println("3 -> Date");
        if (option == "expense") {
            System.out.println("4 -> Category");
        }
    }

    // EFFECTS: displays all options for user to edit for categories depending on current state of category
    private int displayCategoryEditOptions(BudgetCategory category) {
        System.out.println("Select from: \n");
        System.out.println("1 -> Name");

        if (category.hasGoal()) {
            System.out.println("2 -> Edit Goal");
            System.out.println("3 -> Remove Goal");
            return 3;
        } else {
            System.out.println("2 -> Add a Goal");
            return 2;
        }
    }

    // MODIFIES: income
    // EFFECT: edits an Income object based on user input
    private void handleEditIncomeCommand(int command, Income income) {
        if (command == 1) {
            String newName = chooseString();
            budget.editIncomeDescription(income, newName);

        } else if (command == 2) {
            Double newAmount = chooseAmount();
            budget.editIncomeAmount(income, newAmount);

        } else if (command == 3) {
            LocalDate date = chooseDate();
            budget.editIncomeDate(income, date);
        }
    }

    // MODIFIES: this, expense
    // EFFECT: edits an Expense object based on user input
    private void handleEditExpenseCommand(int command, Expense expense) {
        if (command == 1) {
            System.out.println("Enter new name:");
            String newName = chooseString();
            budget.editExpenseName(expense, newName);

        } else if (command == 2) {
            System.out.println("Enter new amount:");
            Double newAmount = chooseAmount();
            budget.editExpenseAmount(expense, newAmount);

        } else if (command == 3) {
            System.out.println("Enter new date:");
            LocalDate date = chooseDate();
            budget.editExpenseDate(expense, date);

        } else if (command == 4) {
            System.out.println("Enter new category");
            displayCategoryItems();
            int index = chooseCommand(budget.getNumberOfCategories());
            budget.editExpenseCategory(expense, budget.getCategoryList().get(index - 1));
        }
    }

    // MODIFIES: category
    // EFFECTS: edits a category's details based on user input
    private void handleEditCategoryCommand(int command, BudgetCategory category, int option) {
        if (command == 1) {
            System.out.print("Enter new name for category:");
            String newName = chooseString();
            budget.editCategoryName(category, newName);
        } else if (command == 2 && option == 2) {
            System.out.print("Select a goal: ");
            double goal = chooseGoal();
            budget.editCategoryHasGoalTrue(category, goal);
        } else if (command == 2 && option == 3) {
            System.out.print("Select a new goal: ");
            double goal = chooseGoal();
            budget.editCategoryGoal(category, goal);
        } else if (command == 3) {
            budget.editCategoryHasGoalFalse(category);
        }
    }

    // EFFECT: returns a valid String object based on user inputs
    private String chooseString() {
        Boolean valid = false;
        String str = "";

        while (!valid) {

            try {
                str = input.nextLine();
                checkEmptyString(str);
                valid = true;
            } catch (EmptyStringException e) {
                printError(1);
            }
        }

        return str;
    }

    // EFFECT: returns a valid Int object based on user inputs
    private int chooseGoal() {
        Boolean valid = false;
        int i = 0;

        while (!valid && i > 0) {
            valid = false;

            try {
                i = Integer.parseInt(input.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid goal, try again!");
            }
        }

        return i;
    }

    // EFFECT: returns a valid Double object based on user inputs
    private Double chooseAmount() {
        Boolean valid = false;
        Double d = 0.0;

        while (!valid) {
            try {
                d = Double.parseDouble(input.nextLine());
                checkBelowZeroAmount(d);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Amount, try again!");
            } catch (BelowZeroAmountException e) {
                printError(0);
            }
        }

        return d;
    }

    // EFFECT: returns a LocalDate object bases on user inputs
    private LocalDate chooseDate() {
        Boolean valid = false;
        LocalDate d = LocalDate.now();

        System.out.print("Choose a date (yyyy-mm-dd): ");

        while (!valid) {
            try {
                d = LocalDate.parse(input.nextLine(), dateFormatter);
                checkIllegalDate(d);
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date, try again!");
            } catch (IllegalDateException e) {
                printError(3);
            }
        }

        return d;
    }

    // EFFECT: returns a valid command bases on user inputs
    private int chooseCommand(int topLimit) {
        Boolean valid = false;
        int i = 0;

        while (!valid) {
            try {
                i = Integer.parseInt(input.nextLine());
                checkIllegalCommand(i, 0, topLimit);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Command, try again!");
            } catch (IllegalCommandException e) {
                printError(2);
            }
        }

        return i;
    }

    // EFFECTS: prints simple back message
    private void back() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(">>> GOING BACK <<<");
    }

    // EFFECTS: prints simple error message
    private void printError(int error) {
        if (error == 0) {
            System.out.println("BelowZeroException: Please enter a value above 0");
        } else if (error == 1) {
            System.out.println("Empty String Exception: Please enter a non-empty value");
        } else if (error == 2) {
            System.out.println("IllegalCommandException: Please only enter a valid command");
        } else if (error == 3) {
            System.out.println("IllegalDateException: Please enter a date before today");
        } else if (error == 4) {
            System.out.println("NoItemsAvailableException: Please add item to budget first");
        } else {
            System.out.println("\n\n\n>>> An error has occurred. Please try again. <<<\n");
        }
    }

    // EFFECTS: pauses program until user inputs any key
    private void pressToContinue() {
        System.out.println("\nPress any key to continue");
        input.nextLine();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
    }

    // EFFECTS: saves the workroom to file
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            System.out.println("Saved budget to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the workroom to file
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: initializes budget with all necessary categories, scanner, and category list
    private void init() {
        budget = new Budget();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        input = new Scanner(System.in);
        input.useDelimiter("\n");

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
        budget.addExpense(new Expense("Expense 1", 1, date1, budget.getCategoryList().get(0)));

        loadBudget();
    }


    // CHECK EXCEPTIONS

    private void checkIllegalCommand(int command, int a, int b) throws IllegalCommandException {
        if (command < a || command > b) {
            throw new IllegalCommandException();
        }
    }

    private void checkEmptyString(String name) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException();
        }
    }

    private void checkBelowZeroAmount(Double amount) throws BelowZeroAmountException {
        if (amount <= 0) {
            throw new BelowZeroAmountException();
        }
    }

    private void checkIllegalDate(LocalDate date) throws IllegalDateException {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalDateException();
        }
    }

    private void checkNoAvailableItems(int items) throws NoItemsAvailableException {
        if (items == 0) {
            throw new NoItemsAvailableException();
        }
    }

}