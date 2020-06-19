package com.example.receiptstoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class TaxPayerSettings extends AppCompatActivity {

    private ImageButton backFromSettings;
    private Button logOut;
    private Button deleteAccount;
    private TextView allInfo;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //applying xml item ids to variables
        backFromSettings = (ImageButton)findViewById(R.id.ibtnTPBackFromSettings);
        logOut = (Button)findViewById(R.id.btnTPLogOut);
        deleteAccount = (Button)findViewById(R.id.btnTPDeleteAccount);
        allInfo = (TextView)findViewById(R.id.tvAllInfo);

        String name = user.getDisplayName();
        String email = user.getEmail();

        //setting allInfo TextVie to display user's full name and email
        allInfo.setText("Name: " + name + "Email: " + email);

        //if back image button is pressed, go to TaxPayerOptions java file
        backFromSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxPayerSettings.this, TaxPayerOptions.class));
            }
        });

        //if log out button is pressed, sign out the user and go to the MainActivity java file
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                Toast.makeText(TaxPayerSettings.this, "Signed out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TaxPayerSettings.this, MainActivity.class));
            }
        });

        //if delete account button is pressed, delete the user's account and go to the MainActivity java file
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TaxPayerSettings.this, "Account deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TaxPayerSettings.this, MainActivity.class));
                        }
                    }
                });
            }
        });



    }
}
