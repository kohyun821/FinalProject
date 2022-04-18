package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproj.databinding.ActivityGuestPencilBinding;

public class GuestPencil extends AppCompatActivity {

    ActivityGuestPencilBinding activityGuestPencilBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuestPencilBinding = ActivityGuestPencilBinding.inflate(getLayoutInflater());
        setContentView(activityGuestPencilBinding.getRoot());
    }
}