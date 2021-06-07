package com.arish1999.socialmediaintegration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacebookProfileActivity extends AppCompatActivity {
    Button logout;
    TextView tvName;
    TextView tvEmail;
    CircleImageView profileImage;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_profile);
        getSupportActionBar().hide();
        logout = findViewById(R.id.fb_logoutButton);
        tvEmail = findViewById(R.id.fb_textEmail);
        builder = new AlertDialog.Builder(this);
        tvName = findViewById(R.id.fb_textName);
        profileImage = findViewById(R.id.fb_profile_image);
        SharedPreferences sharedPreferences = getSharedPreferences("myFBdata",MODE_PRIVATE);
        String name = sharedPreferences.getString("fb_name","");
        String email = sharedPreferences.getString("fb_email","");
        String photoUrl = sharedPreferences.getString("fb_photo","");
        tvName.setText(name);
        tvEmail.setText(email);
        Glide.with(getApplicationContext()).load(photoUrl).placeholder(R.drawable.avatar).into(profileImage);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout");
        builder.setIcon(R.drawable.ic_warning);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    deleteSharedPreferences("myData");
                }
                startActivity(new Intent(FacebookProfileActivity.this,MainActivity.class));
                finish();
                dialog.dismiss();





            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();

    }
}