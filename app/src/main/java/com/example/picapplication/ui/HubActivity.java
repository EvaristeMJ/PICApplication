package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.picapplication.R;
import com.example.picapplication.adapter.UserAdapter;
import com.example.picapplication.board.BoardConnection;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.User;
import com.example.picapplication.databinding.ActivityHubBinding;

import java.util.List;

public class HubActivity extends AppCompatActivity {
    private List<User> players;
    private PicBoardConnection boardConnection = new BoardConnection();
    private ActivityHubBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        binding = ActivityHubBinding.inflate(getLayoutInflater());
        players = boardConnection.getUsers();
        binding.userHubList.setAdapter(new UserAdapter(players));
    }
}