package com.example.islam.gotomarketdynamicstask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private ListView contactListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactListView = findViewById(R.id.contact_lv);
        getContacts();



    }

    void getContacts(){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                , null
                , null
                ,null ,
                null);
        startManagingCursor(cursor);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME , ContactsContract.CommonDataKinds.Phone.NUMBER
                ,ContactsContract.CommonDataKinds.Phone._ID};
        int[] to = {android.R.id.text1 , android.R.id.text2};

        SimpleCursorAdapter  simpleCursorAdapter = new SimpleCursorAdapter(this , android.R.layout.simple_list_item_2,cursor,from,to);
        contactListView.setAdapter(simpleCursorAdapter);
        contactListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
