package com.example.picapplication.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.picapplication.databinding.FragmentLogOutBinding;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.ui.SignInActivity;

public class LogOutFragment extends Fragment {
    PicDatabase picDatabase;

    private FragmentLogOutBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signOut();
        return null;
    }
    private void signOut() {
        startActivity(new Intent(getActivity(), SignInActivity.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}