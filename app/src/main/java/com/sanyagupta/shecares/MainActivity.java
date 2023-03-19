package com.sanyagupta.shecares;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanyagupta.shecares.data.MyDbHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ImageView profile,reminder,note;
    int year,month,day;
    TextView calendar,Support,Profile, days,date , newdate;
    String end = new String();
    int Year,Month,Date;
    MyDbHandler db= new MyDbHandler(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Profile = findViewById(R.id.Profile);
        newdate = findViewById(R.id.newdate);
        Support = findViewById(R.id.Support);
        date=findViewById(R.id.date);
        calendar = findViewById(R.id.Calendar);
        days= findViewById(R.id.days);
        end = db.nextDate();

        Calendar c= Calendar.getInstance();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currdate= myFormat.format(c.getTime());

        if(end==null)
            end = currdate.toString();
        date.setText(end);
        int length = calculateDays(currdate, end);
        if(length<0)
        {
            length = length*-1;
            String set = String.valueOf(length);
            days.setText(set + " days ago");
        }
        else {
            String set = String.valueOf(length);
            days.setText(set + " days to go");
        }

        newdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Updates.class);
                startActivity(intent);
            }
        });

        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Support.class);
                startActivity(intent);

            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dtaes.class);
                startActivity(intent);

            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);

            }
        });

    }

    private void setBootReceiverEnabled() {
    }

//    int calculatedays(String start , String end)
//    {
//
//        if(end!=start) {
//            int duration;
//            String sub = new String(), sub1 = new String();
//            int i = 0, j = 0;
//
//            while (start.charAt(i) != ' ') {
//                sub += String.valueOf(start.charAt(i));
//                i++;
//            }
//            i++;
//            while (end.charAt(j) != ' ') {
//                sub1 += String.valueOf(end.charAt(j));
//                j++;
//            }
//            j++;
//            int date = Integer.valueOf(sub);
//            sub = new String();
//            int date1 = Integer.valueOf(sub1);
//            Date=date1;
//            sub1 = new String();
//            while (start.charAt(i) != ' ') {
//                sub += String.valueOf(start.charAt(i));
//                i++;
//            }
//            while (end.charAt(j) != ' ') {
//                sub1 += String.valueOf(end.charAt(j));
//                j++;
//            }
//            j++;
//
//            if (sub.equals("Jan"))
//                sub = "1";
//            else if (sub.equals("Feb"))
//                sub = "2";
//            else if (sub.equals("Mar"))
//                sub = "3";
//            else if (sub.equals("Apr"))
//                sub = "4";
//            else if (sub.equals("May"))
//                sub = "5";
//            else if (sub.equals("Jun"))
//                sub = "6";
//            else if (sub.equals("Jul"))
//                sub = "7";
//            else if (sub.equals("Aug"))
//                sub = "8";
//            else if (sub.equals("Sep"))
//                sub = "9";
//            else if (sub.equals("Oct"))
//                sub = "10";
//            else if (sub.equals("Nov"))
//                sub = "11";
//            else
//                sub = "12";
//
//            if (sub1.equals("Jan"))
//                sub1 = "1";
//            else if (sub1.equals("Feb"))
//                sub1 = "2";
//            else if (sub1.equals("Mar"))
//                sub1 = "3";
//            else if (sub1.equals("Apr"))
//                sub1 = "4";
//            else if (sub1.equals("May"))
//                sub1 = "5";
//            else if (sub1.equals("Jun"))
//                sub1 = "6";
//            else if (sub1.equals("Jul"))
//                sub1 = "7";
//            else if (sub1.equals("Aug"))
//                sub1 = "8";
//            else if (sub1.equals("Sep"))
//                sub1 = "9";
//            else if (sub1.equals("Oct"))
//                sub1 = "10";
//            else if (sub1.equals("Nov"))
//                sub1 = "11";
//            else
//                sub1 = "12";
//            int month1 = Integer.valueOf(sub1);
//            int month = Integer.valueOf(sub);
//            Month=month1;
//            sub1=new String();
//
//            int datediff = date1 - date;
//            int monthdiff = month1 - month;
//            if (monthdiff > 0) {
//                if (month == 4 || month == 6 || month == 8 || month == 10) {
//                    duration = 30 - date + date1 + 1 + 30* (monthdiff -1);
//                } else if (month == 2) {
//                    duration = 28 - date + date1 + 1 + 31* (monthdiff -1);
//                } else
//                    duration = 31 - date + date1 + 1 + 31 * (monthdiff-1);
//            } else {
//                duration = date1 - date;
//            }
//
//
//            return duration;
//        }
//        return 0;
//    }
    int calculateDays(String curr, String end){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        long diff = 0;
        try {
            Date date1 = myFormat.parse(curr);
            Date date2 = myFormat.parse(end);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}

