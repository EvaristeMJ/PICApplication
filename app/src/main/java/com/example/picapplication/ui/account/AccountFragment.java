package com.example.picapplication.ui.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.databinding.FragmentAccountBinding;
import com.example.picapplication.ui.SignInActivity;
import com.google.android.material.button.MaterialButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private TextView username;
    private ImageView profileImage;
    private Switch assistanceSwitch;
    private MaterialButton deleteAccountButton;
    private PicDatabase picDatabase = new DatabaseHelper();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        username = binding.username;
        deleteAccountButton = binding.DeleteAccount;
        profileImage = binding.profileImage;
        username.setText(picDatabase.getUserLogged().getUsername());
        profileImage.setImageBitmap(picDatabase.getUserLogged().getProfilePicture());
        assistanceSwitch = binding.assistantSwitch;
        assistanceSwitch.setChecked(picDatabase.getUserLogged().wantsAssistance());
        View root = binding.getRoot();
        setListeners();

        return root;
    }

    private void setListeners() {
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });
        assistanceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDatabase.setAssistance(assistanceSwitch.isChecked());
            }
        });
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageUri = data.getData();
                    binding.profileImage.setImageURI(imageUri);
                    updateProfileImage(imageUri);
                }
            }
    );
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Uri imageUri = data.getData();
            binding.profileImage.setImageURI(data.getData());
        }
    }
    private void updateProfileImage(Uri imageUri){
        try {
            InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}