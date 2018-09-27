package com.tsuyoshi.shoujokagekialarm;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

public class soundAlarm extends BroadcastReceiver {

    public soundAlarm(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {




        Intent i = new Intent(context,alarm.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


        /*String passNumString = intent.getStringExtra("passNum");
        int passNum = Integer.parseInt(passNumString);

        Intent i = new Intent(context,alarm.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("passNum",passNum);
        context.startActivity(i);*/

        /*String action = intent.getAction();
        if("WakeUp".equals(action)){
            PowerManager.WakeLock
        }*/

    }
}
