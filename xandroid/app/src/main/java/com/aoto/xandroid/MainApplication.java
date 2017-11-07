package com.aoto.xandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.aoto.xandroid.utils.file.FileUtils;
import com.aoto.xandroid.utils.http.NetWorkUtils;
import com.aoto.xandroid.utils.image.PicassoImageLoader;
import com.aoto.xandroid.utils.qrcode.QRCode;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.x;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MainApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher=setupLeakCanary();//启用LeakCanary

        x.Ext.init(this);//xUtils3框架
        x.Ext.setDebug(false);//xUtils3框架

        settingIamgePicker();//设置IamgePicker参数

        NetWorkUtils.init(MainApplication.this);//初始化网络判断

        FileUtils.init(MainApplication.this);//初始化文件操作

        QRCode.init(MainApplication.this);//初始化生成二维码

    }

    public void settingIamgePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
}

