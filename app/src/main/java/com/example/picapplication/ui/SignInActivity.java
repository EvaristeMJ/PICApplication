package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.picapplication.MainActivity;
import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.User;
import com.example.picapplication.databinding.ActivitySignInBinding;
import com.example.picapplication.sql.PicDatabase;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private PicDatabase picDatabase;
    private com.example.chat.utilities.PreferenceManager preferenceManager;
    public User userLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        preferenceManager = new com.example.chat.utilities.PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners(){
        binding.textCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    public boolean isValidSignInDetails(){

        if(binding.inputUsername.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return false;
        }
        else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputUsername.getText().toString().trim()).matches()){
            showToast("Enter valid email");
        }
        return true;
    }

    /**
     * Sign in user
     * Nothing happens if the user is not found or the password is wrong
     * If the user is found and the password is correct, the user is redirected to the main activity
     */
    public void signIn(){
        if(isValidSignInDetails()){
            int result = picDatabase.logUser(binding.inputUsername.getText().toString(),binding.inputPassword.getText().toString());
            if (result == DatabaseHelper.USER_NOT_FOUND){
                showToast("User not found");
            }
            else if(result == DatabaseHelper.WRONG_PASSWORD){
                showToast("Wrong password");
            }
            else{
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}