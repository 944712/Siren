package com.example.user.siren.Earphone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import com.example.user.siren.MainActivity;
import com.example.user.siren.R;

import java.util.Timer;
import java.util.TimerTask;

public class EarphoneActivity extends Activity {
    private static IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    private static BroadcastReceiver mBroadcastReceiver= null;


    @Override protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_earphone);

            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    AudioManager audio = (AudioManager) getSystemService(EarphoneActivity.this.AUDIO_SERVICE);
                    int systemVolume = audio.getStreamVolume(AudioManager.STREAM_SYSTEM);

                    boolean isEarphoneOn = (intent.getIntExtra("state", 0) > 0) ? true : false;

                    //이어폰이 무조건 연결되었다 해지된 상태여야 함
                    if (isEarphoneOn) {
                        //이어폰 연결되었을 때
                        Log.e("이어폰 log", "Earphone is plugged");

                                Intent popplugon = new Intent(context, PopupPlugOnActivity.class);
                                popplugon.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                context.startActivity(popplugon);

                    }
                    //이어폰 연결되지 않을때
                    else {
                        Log.e("이어폰 log", "Earphone is unPlugged");

                        //팝업창 띄우는 부분
                        PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                        boolean bScreenOn = manager.isInteractive();

                        if (bScreenOn) {

                            Intent popon = new Intent(context, PopupOnActivity.class);
                            popon.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            context.startActivity(popon);

                        }
                        //스크린이 꺼져있을때
                        else {
                                Intent popup = new Intent(getApplicationContext(), PopupActivity.class);
                                popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(popup);
                        }

                    }


                }
            };
            registerReceiver(mBroadcastReceiver, mIntentFilter);

        }

}
