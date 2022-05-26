package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproj.databinding.ActivityUserTimetableBinding;

public class UserTimetable extends DrawerBaseActivity {

    ActivityUserTimetableBinding activityUserTimetableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserTimetableBinding = ActivityUserTimetableBinding.inflate(getLayoutInflater());
        setContentView(activityUserTimetableBinding.getRoot());
        allocateActivityTitle("시간표 조회 및 변경");
    }
}