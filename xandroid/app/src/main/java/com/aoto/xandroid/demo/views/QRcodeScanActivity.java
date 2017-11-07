package com.aoto.xandroid.demo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aoto.xandroid.DaggerMainComponent;
import com.aoto.xandroid.MainModule;
import com.aoto.xandroid.R;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import javax.inject.Inject;

import cn.bingoogolapple.qrcode.core.QRCodeView;

@ContentView(R.layout.activity_qrcode_scan)
public class QRcodeScanActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = "QRcodeScanActivity";

    @Inject
    RefWatcher refWatcher;

    @ViewInject(R.id.zxingview)
    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DaggerMainComponent.create().inject(this);//在没有Module注入时可简写
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        x.view().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
        refWatcher.watch(this);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, "扫描结果", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Event(value = {R.id.btn_scan_start,R.id.btn_scan_stop,R.id.btn_flashlight_start,R.id.btn_flashlight_stop},type = View.OnClickListener.class)
    private  void clickHandle(View view){
        switch (view.getId()) {
            case R.id.btn_scan_start:
                Toast.makeText(this, "开始扫描", Toast.LENGTH_SHORT).show();
                mQRCodeView.startSpot();
                break;
            case R.id.btn_scan_stop:
                Toast.makeText(this, "停止扫描", Toast.LENGTH_SHORT).show();
                mQRCodeView.stopSpot();
                break;
            case R.id.btn_flashlight_start:
                Toast.makeText(this, "打开闪光灯", Toast.LENGTH_SHORT).show();
                mQRCodeView.openFlashlight();
                break;
            case R.id.btn_flashlight_stop:
                Toast.makeText(this, "关闭闪光灯", Toast.LENGTH_SHORT).show();
                mQRCodeView.closeFlashlight();
                break;
        }
    }
}
