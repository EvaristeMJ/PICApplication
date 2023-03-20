package com.example.picapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.picapplication.R;
import com.example.picapplication.database.Game;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameInfoFragment extends Fragment {
    private Game game;
    public GameInfoFragment() {
        // Required empty public constructor
    }
    public GameInfoFragment(Game game) {
        this.game = game;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_info, container, false);
    }
}