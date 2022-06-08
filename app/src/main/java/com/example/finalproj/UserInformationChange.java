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

import com.example.finalproj.databinding.ActivityUserInformationChangeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class UserInformationChange extends DrawerBaseActivity {
    DatabaseReference mDatabase;
    EditText EditName , EditPhoneNum;
    Button EditUserEdit;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    ActivityUserInformationChangeBinding activityUserInformationChangeBinding;
    private String aftername="",afterphonenum="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityUserInformationChangeBinding = ActivityUserInformationChangeBinding.inflate(getLayoutInflater());
        setContentView(activityUserInformationChangeBinding.getRoot());
        allocateActivityTitle("회원 정보 수정");

        EditName = (EditText) findViewById(R.id.EditName);
        EditPhoneNum = (EditText) findViewById(R.id.EditPhoneNum);
        EditUserEdit = (Button) findViewById(R.id.EditUserEdit);

        aftername = EditName.getText().toString().trim();
        afterphonenum = EditPhoneNum.getText().toString().trim();
    }

    public void btnEditSure(View view){
        String userPK = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> nameHashMap = new HashMap<>();
        HashMap<String,Object> numHashMap = new HashMap<>();
        nameHashMap.put("userName",EditName);
        numHashMap.put("phoneNo",EditPhoneNum);
        mDatabase.child("Users").child(userPK).updateChildren(nameHashMap);
        mDatabase.child("Users").child(userPK).updateChildren(numHashMap);

    }

}

