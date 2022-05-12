package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproj.databinding.ActivityGuestDumbbellBinding;

public class GuestDumbbell extends DrawerBaseActivity {

    ActivityGuestDumbbellBinding activityGuestDumbbellBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuestDumbbellBinding = ActivityGuestDumbbellBinding.inflate(getLayoutInflater());
        setContentView(activityGuestDumbbellBinding.getRoot());
        allocateActivityTitle("운동 기록 확인");
    }
}