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

<img width="712" alt="Screenshot 2023-02-25 at 11 35 00 PM" src="https://user-images.githubusercontent.com/32626992/221398147-55efc8a7-487b-4275-92c5-3f26cae14383.png">

<img width="712" alt="Screenshot 2023-02-25 at 11 35 03 PM" src="https://user-images.githubusercontent.com/32626992/221398175-c5c4a40f-e83f-4ca6-8255-b6d1e68acb9f.png">

<img width="712" alt="Screenshot 2023-02-25 at 11 35 07 PM" src="https://user-images.githubusercontent.com/32626992/221398182-af17afcf-90b3-4988-9e13-6982a3bc77b7.png">


<img width="712" alt="Screenshot 2023-02-25 at 11 35 26 PM" src="https://user-images.githubusercontent.com/32626992/221398166-9fad1996-b2dc-454d-8eb6-bd807cc6923b.png">


<img width="712" alt="Screenshot 2023-02-25 at 11 35 22 PM" src="https://user-images.githubusercontent.com/32626992/221398198-a3a97b23-f294-48cb-924d-048f0faa0e90.png">

<img width="712" alt="Screenshot 2023-02-25 at 11 35 30 PM" src="https://user-images.githubusercontent.com/32626992/221398205-ff6128fb-7593-45ae-95ec-cb3b7bd8a85c.png">




