package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.picapplication.R;
import com.example.picapplication.adapter.UserAdapter;
import com.example.picapplication.board.BoardConnection;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.User;
import com.example.picapplication.databinding.ActivityHubBinding;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HubActivity extends AppCompatActivity {
    private List<User> players;
    private PicBoardConnection boardConnection = new BoardConnection();
    private ActivityHubBinding binding;
    private MaterialButton launchGameButton;
    private CheckBox ruleTurnMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        binding = ActivityHubBinding.inflate(getLayoutInflater());
        players = boardConnection.getUsers();
        binding.userHubList.setAdapter(new UserAdapter(players));
        launchGameButton = binding.launchGame;
        ruleTurnMode = binding.checkBox;
        launchGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GameScreenActivity.class));
            }
        });
    }

    private void setListeners() {
        launchGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GameScreenActivity.class));
            }
        });
        ruleTurnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardConnection.setRuleMode(ruleTurnMode.isChecked());
            }
        });
    }
}