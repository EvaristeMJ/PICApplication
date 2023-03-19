package com.example.picapplication.ui.gamecreated;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameCreatedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GameCreatedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}