package com.example.user.siren;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AutoSMS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Messenger messenger=new Messenger(getApplicationContext());
        messenger.sendMessageTo("01056706112","안뇽");
    }
}

class Messenger{

    private Context mContext;

    public Messenger(Context mContext){
        this.mContext=mContext;
    } //Mesenger 클래스의 생성자, 현재 어플의 정보를 얻어옴

    public void sendMessageTo(String phoneNum, String message){

        SmsManager smsManager=SmsManager.getDefault(); //SmsManager 객체 생성
        smsManager.sendTextMessage(phoneNum,null,message,null,null);

        Toast.makeText(mContext, "문자메시지 전송 성공!", Toast.LENGTH_SHORT).show();
    }
}