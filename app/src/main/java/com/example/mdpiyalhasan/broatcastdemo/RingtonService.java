package com.example.mdpiyalhasan.broatcastdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Md Piyal Hasan on 10/20/2016.
 */
public class RingtonService  extends Service {
    MediaPlayer mediaPlayer;

    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        Log.e("Lopcalservice","receiver id"+startId+":"+intent);
        String state=intent.getExtras().getString("extra");
        Log.e("extra string",state);
        Intent intent_main_activity=new Intent(this.getApplicationContext(),Home.class);
        //PendingIntent pendingIntent_main_activity=PendingIntent.getActivity(this,0,intent_main_activity,0);

        assert state != null;
        switch (state)
        {
            case "alarm on": {
                startId=1;
                break;
            }
            case "alarm off": {
                startId = 0;
                break;
            }
            default: {
                startId = 0;
                break;
            }
        }
        //rington off_setup setting
        if(!this.isRunning&&startId==1)
        {
            mediaPlayer=MediaPlayer.create(this,R.raw.whatsapp_whistle);
            mediaPlayer.start();
            this.isRunning=true;
            this.startId=0;
            smssend();
        }
        else if(this.isRunning&&startId==0)
        {
            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isRunning=false;
            this.startId=0;

        }
        else if(!this.isRunning&&startId==0)
        {
            this.isRunning=false;
            this.startId=0;
        }
        else if(this.isRunning&&startId==1)
        {
            this.isRunning=true;
            this.startId=1;

        }
        else
        {

        }
        return START_NOT_STICKY;
    }

    private void addnotification(String msg) {

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.baseicon)
                        .setContentTitle("WISHED SMS SEND ")
                        .setContentText("To: "+msg);
        Intent notificationIntent = new Intent(this, Home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void smssend() {
        Database database=new Database(this);
        List<SmsModel> l=database.getlist();
        String sms_number=l.get(0).getPhone_number();
        String msg=l.get(0).getSms();
        sms_sendto(sms_number,msg);
    }
    private void sms_sendto(String contact, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(contact, null,msg , null, null);
        addnotification(contact);
    }
    public void onDestroy()
    {
        super.onDestroy();
        this.isRunning=false;
    }
}

