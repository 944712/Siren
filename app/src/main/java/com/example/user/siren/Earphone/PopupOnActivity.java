package com.example.user.siren.Earphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.siren.R;


//이어폰이 연결되지 않았을때 & 화면은 켜져있을 때
public class PopupOnActivity extends Activity{

    private Handler mHandler;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popupon);

        state = findViewById(R.id.button2).isSelected();

            mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();



                    if (state == false) {
                        //이후 sms기능 삽입
                        //
                        //
                        //
                        // 사이렌 기능 삽입
                        MediaPlayer mediaPlayer = MediaPlayer.create(PopupOnActivity.this, R.raw.siren);
                        mediaPlayer.start();

                        //volume maximum
                        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 1);
                    }
                }
            }, 5000);


    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
            return true;

    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


    public void mOnClose(View v) {
       state = true;
       finish();
    }


}
