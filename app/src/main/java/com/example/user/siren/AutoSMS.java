package com.example.user.siren;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.siren.Gpsinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoSMS extends AppCompatActivity {
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    Intent intent;
    // GPSTracker class
    private Gpsinfo gps;

//    public MainActivity phoneNum;


    //전화번호로 들어갈 값을 number로 선언하고 임의로 내번호를 넣음
    public String number;
    public String msgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //연락처 전달받는 함
        intent = getIntent(); //getIntent()로 받을준비
        number = intent.getStringExtra("num2");
//
//        Log.v("AutoSMS","phone : " + number);





        final Geocoder geocoder = new Geocoder(this);

        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        gps = new Gpsinfo(AutoSMS.this);



        //위도, 경도 변환 후 주소값 보여주기
        List<Address> list = null;
        try {
            double d1 = Double.parseDouble(String.valueOf(gps.getLatitude()));
            double d2 = Double.parseDouble(String.valueOf(gps.getLongitude()));

            list = geocoder.getFromLocation(
                    d1, // 위도
                    d2, // 경도
                    10); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size()==0) {
                msgText = "해당되는 주소 정보는 없습니다";
            } else {
                //현재위치받아온것을 msgText에 저장함
                msgText = list.get(0).toString();
            }
        }

        callPermission();


        //Messenger 함수 불러오는 곳
        Messenger messenger=new Messenger(getApplicationContext());
        messenger.sendMessageTo(number,msgText);


    }

    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }


}

class Messenger {
    private Context mContext;


    public Messenger(Context mContext) {
        this.mContext = mContext;
    }
    //Mesenger 클래스의 생성자, 현재 어플의 정보를 얻어옴

    public void sendMessageTo(String number, String msgText) {

        SmsManager smsManager = SmsManager.getDefault(); //SmsManager 객체 생성

        // 140자 이상 일때 mms로 보내는 방법
        if (msgText.length() > 140) {
            ArrayList msgTexts = smsManager.divideMessage(msgText);

            //number에 전화번호가 들어감
            smsManager.sendMultipartTextMessage(number, null, msgTexts, null, null);

        } else {
            //140미만일때 sms로 보내는 방법
            try {

                //number에 전화번호가 들어감
                smsManager.sendTextMessage(number, null, msgText, null, null);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }
    }
}