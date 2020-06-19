package com.example.receiptstoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class TaxPayerRegistration extends AppCompatActivity {

    private EditText TPFullNameRegister;
    private EditText TPEmailRegister;
    private EditText TPPasswordRegister;
    private EditText TPAccessCodeRegister;
    private ImageButton TPRegisterBack;
    private Button TPRegister;
    private TextView TPLoginHere;
    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //assigning xml item ids to variables
        TPFullNameRegister = (EditText)findViewById(R.id.etTPFullNameRegister);
        TPEmailRegister = (EditText)findViewById(R.id.etTPEmailRegister);
        TPPasswordRegister = (EditText)findViewById(R.id.etTPPasswordRegister);
        TPAccessCodeRegister = (EditText)findViewById(R.id.etTPAccessCodeRegister);
        TPRegisterBack = (ImageButton)findViewById(R.id.ibtnTPRegisterBack);
        TPRegister = (Button)findViewById(R.id.btnTPRegister);
        TPLoginHere = (TextView)findViewById(R.id.tvTPLoginHere);

        //when register button is pressed, call validate. if validate returns true, create new user and add tax payer info to realitime database
        TPRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    final String tpEmail = TPEmailRegister.getText().toString().trim();
                    String tpPassword = TPPasswordRegister.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(tpEmail, tpPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                addTaxPayerInfo();
                                Toast.makeText(TaxPayerRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TaxPayerRegistration.this, TaxPayerLogin.class));
                            } else {
                                Toast.makeText(TaxPayerRegistration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //if login here text is clicked, go to TaxPayerLogin java file
        TPLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerRegistration.this, TaxPayerLogin.class));
            }
        });

        //if back image button is clicked, go to MainActivity java file
        TPRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerRegistration.this, MainActivity.class));
            }
        });
    }

    /*
    Validates that all fields are filled out

    @returns boolean value true if all fields are filled out and boolean value false if they are not
     */

    private Boolean validate() {
        Boolean result = false;

        String tpFullName = TPFullNameRegister.getText().toString().trim();
        String tpEmail = TPEmailRegister.getText().toString().trim();
        String tpPassword = TPPasswordRegister.getText().toString().trim();
        String tpAccessCode = TPAccessCodeRegister.getText().toString().trim();

        if (tpFullName.isEmpty() || tpEmail.isEmpty() || tpPassword.isEmpty() || tpAccessCode.isEmpty()) {
            Toast.makeText(this, "Please enter all information.", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    /*
    Adds taxpayer info to realtime database after validate returns true
     */

    private void addTaxPayerInfo() {

        String tpFullName = TPFullNameRegister.getText().toString().trim();
        String tpEmail = TPEmailRegister.getText().toString().trim();
        String tpPassword = TPPasswordRegister.getText().toString().trim();
        String tpAccessCode = TPAccessCodeRegister.getText().toString().trim();

        String id = databaseReference.push().getKey();
        TaxPayer taxPayer = new TaxPayer(id, tpFullName, tpEmail, tpPassword, tpAccessCode, null);

        databaseReference.child("taxpayers").child(id).setValue(taxPayer);

    }

}