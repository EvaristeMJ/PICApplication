package com.example.picapplication.ui.gamecreated;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picapplication.adapter.GameAdapter;
import com.example.picapplication.database.Game;
import com.example.picapplication.databinding.FragmentGameCreatedBinding;
import com.example.picapplication.databinding.FragmentGamesBinding;

import java.util.List;


public class GameCreatedFragment extends Fragment {

    private FragmentGameCreatedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameCreatedBinding.inflate(getLayoutInflater());
        displayLastGames();
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
}