# My Personal Project - Budget App

## Functionality

This program will run as a simple budgeting application that helps to make planning and tracking expenses easier for the user.

A user will be able to add different types of **expenses** and **incomes** while setting goals and limits for how they want to use their money.

Further functionality will be added as the project progresses.

## User Demographic

The target audience for this project are users who have any sort of income stream with regularly occurring expenses they wish to keep track of, such as:

- *Groceries*
- *Shopping*
- *Subscriptions*

## Project Inspiration

This project was created simply for a single reason. I want to be able to budget but do not enjoy using any of the numerous paid apps or bland Excel sheets. Instead, I've decided that creating my own program with the ability to change features depending on my own preferences is far superior.

## User Stories

- As a user, I want to be able to add my income to my budget.
- As a user, I want to be able to add my expense to my budget.
- As a user, I want to be able to categorise my expenses based on their type of purchase in my budget.
- As a user, I want to be able to edit an expense or income in my budget.
- As a user, I want to be able to delete an expense or income from my budget.
- As a user, I want to be able to automatically save my budget on quitting my program.
- As a user, I want to be able to automatically load my budget on running my program.

## Instructions for Grader

- You can generate the first required event by clicking either 'Income' or 'Expense' and clicking 'ADD'. You can then fill in the blanks with valid inputs and click 'SUBMIT' to add the new item to the Budget.
- You can generate the second required event by clicking either 'Income' or 'Expense' and clicking 'DELETE'. You can then select any of the available items listed and they will be deleted.
- You can locate my visual component by starting the application and viewing the splash screen in the beginning 3 seconds.
- You can save the state of my application by clicking 'SAVE' in the MainMenu.
- You can reload the state of my application by clicking 'LOAD' in the MainMenu.
  - <b>NOTE FOR GRADER:</b> When loading, you may have to click 'BACK' and reenter the menu for changes to take effect. (not enough time to debug that) Sorry and thank you!

## Phase 4: Task 2

Thu Aug 11 14:03:15 PDT 2022 <br>
Added Income 'Income 1' to Budget

Thu Aug 11 14:03:15 PDT 2022 <br>
Added Income 'Income 2' to Budget

Thu Aug 11 14:03:15 PDT 2022 <br>
Added Income 'Income 4' to Budget

Thu Aug 11 14:03:15 PDT 2022 <br>
Added Income 'Income 5' to Budget

Thu Aug 11 14:03:15 PDT 2022 <br>
Added Expense 'Expense 1' to Budget

Thu Aug 11 14:03:19 PDT 2022 <br>
Deleted Income 'Income 1' from Budget

Thu Aug 11 14:03:19 PDT 2022 <br>
Deleted Income 'Income 4' from Budget

Thu Aug 11 14:03:22 PDT 2022 <br>
Deleted Expense 'Expense 1' from Budget

<b>NOTE FOR GRADER:</b> All the 'adding' logs are created as the program runs from <i>MyBudgetGUI.testingInitiator</i>

## Phase 4: Task 3
- Refactor Budget.java to reduce duplicate code and use BudgetItem in place of Income and Expense
- Refactor BudgetCategory.java into an abstract function and have a dedicated class for Expense Categories that extends BudgetCategory.
- Refactor to use Observers and Observables to decrease amount of parameters passed between UI Classes (eg: Budget.java).
- Refactor UI classes to use Item.java interface to reduce repetitive code regarding Income.java, Expense.java, BudgetCategory.Java
- Refactor CategoryList in Budget.java to use a HashMap instead.
- Refactor all Collections within Budget.java to use a separate class to handle lists instead for easier internal changes.
  - <b>NOTE FOR GRADER:</b> The above line might not be refactoring, so I apologize in advance 