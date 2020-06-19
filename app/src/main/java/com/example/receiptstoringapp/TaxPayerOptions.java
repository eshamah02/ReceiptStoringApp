package com.example.receiptstoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaxPayerOptions extends AppCompatActivity {

    private Button TPInputReceipt;
    private Button TPSearchReceipt;
    private Button TPSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer_options);

        //assigning xml item ids to variables
        TPInputReceipt = (Button)findViewById(R.id.btnTPInputReceipt);
        TPSearchReceipt = (Button)findViewById(R.id.btnTPSearchReceipt);
        TPSettings = (Button)findViewById(R.id.btnTPSettings);

        //when input receipt button is clicked, go to TaxPayerInputReceipt java file
        TPInputReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerOptions.this, TaxPayerInputReceipt.class));
            }
        });

        //when search receipt button is clicked, go to TaxPayerSortReceipt java file
        TPSearchReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerOptions.this, TaxPayerSortReceipt.class));
            }
        });

        //when settings button is clicked, go to _____________ java file
        TPSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new activity
            }
        });

    }
}
