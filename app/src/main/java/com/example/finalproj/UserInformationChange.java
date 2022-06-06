package com.example.finalproj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserInformationChange extends AppCompatActivity {
    DatabaseReference mDatabase;

   /* protected void onCreate(Bundle savedInstanceState) {
        *//*super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView userName = (TextView)findViewById(R.id.Read_Name);
        TextView userNumber = (TextView)findViewById(R.id.Read_Number);

        Log.w(this.getClass().getName(), (String)userName.getText());
        Log.w(this.getClass().getName(), (String)userNumber.getText());

        HashMap<String,Object> hashmap_userName = new HashMap<>();
        HashMap<String,Object> hashmap_userNumber = new HashMap<>();

        hashmap_userName.put(auth,userName);
        hashmap_userNumber.put(auth,userNumber);

        mDatabase.child("userName").child(user.getUid());
        mDatabase.child("userNumber").child(user.getUid());*//*

    }*/

    private static final int CALL_UserChange =0; // intent 객체와 상수값을 전달
    EditText E4 , E5;
    /*ActivityInBody1Binding activityUserInformationChangeBinding;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_change);

    }
        /*Button onButton8 = findViewById(R.id.onButton8);

        onButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                E4 = findViewById(R.id.EditView4);
                String name = E4.getText().toString();

                E5 = findViewById(R.id.EditView5);
                String number = E5.getText().toString();

            String name = E4.getText().toString().trim();//이름
            String number = E5.getText().toString().trim();//번호
*/
                /*Intent intent = new Intent(this.getApplicationContext(), UserChange.class);
                intent.putExtra("name", name); // 인텐트에 정보를 실어줌
                intent.putExtra("number", number);

                //startActivity(intent);
                startActivityForResult(intent, CALL_UserChange); // 수정된 값(리턴값)을 받음
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
                super.onActivityResult(requestCode, resultCode, data);

                switch (requestCode) {
                    case CALL_UserChange:
                        if (resultCode == RESULT_OK) { // 잘 넘어왔으면 resultCode가 ok값을 가져옴
                            String name = data.getStringExtra("name");
                            String number = data.getStringExtra("number");

                            E4.setText(name); // 인텐트에서 읽어온 값 다시 설정
                            E5.setText(number);

                        }
                        break;
                }
            }*/
        /*});*/
        public void onButton1Click(View view) { // 수정 버튼

            E4 = findViewById(R.id.EditView8);
            String name = E4.getText().toString();

            E5 = findViewById(R.id.EditView9);
            String number = E5.getText().toString();

            /*String name = E4.getText().toString().trim();//이름
            String number = E5.getText().toString().trim();//번호*/

            Intent intent = new Intent(this.getApplicationContext(), UserChange.class);
            intent.putExtra("name", name); // 인텐트에 정보를 실어줌
            intent.putExtra("number", number);

            //startActivity(intent);
            startActivityForResult(intent, CALL_UserChange); // 수정된 값(리턴값)을 받음
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case CALL_UserChange:
                    if (resultCode == RESULT_OK) { // 잘 넘어왔으면 resultCode가 ok값을 가져옴
                        String name = data.getStringExtra("name");
                        String number = data.getStringExtra("number");

                        E4.setText(name); // 인텐트에서 읽어온 값 다시 설정
                        E5.setText(number);

                    }
                    break;
            }
        }
    }// MainActivity
        /*activityUserInformationChangeBinding = ActivityInBody1Binding.inflate(getLayoutInflater());
        setContentView(activityUserInformationChangeBinding.getRoot());*/

        /*EditText textView4 = findViewById(R.id.textView4);//이름
        EditText textView5 = findViewById(R.id.textView5);//번호
        Button button8 = findViewById(R.id.button8);//수정 버튼

*//*
        mDatabase = FirebaseDatabase.getInstance().getReference();
*//*

        button8.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String User_name = textView4.getText().toString().trim();//이름
                String User_number = textView5.getText().toString().trim();//번호

                HashMap<String,Object> hashmap_Username = new HashMap<>();
                HashMap<String,Object> hashmap_Usernumber = new HashMap<>();

            }
        });*/

