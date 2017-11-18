package com.example.islam.gotomarketdynamicstask;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {

    private EditText title_et , description_et , location_et;
    private Button addEvent_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        title_et = findViewById(R.id.title_et);
        description_et = findViewById(R.id.description_et);
        location_et = findViewById(R.id.location_et);

        addEvent_btn = findViewById(R.id.addEvent_btn);

        addEvent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");

                Calendar cal = Calendar.getInstance();
                long startTime = cal.getTimeInMillis();
                long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                intent.putExtra(CalendarContract.Events.TITLE, title_et.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description_et.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location_et.getText().toString());
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                startActivity(intent);
            }
        });



    }
}
