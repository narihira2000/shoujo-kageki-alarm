package com.tsuyoshi.shoujokagekialarm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {



    private int sethour;
    private int setmin;
    private int setClick = 0;

    int versionCode;
    int newVersionCode = 0;
    private String verCode = null;

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

        //得到目前version code
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionCode = packageInfo.versionCode;



        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<String> urls = new ArrayList<String>();
                try{
                    URL url = new URL("https://rawgit.com/narihira2000/shoujo-kageki-alarm/master/ShoujoKagekiAlarm/versionCode.txt");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000);

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    while ((verCode = in.readLine()) != null){
                        urls.add(verCode);
                    }
                    in.close();
                    verCode = urls.get(0);
                    if (verCode != null){
                        newVersionCode = Integer.parseInt(verCode);
                    }

                }catch (Exception e){
                    Log.d("MyTag",e.toString());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findUpdate();
                    }
                });


            }
        }).start();


//        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
//        if (activeNetwork != null && activeNetwork.isConnected()){
//            try {
//                URL url = new URL("https://rawgit.com/narihira2000/shoujo-kageki-alarm/master/ShoujoKagekiAlarm/versionCode.txt");
//
//                new ReadTextTask().execute(url);
//            }catch (MalformedURLException e){
//            }
//        }

//        try{
//            URL url = new URL("https://rawgit.com/narihira2000/shoujo-kageki-alarm/master/ShoujoKagekiAlarm/versionCode.txt");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//            while ((verCode = in.readLine()) != null){
//
//            }
//            in.close();
//        }catch (MalformedURLException e){
//        }catch (IOException e){
//        }

//        verCode = downloadText();



//        if (verCode != null){
//            newVersionCode = Integer.parseInt(verCode);
//        }



//
//


    }

    private void findUpdate(){
        if(newVersionCode > versionCode){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("There is a newer version")
                    .setMessage("Would you like to download?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent updateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/uc?export=download&id=1nhU5UpMMESHasyyJ2CpU3lUYDDgb2cul"));
                            startActivity(updateIntent);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }



//    private class ReadTextTask extends AsyncTask<URL, Void, String>{
//
//        @Override
//        protected String doInBackground(URL... urls) {
//            try {
//                BufferedReader in = new BufferedReader(new InputStreamReader(urls[0].openStream()));
//
//                verCode = in.readLine();
//                in.close();
//            }catch (IOException e){
//            }
//            return verCode;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            verCode = s;
//            super.onPostExecute(s);
//        }
//    }




//    private String downloadText(){
//        int BUFFER_SIZE = 2000;
//        InputStream in = null;
//        try{
//            in = openHttpConnection();
//        }catch (IOException e1){
//            return "";
//        }
//
//        String str = "";
//        if(in != null){
//            InputStreamReader isr = new InputStreamReader(in);
//            int charRead;
//            char[] inputBuffer = new char[BUFFER_SIZE];
//            try {
//                while ((charRead = isr.read(inputBuffer))>0){
//                    String readString = String.copyValueOf(inputBuffer,0,charRead);
//                    str += readString;
//                    inputBuffer = new char[BUFFER_SIZE];
//                }
//                in.close();
//            }catch (IOException e){
//                return "";
//            }
//        }
//        return str;
//    }
//
//    private InputStream openHttpConnection() throws IOException{
//        InputStream in = null;
//        int response = -1;
//
//        URL url = new URL("https://rawgit.com/narihira2000/shoujo-kageki-alarm/master/ShoujoKagekiAlarm/versionCode.txt");
//        URLConnection conn = url.openConnection();
//
//        if (!(conn instanceof HttpURLConnection)){
//            throw new IOException("Not an HTTP connection");
//        }
//
//        try {
//            HttpURLConnection httpConn = (HttpURLConnection) conn;
//            httpConn.setAllowUserInteraction(false);
//            httpConn.setInstanceFollowRedirects(true);
//            httpConn.setRequestMethod("GET");
//            httpConn.connect();
//
//            response = httpConn.getResponseCode();
//            if(response == HttpURLConnection.HTTP_OK){
//                in = httpConn.getInputStream();
//            }
//        }catch (Exception ex){
//            throw new IOException("Error connecting");
//        }
//        return in;
//    }





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



