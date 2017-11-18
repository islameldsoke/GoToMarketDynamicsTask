package com.example.islam.gotomarketdynamicstask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    private final static int READ_CONTACT = 123;
    private Button facebookActivity ,search , contact , map , calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        facebookActivity = findViewById(R.id.facebook_btn);
        search = findViewById(R.id.search_btn);
        contact = findViewById(R.id.contact_btn);
        map = findViewById(R.id.map_btn);
        calender = findViewById(R.id.calender_btn);
        facebookActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , FacebookActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS);
                if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS} , READ_CONTACT);
                }else {
                    startActivity(new Intent(MainActivity.this,ContactActivity.class));
                }

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , MapsActivity.class));
            }
        });

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , CalenderActivity.class));
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACT: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this,ContactActivity.class));
                }else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this ,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        new AlertDialog.Builder(this)
                                .setTitle("permission")
                                .setMessage("Read Contacts").show();
                    }else {
                        new AlertDialog.Builder(this)
                                .setTitle("permission")
                                .setMessage("denied").show();
                    }

                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                // other 'case' lines to check for other
                // permissions this app might request
        }

    }
}
