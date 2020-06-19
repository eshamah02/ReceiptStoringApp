package com.example.receiptstoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountantLoginPage extends AppCompatActivity {

    //initializing variables from xml file
    EditText clientFullName;
    EditText clientAccessCode;
    Button accountantViewReceipts;

    //initializing variables for firebase authentication and database
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //creating list of receipts
    List<Receipt> receiptList;

    //initializing variables for recycler view functions
    RecyclerView recyclerView;
    ReceiptAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountant_login_page);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //assigning xml ids to variables
        clientFullName = (EditText)findViewById(R.id.etAClientFullName);
        clientAccessCode = (EditText)findViewById(R.id.etAClientAccessCode);
        accountantViewReceipts = (Button)findViewById(R.id.btnAViewReceipts);

        String clientName = clientFullName.getText().toString().trim();
        String clientCode = clientAccessCode.getText().toString().trim();

        //creating array list of receipts
        receiptList = new ArrayList<>();

        //setting up recycler view layout
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReceiptAdapter(this, receiptList);
        recyclerView.setAdapter(adapter);

        //querying in realtime database for child nodes equal to the client name and client code
        Query queryName = databaseReference.child("taxpayers").orderByChild("fullName").equalTo(clientName);
        Query queryCode = databaseReference.child("taxpayers").orderByChild("accessCode").equalTo(clientCode);

        if (queryName == queryCode) {

            databaseReference = FirebaseDatabase.getInstance().getReference("taxpayers");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);

        } else {
            Toast.makeText(AccountantLoginPage.this, "Codes incorrect, therefore access not granted.", Toast.LENGTH_LONG).show();

        }

    }

    /*
    Accesses the parents of the child node (client name and client code) to get the uid of the user
     */

    ValueEventListener firstValueEventListener = new ValueEventListener() {
        public static final String TAG = "UID";

        @Override
        public void onDataChange(DataSnapshot snapshot) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                final String clientName = clientFullName.getText().toString().trim();
                String clientCode = clientAccessCode.getText().toString().trim();
                //querying in realtime database for child nodes equal to the client name and client code
                final Query queryName = databaseReference.child("taxpayers").orderByChild("fullName").equalTo(clientName);
                Query queryCode = databaseReference.child("taxpayers").orderByChild("accessCode").equalTo(clientCode);
                final String key = ds.getKey();
                Log.d(TAG, key);
                queryName.addListenerForSingleValueEvent(firstValueEventListener);
                queryCode.addListenerForSingleValueEvent(firstValueEventListener);

                accountantViewReceipts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Query query = FirebaseDatabase.getInstance().getReference("taxpayers", key, "receipts");
                    }
                });
            }
        }

        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    /*
    Adds receipts to the receipt arrayList when the receipt is encountered in the database

     */

    final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            receiptList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Receipt receipt = snapshot.getValue(Receipt.class);
                    receiptList.add(receipt);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

}

