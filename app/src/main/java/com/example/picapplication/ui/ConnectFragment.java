package com.example.picapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.picapplication.R;
import com.example.picapplication.board.PicBoardConnection;

/**
 * ConnectFragment is a fragment that allows the user to connect to a board.
 */
public class ConnectFragment extends Fragment {
    private PicBoardConnection boardConnection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boardConnection.connect();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }
}