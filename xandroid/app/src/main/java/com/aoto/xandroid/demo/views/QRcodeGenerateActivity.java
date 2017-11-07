package com.aoto.xandroid.demo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.aoto.xandroid.DaggerMainComponent;
import com.aoto.xandroid.MainModule;
import com.aoto.xandroid.R;
import com.aoto.xandroid.utils.qrcode.QRCode;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ContentView(R.layout.activity_qecode_generate)
public class QRcodeGenerateActivity extends AppCompatActivity {
    @Inject
    RefWatcher refWatcher;

    @ViewInject(R.id.qrcode_chinese_logo)
    private ImageView mImageViewChineseLogo;
    @ViewInject(R.id.qrcode_chinese)
    private ImageView mImageViewChinese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DaggerMainComponent.create().inject(this);//在没有Module注入时可简写
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        x.view().inject(this);

        createQRCode();
    }

    private void createQRCode() {
        Observable.just(QRCode.createQRCodeWithLogo("赵德华",R.mipmap.grid_camera))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bitmap)->mImageViewChineseLogo.setImageBitmap(bitmap),
                        (e)-> Toast.makeText(QRcodeGenerateActivity.this,e.getMessage(),Toast.LENGTH_LONG).show()
                );
        Observable.just(QRCode.createQRCodeWithoutLogo("赵德华"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bitmap)->mImageViewChinese.setImageBitmap(bitmap),
                        (e)-> Toast.makeText(QRcodeGenerateActivity.this,e.getMessage(),Toast.LENGTH_LONG).show()
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refWatcher.watch(this);
    }
}
