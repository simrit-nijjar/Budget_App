package model;

import java.text.NumberFormat;

public interface Item {

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    String getName();

    void setName(String name);




}
