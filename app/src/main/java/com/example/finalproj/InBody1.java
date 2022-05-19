package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InBody1 extends AppCompatActivity {

    EditText editTextDate, editTextDate3, editTextDate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_body1);

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate3 = (EditText) findViewById(R.id.editTextDate3);
        editTextDate2 = (EditText) findViewById(R.id.editTextDate2);

    }

    public void buttonSignInScrSignInClicked(View v){

        String UserInBody1 = editTextDate.getText().toString().trim();
        String UserInBody2 = editTextDate3.getText().toString().trim();
        String UserInBody3 = editTextDate2.getText().toString().trim();

        if(editTextDate.length()<200){

        }

        if(editTextDate3.length()<200){

        }

        if(editTextDate2.length()<200){

        }
    }

}