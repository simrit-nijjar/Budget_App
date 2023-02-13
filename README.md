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

## TODO
- Refactor Budget.java to reduce duplicate code and use BudgetItem in place of Income and Expense
- Refactor BudgetCategory.java into an abstract function and have a dedicated class for Expense Categories that extends BudgetCategory.
- Refactor to use Observers and Observables to decrease amount of parameters passed between UI Classes (eg: Budget.java).
- Refactor UI classes to use Item.java interface to reduce repetitive code regarding Income.java, Expense.java, BudgetCategory.Java
- Refactor CategoryList in Budget.java to use a HashMap instead.
- Refactor all Collections within Budget.java to use a separate class to handle lists instead for easier internal changes.
