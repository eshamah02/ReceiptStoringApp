package com.example.receiptstoringapp;

import java.util.ArrayList;
import java.util.Objects;

public class TaxPayer extends Person {
    protected String studentId;
    protected String accessCode;
    protected ArrayList<Receipt> receipts;

    /*
    Constructer function for new TaxPayer object

    @param studentID - user ID
    @param accessCode - access code that tp can give to their accountant to allow the viewing of the receipts
    @param receipts - the receipts that are submitted into the database
     */

    public TaxPayer(String studentId, String fullName, String email, String password, String accessCode, ArrayList<Receipt> receipts) {
        super(fullName, email, password);
        this.studentId = studentId;
        this.accessCode = accessCode;
        this.receipts = receipts;
    }

    /*
    Gets the student's id

    @returns students studentID as a string
     */

    public String getStudentId() {
        return studentId;
    }

    /*
    Gets the student's accessCodd

    @returns students access code as a string
     */

    public String getAccessCode() {
        return accessCode;
    }
}
