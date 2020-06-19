package com.example.receiptstoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //initializing variables from xml file
    private Button TaxPayer;
    private Button Accountant;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing variables for firebase authorization
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //assigning xml ids to variables
        TaxPayer = (Button)findViewById(R.id.btnTaxPayer);
        Accountant = (Button)findViewById(R.id.btnAccountant);

        //when TaxPayer button is pressed, go to the TaxPayerLogin.java file
        TaxPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaxPayerLogin.class));
            }
        });

        //when Accountant button is pressed, go to the AccountantLoginPage.java file
        Accountant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountantLoginPage.class));
            }
        });

    }
}
