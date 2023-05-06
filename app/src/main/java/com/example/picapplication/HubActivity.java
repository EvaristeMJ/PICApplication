package com.example.picapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.picapplication.adapter.UserAdapter;
import com.example.picapplication.board.BluetoothBoardConnection;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.User;
import com.example.picapplication.databinding.ActivityHubBinding;
import com.example.picapplication.ui.GameScreenActivity;

import java.util.List;

public class HubActivity extends AppCompatActivity {
    private List<User> players;
    private PicBoardConnection boardConnection = new BluetoothBoardConnection();
    private ActivityHubBinding binding;
    private CheckBox ruleTurnMode;
    private RecyclerView userHubList;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        binding = ActivityHubBinding.inflate(getLayoutInflater());
        //players = boardConnection.getUsers();
        userHubList = binding.recyclerView;
        ruleTurnMode = binding.checkBox;
        players = User.createTestDisplayUsers(4);
        userHubList.setAdapter(new UserAdapter(players));
        binding.launchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HubActivity.this, GameScreenActivity.class);
                startActivity(intent);
            }
        });
}
}