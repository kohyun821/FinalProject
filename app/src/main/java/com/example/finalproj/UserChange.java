package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class UserChange extends AppCompatActivity {
    /*EditText UpdateName, UpdateNumber;

    String Key, name, number;*/
    EditText et5, et6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change);
        Intent intent = getIntent(); // 실려온 인텐트 정보를 담음
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        Button onButton9 = findViewById(R.id.button9);
        Button onButton10 = findViewById(R.id.button1);

        et5 = findViewById(R.id.EditView8);
        et5.setText(name);

        et6 = findViewById(R.id.EditView9);
        et6.setText(number);

        onButton9.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent data = new Intent();

                String name = et5.getText().toString(); // 읽어와서 변수에 담음
                String number = et6.getText().toString();

                data.putExtra("name", name); // 인텐트에 내용 실음
                data.putExtra("number", number);

                 //현재 액티비티 넘기고 메인 메소드 호출하며 종료
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        onButton10.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish(); // 현재 화면 종료
            }
        });

    }
}