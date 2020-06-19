package com.example.receiptstoringapp;

public class Person {
    protected String fullName;
    protected String email;
    protected String password;

    /*
    Construction function for new Person object

    @param fullName - person's full name
    @param email - person's email
    @param password - person's password

     */

    public Person(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }


    /*
    Sets the full name of the person

    @param fullName - person's full name

     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /*
    Sets the email of the person

    @param email - person's email

     */

    public void setEmail(String email) {
        this.email = email;
    }

    /*
    Sets the password of the person

    @param password - person's password

     */
    public void setPassword(String password) {
        this.password = password;
    }

    /*
    Returns the full name of the person

    @returns the full name of the person as a string
     */

    public String getFullName() {
        return fullName;
    }

    /*
    Returns the email of the person

    @returns the email of the person as a string
     */

    public String getEmail() {
        return email;
    }

    /*
    Returns the password of the person

    @returns the password of the person as a string
     */

    public String getPassword() {
        return password;
    }
}
