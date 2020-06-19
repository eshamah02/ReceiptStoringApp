package com.example.receiptstoringapp;

import java.util.HashMap;
import java.util.Map;

public class Receipt {
    protected String purpose;
    protected String place;
    protected String date;
    protected String amount;

    /*
    Constructor function for new receipt objects

    @param purpose - purpose of the purchase
    @param place - place of the purchase (store name)
    @param date - date of the purchase
    @param amount - amount spent for purhcase

     */

    public Receipt(String purpose, String place, String date, String amount) {
        this.purpose = purpose;
        this.place = place;
        this.date = date;
        this.amount = amount;
    }

    /*
    Returns the purpose of the receipt

    @returns the purpose as a String
     */

    public String getPurpose() {
        return purpose;
    }

    /*
    Returns the place of the receipt

    @returns the place as a String
     */

    public String getPlace() {
        return place;
    }

    /*
    Returns the date of the receipt

    @returns the date as a String
     */

    public String getDate() {
        return date;
    }

    /*
    Returns the amount of the receipt

    @returns the amount as a String
     */

    public String getAmount() {
        return amount;
    }


    }

