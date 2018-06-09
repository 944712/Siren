package com.example.user.siren;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.user.siren.AutoSMS;
import com.example.user.siren.R;


//이어폰이 연결되지 않았을때 & 화면이 꺼져있을때
public class PopupActivity extends Activity {

    private Handler mHandler;
    private boolean state2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);

        state2 = findViewById(R.id.button).isSelected();

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();



                if (state2 == false) {

                    // 사이렌 기능 삽입
                   // MediaPlayer mediaPlayer = MediaPlayer.create(PopupActivity.this, R.raw.siren);
                   // mediaPlayer.start();

                    //volume maximum
                    //AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                  //  am.setStreamVolume(AudioManager.STREAM_MUSIC, 1, 1);

                    Intent msg = new Intent(getApplicationContext(), AutoSMS.class);
                    startActivity(msg);
                }
            }
        }, 5000);




    }

        @Override
        public boolean onTouchEvent (MotionEvent event){
            //바깥레이어 클릭시 안닫히게
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                return false;
            }
            return true;
        }

        @Override
        public void onBackPressed () {
            //안드로이드 백버튼 막기
            return;
        }


    public void mOnClose(View v) {
        state2 = true;
        finish();
    }


}
