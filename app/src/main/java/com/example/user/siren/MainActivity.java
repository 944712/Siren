package com.example.user.siren;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.user.siren.PopupActivity;
import com.example.user.siren.PopupOnActivity;
import com.example.user.siren.PopupPlugOnActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    private static IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    private static BroadcastReceiver mBroadcastReceiver= null;

    String name;
    String number;

    //출력용
    StringBuffer buffer1 = new StringBuffer();
    String data1 = null;
    FileInputStream fis1 = null;
    TextView contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_add).setOnClickListener(mClickListener);
        contact = (TextView) findViewById(R.id.contact);


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AudioManager audio = (AudioManager) getSystemService(MainActivity.this.AUDIO_SERVICE);
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




        try {
            fis1 = openFileInput("name.txt");
            BufferedReader iReader = new BufferedReader(new InputStreamReader((fis1)));
            data1 = iReader.readLine();
            while (data1 != null) {
                buffer1.append(data1);
                data1 = iReader.readLine();
            }
            buffer1.append("\n");
            iReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis1 = openFileInput("number.txt");
            BufferedReader iReader = new BufferedReader(new InputStreamReader((fis1)));
            data1 = iReader.readLine();
            while (data1 != null) {
                buffer1.append(data1);
                data1 = iReader.readLine();
            }
            buffer1.append("\n");
            iReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contact.setText(buffer1.toString() + "\n");




    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            number = cursor.getString(1);   //1은 번호를 받아옵니다.
            cursor.close();
        }
        //저장
        super.onActivityResult(requestCode, resultCode, data);

        String inputName = name.toString();
        String inputNum = number.toString();

        FileOutputStream fos1 = null;
        FileOutputStream fos2 = null;
        try {
            fos1 = openFileOutput("name.txt", Context.MODE_PRIVATE);
            fos1.write(inputName.getBytes());
            fos1.close();
            ;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos2 = openFileOutput("number.txt", Context.MODE_PRIVATE);
            fos2.write(inputNum.getBytes());
            fos2.close();
            ;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //출력
        try {
            fis1 = openFileInput("name.txt");
            BufferedReader iReader = new BufferedReader(new InputStreamReader((fis1)));
            data1 = iReader.readLine();
            while (data1 != null) {
                buffer1.append(data1);
                data1 = iReader.readLine();
            }
            buffer1.append("\n");
            iReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis1 = openFileInput("number.txt");
            BufferedReader iReader = new BufferedReader(new InputStreamReader((fis1)));
            data1 = iReader.readLine();
            while (data1 != null) {
                buffer1.append(data1);
                data1 = iReader.readLine();
            }
            buffer1.append("\n");
            iReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contact.setText(buffer1.toString() + "\n");
    }
}

