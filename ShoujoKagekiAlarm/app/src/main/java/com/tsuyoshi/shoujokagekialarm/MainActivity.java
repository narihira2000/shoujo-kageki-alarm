package com.tsuyoshi.shoujokagekialarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private int sethour;
    private int setmin;
    private int setClick = 0;

    private TextView timeshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSetClock = (Button) findViewById(R.id.setclock);
        Button btnStartClock = (Button) findViewById(R.id.startclock);
        timeshow = (TextView) findViewById(R.id.timeshow);
        TextView github = (TextView) findViewById(R.id.textView4);
        github.setMovementMethod(LinkMovementMethod.getInstance());

        /*Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.day,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(passNum);*/


        btnSetClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                final int hour = c.get(Calendar.HOUR_OF_DAY);
                final int minute = c.get(Calendar.MINUTE);

                new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        setClick = 1;
                        sethour = i;
                        setmin = i1;
                        if(i1<10){
                            timeshow.setText(i + ":0" + i1);
                        }
                        else {
                            timeshow.setText(i + ":" + i1);
                        }

                    }
                },hour,minute,false).show();


            }

        });

        btnStartClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnStartClock(view);
            }
        });

    }



    private void setBtnStartClock(View view){
        long triggerTime;
        if(setClick==1){
            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),0);
            long nowTime = c.getTimeInMillis();
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),sethour,setmin,0);
            triggerTime = c.getTimeInMillis();

            /*Calendar d = Calendar.getInstance();
            d.set(d.get(Calendar.YEAR),d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH),d.get(Calendar.HOUR_OF_DAY),d.get(Calendar.MINUTE),0);
            long nowTime = d.getTimeInMillis();*/

            if(triggerTime < nowTime){
                triggerTime += 24*60*60*1000;
            }

            long waitTime = triggerTime - nowTime;

            long waitHour = TimeUnit.MILLISECONDS.toHours(waitTime);
            long waitMin = TimeUnit.MILLISECONDS.toMinutes(waitTime) % TimeUnit.HOURS.toMinutes(1);

            switch ((int) waitHour){
                case 0:
                    switch ((int) waitMin){
                        case 0:
                        case 1:
                            Toast.makeText(view.getContext(), "Alarm set for less than 1 minute from now", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(view.getContext(), "Alarm set for " + waitMin + " minutes from now", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
                case 1:
                    switch ((int) waitMin){
                        case 0:
                            Toast.makeText(view.getContext(), "Alarm set for 1 hour from now", Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(view.getContext(), "Alarm set for 1 hour and 1 minute from now", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(view.getContext(), "Alarm set for 1 hour and " + waitMin + " minutes from now", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;

                default:
                    switch ((int) waitMin){
                        case 0:
                            Toast.makeText(view.getContext(), "Alarm set for " + waitHour + " hours from now", Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(view.getContext(), "Alarm set for " + waitHour + " hours and " + waitMin + " minute from now", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(view.getContext(), "Alarm set for " + waitHour + " hours and " + waitMin + " minutes from now", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }

            /*if(waitHour == 0 && waitMin <= 1){
                Toast.makeText(view.getContext(), "Alarm set for less than 1 minute from now", Toast.LENGTH_LONG).show();
            }
            else if(waitHour == 0 && waitMin > 1){
                Toast.makeText(view.getContext(), "Alarm set for " + waitMin + " minutes from now", Toast.LENGTH_LONG).show();
            }
            else if(waitHour == 1 && waitMin == 0){
                Toast.makeText(view.getContext(), "Alarm set for 1 hour from now", Toast.LENGTH_LONG).show();
            }
            else if(waitHour == 1 && waitMin ==1){
                Toast.makeText(view.getContext(), "Alarm set for 1 hour and 1 minute from now", Toast.LENGTH_LONG).show();
            }
            else if(waitHour == 1 && waitMin > 1){
                Toast.makeText(view.getContext(), "Alarm set for 1 hour and " + waitMin + " minutes from now", Toast.LENGTH_LONG).show();
            }
            else if(waitHour > 1 && waitMin == 0){
                Toast.makeText(view.getContext(), "Alarm set for " + waitHour + " hours from now", Toast.LENGTH_LONG).show();
            }
            else if(waitHour > 1 && waitMin > 1){
                Toast.makeText(view.getContext(), "Alarm set for " + waitHour + " hours and " + waitMin + " minutes from now", Toast.LENGTH_LONG).show();
            }*/
        }
        else {
            triggerTime = 100;
            Toast.makeText(view.getContext(), "Alarm in 3 seconds", Toast.LENGTH_LONG).show();
        }

        setClick = 0;

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this,alarm.class);

        /*Intent intent1 = new Intent(this,soundAlarm.class);
        intent1.putExtra("passNum",passNum);*/

        //intent.setAction("WakeUp");

        /*Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);*/

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }

}
