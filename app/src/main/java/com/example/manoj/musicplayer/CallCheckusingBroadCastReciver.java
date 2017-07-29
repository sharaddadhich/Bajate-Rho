package com.example.manoj.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Manoj on 7/29/2017.
 */

public class CallCheckusingBroadCastReciver extends BroadcastReceiver {

    private int lastState = TelephonyManager.CALL_STATE_IDLE;
    private boolean isIncoming;
    private Date callStart;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL"))
        {
            Toast.makeText(context, "Outgoing Call Start", Toast.LENGTH_SHORT).show();
            //PlayMusicActivity.mp.pause();
        }
        else
        {
            String stateString = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

            int state = 0;
            if(stateString.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                Toast.makeText(context, "Idle State", Toast.LENGTH_SHORT).show();
                state  = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateString.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                Toast.makeText(context, "OffHook", Toast.LENGTH_SHORT).show();
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateString.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                Toast.makeText(context, "Ringing", Toast.LENGTH_SHORT).show();
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            OnCallStateChanged(context,state);
        }
    }

    public void OnCallStateChanged(Context context,int state)
    {
        if(lastState==state)
        {
            PlayMusicActivity.mp.start();
            //No change hence did nothing
            return;
        }
         switch (state)
         {
             case TelephonyManager.CALL_STATE_RINGING:
                 isIncoming =true;
                 callStart = new Date();
                 OnincomingcallStarted(context,callStart);
                 break;

             case TelephonyManager.CALL_STATE_OFFHOOK:
                 //if reciver picks incoming call do nothing
                 if(lastState!=TelephonyManager.CALL_STATE_RINGING)
                 {
                     //then it is an outgoing call
                     callStart = new Date();
                     isIncoming = false;
                     OnoutgoingcallStarted(context,callStart);
                 }
                 break;

             case TelephonyManager.CALL_STATE_IDLE:

                 if(lastState == TelephonyManager.CALL_STATE_RINGING)
                 {
                     //thien it is a missed call

                     Onmissedcall(context,callStart);
                 }
                 else if(lastState==TelephonyManager.CALL_STATE_OFFHOOK)
                 {
                     Toast.makeText(context, "Outgoing Call Has Ended", Toast.LENGTH_SHORT).show();
                     OnoutgoingcallEnded(context,callStart,new Date());
                     //then either of the cases of incoming or outgiong but is now hung up
//                     if(isIncoming)
//                     {
//                         OnincomingcallEnded(context,callStart,new Date());
//                     }
//                     else
//                     {
//                         OnoutgoingcallEnded(context,callStart,new Date());
//                     }
                 }
                 break;

         }
        lastState = state;
    }

    public void OnincomingcallStarted(Context context,Date dateStart){
            Toast.makeText(context, "Incoming Call", Toast.LENGTH_SHORT).show();
            PlayMusicActivity.mp.pause();
    }

    public void OnoutgoingcallStarted(Context context,Date dateStart){
        Toast.makeText(context, "Outgoing Call Start ho gyi", Toast.LENGTH_SHORT).show();
        PlayMusicActivity.mp.pause();
    }

    public void OnincomingcallEnded(Context context,Date dateStart, Date end){
        Toast.makeText(context, "Incoming Call Ended", Toast.LENGTH_SHORT).show();
        PlayMusicActivity.mp.start();
    }

    public void OnoutgoingcallEnded(Context context,Date dateStart, Date end){
        Toast.makeText(context, "Outgoing Call has Ended", Toast.LENGTH_SHORT).show();
        PlayMusicActivity.mp.start();
    }

    public void Onmissedcall(Context context,Date dateStart){
        Toast.makeText(context, "U mIssed the Call", Toast.LENGTH_SHORT).show();
        PlayMusicActivity.mp.start();
    }

}
