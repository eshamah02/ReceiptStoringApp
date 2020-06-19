package com.example.receiptstoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaxPayerSortReceipt extends AppCompatActivity {

    private Button sortByPurpose;
    private Button sortByPlace;

    private EditText purposeSearch;
    private EditText placeSearch;
    private ImageButton pressPurposeSearch;
    private ImageButton pressPlaceSearch;

    RecyclerView recyclerView;
    ReceiptAdapter adapter;

    List<Receipt> receiptList;

    FirebaseDatabase database;
    DatabaseReference reference;

    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer_sort_receipt);

        //assigning xml item ids to variables

        sortByPurpose = (Button)findViewById(R.id.btnSortByPurpose);
        sortByPlace = (Button)findViewById(R.id.btnSortByPlace);

        purposeSearch = (EditText)findViewById(R.id.editTextPurposeSearch);
        placeSearch = (EditText)findViewById(R.id.editTextPlaceSearch);
        pressPurposeSearch = (ImageButton)findViewById(R.id.ibtnPurposeSearch);
        pressPlaceSearch = (ImageButton)findViewById(R.id.ibtnPlaceSearch);

        //creating new array list of receipts
        receiptList = new ArrayList<>();

        //setting up recyclerView to list out the receipts
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setting up the receiptAdapter
        adapter = new ReceiptAdapter(this, receiptList);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("taxpayers" + currentUser + "receipts");
        reference.addListenerForSingleValueEvent(valueEventListener);

        //when sort by purpose button is clicked, query database and display receipts ordered by "purpose"
        sortByPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference("taxpayers" + currentUser + "receipts").orderByChild("purpose");
            }
        });

        //when sort by place button is clicked, query database and display receipts ordered by "place"
        sortByPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference("taxpayers" + currentUser + "receipts").orderByChild("place");
            }
        });

        //when search by purpose is clicked, query database and display receipts by purpose depending on what the editText purpose value is
        pressPurposeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference("taxpayers" + currentUser + "receipts").orderByChild("purpose").equalTo(String.valueOf(purposeSearch));
            }
        });

        //when search by place is clicked, query database and display receipts by place depending on what the editText place value is
        pressPlaceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference("taxpayers" + currentUser + "receipts").orderByChild("place").equalTo(String.valueOf(placeSearch));
            }
        });

    }

    /*
    Adds receipts to the receipt arrayList when the receipt is encountered in the database

     */

    ValueEventListener valueEventListener = new ValueEventListener() {
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
