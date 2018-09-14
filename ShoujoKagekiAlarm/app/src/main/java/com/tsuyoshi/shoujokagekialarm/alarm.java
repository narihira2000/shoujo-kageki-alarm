package com.tsuyoshi.shoujokagekialarm;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class alarm extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    //ImageView a1,a2,a3,a4,a5,a6,a7,a8,a9;

    //private int passNum = (int) (Math.random()*9+1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        int passNum = (int) (Math.random()*8+1);

        ImageButton btnstop = (ImageButton) findViewById(R.id.alarmstop);
        ImageView giraffeIcon = (ImageView) findViewById(R.id.giraffeIcon);


        ImageView a1 = (ImageView) findViewById(R.id.a1);
        ImageView a2 = (ImageView) findViewById(R.id.a2);
        ImageView a3 = (ImageView) findViewById(R.id.a3);
        ImageView a4 = (ImageView) findViewById(R.id.a4);
        ImageView a5 = (ImageView) findViewById(R.id.a5);
        ImageView a6 = (ImageView) findViewById(R.id.a6);
        ImageView a7 = (ImageView) findViewById(R.id.a7);
        ImageView title = (ImageView) findViewById(R.id.imageView6);
        ImageView lastday = (ImageView) findViewById(R.id.imageView7);
//        ImageView a8 = (ImageView) findViewById(R.id.a8);
//        ImageView a9 = (ImageView) findViewById(R.id.a9);

        switch (passNum){
            case 1:
                a1.setVisibility(View.VISIBLE);
                break;
            case 2:
                a2.setVisibility(View.VISIBLE);
                break;
            case 3:
                a3.setVisibility(View.VISIBLE);
                break;
            case 4:
                a4.setVisibility(View.VISIBLE);
                break;
            case 5:
                a5.setVisibility(View.VISIBLE);
                break;
            case 6:
                a6.setVisibility(View.VISIBLE);
                break;
            case 7:
                a7.setVisibility(View.VISIBLE);
                break;
            default:
                title.setVisibility(View.GONE);
                lastday.setVisibility(View.VISIBLE);
                break;
        }

        /*PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyWakelockTag");
        wakeLock.acquire();*/

        /*Intent intent = getIntent();
        String passNumString = intent.getStringExtra("passNum");
        passNum = Integer.parseInt(passNumString);*/

        Animation animation = new RotateAnimation(0,-360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(10000);
        animation.setRepeatCount(Animation.INFINITE);
        giraffeIcon.setAnimation(animation);
        animation.startNow();

        mediaPlayer = MediaPlayer.create(this,R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.stop();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }

    /*private void numShow(){
        switch (passNum){
            case 1:
                a1.setVisibility(View.VISIBLE);
                break;
            case 2:
                a2.setVisibility(View.VISIBLE);
                break;
            case 3:
                a3.setVisibility(View.VISIBLE);
                break;
            case 4:
                a4.setVisibility(View.VISIBLE);
                break;
            case 5:
                a5.setVisibility(View.VISIBLE);
                break;
            case 6:
                a6.setVisibility(View.VISIBLE);
                break;
            case 7:
                a7.setVisibility(View.VISIBLE);
                break;
            case 8:
                a8.setVisibility(View.VISIBLE);
                break;
            default:
                a9.setVisibility(View.VISIBLE);
                break;
        }
    }*/

    /*private void numInit(){
        a1 = (ImageView) findViewById(R.id.a1);
        a2 = (ImageView) findViewById(R.id.a2);
        a3 = (ImageView) findViewById(R.id.a3);
        a4 = (ImageView) findViewById(R.id.a4);
        a5 = (ImageView) findViewById(R.id.a5);
        a6 = (ImageView) findViewById(R.id.a6);
        a7 = (ImageView) findViewById(R.id.a7);
        a8 = (ImageView) findViewById(R.id.a8);
        a9 = (ImageView) findViewById(R.id.a9);

    }*/
}
