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
import com.google.firebase.auth.FirebaseUser;

public class TaxPayerLogin extends AppCompatActivity {

    private EditText TPEmailLogin;
    private EditText TPPasswordLogin;
    private Button TPLogin;
    private TextView TPRegisterHere;
    private ImageButton TPLoginBack;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer_login);

        //assigning xml item ids to variables
        TPEmailLogin = (EditText)findViewById(R.id.etTPEmailLogin);
        TPPasswordLogin = (EditText)findViewById(R.id.etTPPasswordLogin);
        TPLogin = (Button)findViewById(R.id.btnTPLogin);
        TPRegisterHere = (TextView)findViewById(R.id.tvTPRegisterHere);
        TPLoginBack = (ImageButton)findViewById(R.id.ibtnTPLoginBack);

        firebaseAuth = FirebaseAuth.getInstance();

        //when login button is clicked, call validateTPLogin function
        TPLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateTPLogin(TPEmailLogin.getText().toString(), TPPasswordLogin.getText().toString());
            }
        });

        //when registerHere text is clicked, go to TaxPayerRegistration java file
        TPRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerLogin.this, TaxPayerRegistration.class));
            }
        });

        //when back image button is clicked, go to MainActivity java file
        TPLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerLogin.this, MainActivity.class));
            }
        });
    }

    /*
    Validates email and password from realtime database and either allows you to sign in or try again
     */
    private void validateTPLogin(String tpEmail, String tpPassword) {
        firebaseAuth.signInWithEmailAndPassword(tpEmail, tpPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(TaxPayerLogin.this, TaxPayerOptions.class));
                } else {
                    Toast.makeText(TaxPayerLogin.this, "Login failed. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

