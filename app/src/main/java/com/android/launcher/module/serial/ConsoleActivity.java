/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.android.launcher.module.serial;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.launcher.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android_serialport_api.Converter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsoleActivity extends SerialPortActivity {

    @BindView(R.id.et_recept) EditText mReception;
    @BindView(R.id.et_emission) EditText Emission;
    @BindView(R.id.sp_data_type) Spinner mSpinner;
    @BindView(R.id.bt_send) Button mBtSend;
    @BindView(R.id.toolbar) Toolbar mToolbar;
//    TextWatcher mTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.console);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    Emission.addTextChangedListener(mTextWatcher);
//                } else {
//                    Emission.removeTextChangedListener(mTextWatcher);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

    }

    @OnClick(R.id.bt_send)
    public void onClick() {
        CharSequence t = Emission.getText();
        if (t.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入数据",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        char[] text = new char[t.length()];
        for (int i = 0; i < t.length(); i++) {
            text[i] = t.charAt(i);
        }
        try {
                    /*
                     * byte[] b=new byte[]{0x40,0x30,0x31, (byte) 0xD5,(byte)
					 * 0xC5,(byte)0xC8,(byte)0xFD, 0x2C, (byte) 0xD5,(byte)
					 * 0xC5,(byte)0xC8,(byte)0xFD}; mOutputStream.write(b);
					 */
//					byte[] info = new String(text).getBytes("GB2312");
            byte[] info;
            if (mSpinner.getSelectedItemId() == 1) {
                info = t.toString().getBytes("GB2312");
                Log.i("hexstring", Converter.bytesToHexString(info, info.length));
            } else {
                info = Converter.hexStringToBytes(t.toString());
            }
            mOutputStream.write(info);
            System.out.println(Converter.getHexString(info, info.length));
            // mOutputStream.write('\n');

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                // if (mReception != null) {
                try {
                    String data = new String(buffer, 0, size, "GB2312");
                    Log.d(TAG, "onDataReceived-->data=" + data);
                    mReception.append(data);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("mReception=" + new String(buffer, 0, size));
                // }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
