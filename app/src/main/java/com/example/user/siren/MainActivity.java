package com.example.user.siren;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

