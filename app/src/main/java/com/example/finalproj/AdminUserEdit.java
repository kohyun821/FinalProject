package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproj.databinding.ActivityAdminUserEditBinding;

public class AdminUserEdit extends AdminDrawerBaseActivity {

    ActivityAdminUserEditBinding activityAdminUserEditBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminUserEditBinding = ActivityAdminUserEditBinding.inflate(getLayoutInflater());
        setContentView(activityAdminUserEditBinding.getRoot());
        allocateActivityTitle("유저 정보 변경");


    }
}