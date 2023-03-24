package com.example.picapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.picapplication.MainActivity;
import com.example.picapplication.R;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.sql.PicDatabase;

/**
 * ConnectFragment is a fragment that allows the user to connect to a board.
 */
public class ConnectFragment extends Fragment {
    private PicBoardConnection boardConnection;
    PicDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int idBoard = boardConnection.connect();
        if(idBoard != 0){
            database.getUserLogged().setIdBoard(idBoard);
            MainActivity mainActivity = (MainActivity) getActivity();
            boardConnection.addReceiver(mainActivity);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }
}