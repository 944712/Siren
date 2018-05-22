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


public class MainActivity extends AppCompatActivity {

    String name;
    String number;
    private ArrayList<Map<String, String>> dataList;
    private ListView mListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_add).setOnClickListener(mClickListener);
        mListview = (ListView) findViewById(R.id.list_num);


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
        dataList = new ArrayList<Map<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        super.onActivityResult(requestCode, resultCode, data); //간단하게 리스트 추가
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "phone"},
                new int[]{android.R.id.text1, android.R.id.text2}) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);//0은 이름을 얻어옵니다
            map.put("name", name);
            number = cursor.getString(1);   //1은 번호를 받아옵니다.
            map.put("phone", number);
            dataList.add(map);
            cursor.close();

        }
        adapter.notifyDataSetChanged();
        mListview.setAdapter(adapter);

    }
}
