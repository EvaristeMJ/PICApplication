package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.picapplication.R;
import com.example.picapplication.database.Game;
import com.example.picapplication.database.GameInfo;
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
        Game game = database.getGameSelected();
        GameInfo gameInfo = database.getGameInfo(game.getId());
        String[] InformationName = gameInfo.getNameInformation();
        binding.firstInfo.setText(InformationName[0]);
        binding.secondInfo.setText(InformationName[1]);
        binding.thirdInfo.setText(InformationName[2]);
        binding.fourthInfo.setText(InformationName[3]);
        binding.fifthInfo.setText(InformationName[4]);

        binding.backgroundView.setImageBitmap(game.getImage());
    }
}