package com.example.mdpiyalhasan.broatcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Md Piyal Hasan on 10/20/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
     Intent myIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Receive","yeh!");
        String string=intent.getExtras().getString("extra");
        Log.e("string ",string);
        Intent intent_service=new Intent(context,RingtonService.class);
        intent_service.putExtra("extra",string);
        context.startService(intent_service);
    }
}
