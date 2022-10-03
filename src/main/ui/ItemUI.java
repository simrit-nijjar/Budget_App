package ui;

import model.Budget;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

public abstract class ItemUI {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    ActionListener actionListener;
    JPanel itemPanel = new JPanel();

    JPanel itemListView;
    JPanel buttonOptionPanel;

    Budget budget;

    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    JButton viewButton;
    JButton backButton;

    String activeOption = "";

    // REQUIRES: str must be one of main buttons labels
    // EFFECTS: generates buttons according to specification
    void initializeButtons(String str) {
        addButton = new JButton("Add " + str);
        editButton = new JButton("Edit " + str);
        deleteButton = new JButton("Delete " + str);
        viewButton = new JButton(("View " + str));
        backButton = new JButton("Back");

        addButton.setActionCommand("add");
        editButton.setActionCommand("edit");
        deleteButton.setActionCommand("delete");
        viewButton.setActionCommand("view");
        backButton.setActionCommand("back");
    }

    abstract void init();

    abstract JPanel generateButtons();

    abstract JPanel generateListView();

    abstract Item getItemFromIndex(String actionCommand);

    // EFFECTS: returns the main Panel for class
    protected JPanel getPanel() {
        return this.itemPanel;
    }

    // MODIFIES: this
    // EFFECTS: refreshes components in panel
    protected void refresh() {
        buttonOptionPanel = generateButtons();
        itemListView = generateListView();
        itemPanel.updateUI();
    }

    // MODIFIES: this
    // EFFECTS: resets panel to original state
    protected void reset() {
        refresh();
        itemPanel.removeAll();
        itemPanel.add(backButton, BorderLayout.NORTH);
        itemPanel.add(buttonOptionPanel, BorderLayout.SOUTH);
        itemPanel.add(itemListView, BorderLayout.CENTER);
        itemPanel.updateUI();
    }

    // REQUIRES: txt must not be empty
    // EFFECTS: Creates header for panel
    protected void header(JPanel panel, String txt) {
        panel.setBorder(BorderFactory.createTitledBorder(txt));
    }
}
