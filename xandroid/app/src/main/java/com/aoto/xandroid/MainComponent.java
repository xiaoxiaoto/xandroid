package com.aoto.xandroid;

import com.aoto.xandroid.demo.presenter.WarehousesPresenter;
import com.aoto.xandroid.demo.views.ArcGISActivity;
import com.aoto.xandroid.demo.views.QRcodeGenerateActivity;
import com.aoto.xandroid.demo.views.QRcodeScanActivity;
import com.aoto.xandroid.demo.views.WarehousesActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);

    void inject(WarehousesActivity warehousesActivity);
    void inject(WarehousesPresenter warehousesPresenter);

    void inject(ArcGISActivity arcGISActivity);

    void inject(QRcodeGenerateActivity qRcodeGenerateActivity);

    void inject(QRcodeScanActivity qRcodeScanActivity);
}