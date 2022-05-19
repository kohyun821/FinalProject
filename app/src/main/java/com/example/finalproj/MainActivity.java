package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createQRBtn;
    private Button InBodyIPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createQRBtn = (Button) findViewById(R.id.createQR);
        InBodyIPBtn = (Button) findViewById(R.id.InBodyIP);

        createQRBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CreateQR.class);
                startActivity(intent);
            }
        });

        InBodyIPBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, InBody1.class);
                startActivity(intent);
            }
        });
    }


}