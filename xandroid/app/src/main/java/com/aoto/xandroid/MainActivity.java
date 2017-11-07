package com.aoto.xandroid;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aoto.xandroid.demo.views.WarehousesActivity;
import com.aoto.xandroid.utils.file.FileUtils;
import com.squareup.leakcanary.RefWatcher;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";

    @Inject
    RefWatcher refWatcher;

    SharedPreferences sharepreferences =null;
    SharedPreferences.Editor edit =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DaggerMainComponent.create().inject(this);//在没有Module注入时可简写
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        x.view().inject(this);

        sharepreferences =this.getSharedPreferences("check", MODE_PRIVATE);
        edit =sharepreferences.edit();

        appLoadJudge();
    }
    
    /**
     * 初始化应用
     */
    public void initApplication() {
        //申请运行时权限
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        //初始化项目目录
                        Observable.fromIterable(Arrays.asList(new String[]{"XZQH.tpk"}))
                                .map(item->FileUtils.packAssetsFiles2ExternalStorage(item,Constants.MAP_TPKPATH))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(rs-> {
                                    if(rs){
                                        edit.putBoolean("fristLoad", false);
                                        edit.commit();
                                        Intent intent = new Intent(this, WarehousesActivity.class);
                                        startActivity(intent);
                                    }
                                },e->Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show());
                    } else {
                        Toast.makeText(this,"权限申请失败",Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * 初始加载APP规则
     */
    public void appLoadJudge() {
        boolean fristLoad=sharepreferences.getBoolean("fristLoad", true);//从SharedPreferences中获取是否第一次启动   默认为true
        if (fristLoad) {
            initApplication();
        }else {
            Intent intent = new Intent(this, WarehousesActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refWatcher.watch(this);
    }
}
