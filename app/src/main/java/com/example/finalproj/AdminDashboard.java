package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproj.databinding.ActivityAdminDashboardBinding;

public class AdminDashboard extends AdminDrawerBaseActivity {

    ActivityAdminDashboardBinding activityAdminDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminDashboardBinding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityAdminDashboardBinding.getRoot());
        allocateActivityTitle("I'm Admin!");
    }
}