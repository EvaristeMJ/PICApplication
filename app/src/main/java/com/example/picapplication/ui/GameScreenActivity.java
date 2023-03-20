package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.picapplication.R;
import com.example.picapplication.databinding.ActivityGameScreenBinding;
import com.example.picapplication.sql.PicDatabase;

public class GameScreenActivity extends AppCompatActivity {
    private ActivityGameScreenBinding binding;
    private PicDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        binding = ActivityGameScreenBinding.inflate(getLayoutInflater());
        database.getGameSelected();
        binding.backgroundView.setImageBitmap(database.getGameSelected().getImage());
    }
}