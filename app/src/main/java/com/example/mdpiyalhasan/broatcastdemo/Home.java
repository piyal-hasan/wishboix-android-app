package com.example.mdpiyalhasan.broatcastdemo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {
    Button btbirth,btwish,stopbt,startbt,okbt;
    public static EditText contx,smstx;
    int date_id=0;
    int time_id=9999;
    int  month_x,year_x,day_x,minuiteofhour,hour;
    AlarmManager alarm;
    PendingIntent pendintent;
    Intent intent;
    Context contex;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        setTitle("       Wish Box");

        alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
        btbirth=(Button)findViewById(R.id.setdatebt);
        btwish=(Button)findViewById(R.id.settimebt);
        okbt=(Button) findViewById(R.id.savebt);
        stopbt=(Button)findViewById(R.id.stopbt);
        startbt=(Button)findViewById(R.id.startbt);
        contx=(EditText)findViewById(R.id.contact);
        smstx=(EditText)findViewById(R.id.smstext);
        this.contex=this;
        cal=Calendar.getInstance();
        year_x= cal.get(Calendar.YEAR);
        month_x= cal.get(Calendar.MONTH);
        day_x= cal.get(Calendar.DAY_OF_MONTH);
        showdate_time();
        alarmservice();
       // fileRead();
    }
    private void showdate_time()
    {
        btbirth.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
             showDialog(date_id);
            }
        });
        btwish.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(time_id);

            }
        });
    }
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == time_id) {
            return new TimePickerDialog(this, timedialoglistener,hour,minuiteofhour, DateFormat.is24HourFormat(this));
        }
        else {
            if (id ==date_id ) {
                return new DatePickerDialog(this, daydialoglistener, year_x, month_x, day_x);
            }
        }
        return null;
    }
        private DatePickerDialog.OnDateSetListener daydialoglistener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year_x=year;
                month_x=monthOfYear;
                day_x=dayOfMonth;
                //Toast.makeText(DemoActivity.this,"Set the day"+year_x+"/"+month_x+"/"+day_x,Toast.LENGTH_SHORT).show();
            }
        };
    private TimePickerDialog.OnTimeSetListener timedialoglistener =new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour=hourOfDay;
            minuiteofhour=minute;
            //Toast.makeText(DemoActivity.this,"Time set to"+hour+"/"+minuiteofhour,Toast.LENGTH_LONG).show();
        }
    };
    public void alarmservice()
    {
        intent=new Intent(getApplicationContext(),AlarmReceiver.class);
        stopbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Wish Cancel",Toast.LENGTH_LONG).show();
                intent.putExtra("extra", "alarm off");
                alarm.cancel(pendintent);
                sendBroadcast(intent);

            }
        });
        startbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.set(Calendar.HOUR_OF_DAY,hour);
                cal.set(Calendar.MINUTE,minuiteofhour);
                cal.set(Calendar.YEAR,year_x);
                cal.set(Calendar.MONTH,month_x);
                cal.set(Calendar.DAY_OF_MONTH,day_x);
                Toast.makeText(getBaseContext(),"Wish set to"+year_x+ "/"+month_x+"/"+day_x+"\n"+hour+":"+minuiteofhour,Toast.LENGTH_SHORT).show();
                intent.putExtra("extra","alarm on");
                //sendBroadcast(intent);
                pendintent=PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendintent);
            }
        });
        okbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savetofile(contx.getText().toString(),smstx.getText().toString());
            }
        });
    }
    private void savetofile(String contact, String sms) {
        try {
            Database database=new Database(this);
            SmsModel smsModel= new SmsModel(1,sms,contact);
            database.addItem(smsModel);
            Toast.makeText(getBaseContext(),"Wish saved",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

