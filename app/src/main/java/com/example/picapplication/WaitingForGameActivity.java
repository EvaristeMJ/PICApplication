package com.example.picapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CheckBox;

import com.example.picapplication.R;
import com.example.picapplication.adapter.UserAdapter;
import com.example.picapplication.board.BluetoothBoardConnection;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.User;


import java.util.List;

public class WaitingForGameActivity extends AppCompatActivity {

    private List<User> players;
    private PicBoardConnection boardConnection = new BluetoothBoardConnection();
    private CheckBox ruleTurnMode;
    private RecyclerView userHubList;
    private UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_game);
    }
}