package com.android.launcher.module.spi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.launcher.R;

import android_serialport_api.Converter;
import android_spi_api.SpiJNITransfer;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android_spi_api.SpiJNITransfer.test_bytes;

/**
 * Created by wpc on 2017/7/28.
 */

public class Spi_Activity extends AppCompatActivity {
    SpiJNITransfer transfer;
    @BindView(R.id.et_spi_receive) EditText mEtSpiReceive;
    @BindView(R.id.et_spi_send) EditText mEtSpiSend;
    @BindView(R.id.spinner) Spinner mSpinner;
    @BindView(R.id.test) Button mTest;
    @BindView(R.id.bt_spi_send) Button mBtSpiSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi);
        ButterKnife.bind(this);

        transfer = new SpiJNITransfer();
//        final int index = transfer.open(SpiJNITransfer.path);

        mEtSpiSend.setText(Converter.bytesToHexString(test_bytes,test_bytes.length));
        mBtSpiSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer.test();
//                transfer.transfer(index, test_bytes);
            }
        });
    }
}
