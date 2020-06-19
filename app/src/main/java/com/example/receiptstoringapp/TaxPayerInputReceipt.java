package com.example.receiptstoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TaxPayerInputReceipt extends AppCompatActivity {

    //initializing variables for xml file
    private EditText TPPurposeInput;
    private EditText TPPlaceInput;
    private EditText TPDateInput;
    private EditText TPAmountInput;
    private Button TPSubmitInput;

    //initializing firebase database and authorization
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    //gets the userID of the current user
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer_input_receipt);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //assigning xml item ids to variables
        TPPurposeInput = (EditText)findViewById(R.id.etTPPurposeInput);
        TPPlaceInput = (EditText)findViewById(R.id.etTPPlaceInput);
        TPDateInput = (EditText)findViewById(R.id.etTPDateInput);
        TPAmountInput = (EditText)findViewById(R.id.etTPAmountInput);
        TPSubmitInput = (Button)findViewById(R.id.btnTPSubmitInput);

        TPSubmitInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate() == true) {
                    addReceiptInfo();
                } else {
                    Toast.makeText(TaxPayerInputReceipt.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*
    Adds receipt information to realtime database under user's id
     */

    protected void addReceiptInfo() {
        String tpPurposeInput = TPPurposeInput.getText().toString().trim();
        String tpPlaceInput = TPPlaceInput.getText().toString().trim();
        String tpDateInput = TPDateInput.getText().toString().trim();
        String tpAmountInput = TPAmountInput.getText().toString().trim();
        String userUID = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference reference = database.getReference("taxpayers" + currentUser + "receipts");
        String id = databaseReference.child(String.valueOf(reference)).push().getKey();

        Receipt receipt = new Receipt(tpPurposeInput, tpPlaceInput, tpDateInput, tpAmountInput);

        databaseReference.child(id).setValue(receipt);

    }

    /*
    Validates that all fields in xml file for receipt input are filled

    @returns allFieldsFilled as a boolean value (true if all fields are filled, false if they are not)
     */

    protected Boolean validate() {
        String tpPurposeInput = TPPurposeInput.getText().toString().trim();
        String tpPlaceInput = TPPlaceInput.getText().toString().trim();
        String tpDateInput = TPDateInput.getText().toString().trim();
        String tpAmountInput = TPAmountInput.getText().toString().trim();

        Boolean allFieldsFilled = false;

        if (!tpPurposeInput.equals("") && !tpPlaceInput.equals("") && !tpDateInput.equals("") && !tpAmountInput.equals("")) {
            allFieldsFilled = true;
            return allFieldsFilled;
        } else {
            return allFieldsFilled;
        }
    }

}
