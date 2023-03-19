package com.example.picapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.picapplication.R;
import com.example.picapplication.adapter.GameAdapter;
import com.example.picapplication.database.Game;
import com.example.picapplication.databinding.FragmentAccountBinding;
import com.example.picapplication.databinding.FragmentGamesBinding;
import com.example.picapplication.sql.PicDatabase;

import java.util.List;
public class GamesFragment extends Fragment {
    private FragmentGamesBinding binding;
    private PicDatabase picDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGamesBinding.inflate(getLayoutInflater());
        displayLastGames();
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        return view;
    }
    private void displayLastGames(){
        //List<Game> games = picDatabase.getLastGamesCreated();
        List<Game> games = Game.initGameList();
        if(games.size()>0){
            GameAdapter gameAdapter = new GameAdapter(games);
            binding.GameList.setAdapter(gameAdapter);
            binding.GameList.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}