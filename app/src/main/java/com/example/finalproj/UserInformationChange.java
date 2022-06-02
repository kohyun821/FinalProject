package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalproj.databinding.ActivityGuestDumbbellBinding;
import com.example.finalproj.databinding.ActivityUserTimetableBinding;

public class UserInformationChange extends DrawerBaseActivity{

    ActivityUserTimetableBinding activityUserTimetableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityUserTimetableBinding = ActivityUserTimetableBinding.inflate(getLayoutInflater());
        setContentView(activityUserTimetableBinding.getRoot());
        allocateActivityTitle("운동 기록 조회 및 등록");



    }
}