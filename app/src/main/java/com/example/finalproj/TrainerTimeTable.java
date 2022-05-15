package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproj.databinding.ActivityDashboardBinding;
import com.example.finalproj.databinding.ActivityTrainerTimeTableBinding;

public class TrainerTimeTable extends TrainerDrawerBaseActivity{

    @NonNull ActivityDashboardBinding activityTrainerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTrainerBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityTrainerBinding.getRoot());
        allocateActivityTitle("시간표 조회");
    }
}