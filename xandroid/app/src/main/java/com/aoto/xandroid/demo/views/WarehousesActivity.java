package com.aoto.xandroid.demo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aoto.xandroid.DaggerMainComponent;
import com.aoto.xandroid.MainApplication;
import com.aoto.xandroid.MainModule;
import com.aoto.xandroid.R;
import com.aoto.xandroid.demo.presenter.WarehousesPresenter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

@ContentView(R.layout.activity_warehouses)
public class  WarehousesActivity extends AppCompatActivity {
    public static final Integer REQUEST_CODE = 100;
    @Inject
    WarehousesPresenter warehousesPresenter;

    @Inject
    MainApplication mainApplication;

    @Inject
    Retrofit retrofit;

    @Inject
    RefWatcher refWatcher;

    @ViewInject(R.id.btn_scan_qrcode)
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DaggerMainComponent.create().inject(this);//在没有Module注入时可简写
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        x.view().inject(this);

        //warehousesPresenter.test(); //测试ORM

//        IWarehousesContract.RetrofitService service = retrofit.create(IWarehousesContract.RetrofitService.class);//测试网络请求
//        Observable<WarehousesModel> observable  = service.getWarehouse(72);
//        observable
//               .subscribeOn(Schedulers.io())
//               .observeOn(AndroidSchedulers.mainThread())
//               .subscribe((warehouse)->{
//                   String wname = warehouse.getWname();
//                   String address = warehouse.getAddress();
//                },(e)->Log.e("AOTO", "onCreate: "+e.getMessage()));
    }

    @Event(value = {R.id.btn_scan_qrcode,R.id.btn_generate_qrcode,R.id.btn_image_picker,R.id.btn_take_pickers,R.id.btn_arcgis_map},type = View.OnClickListener.class)
    private  void clickHandle(View view){
        switch (view.getId()) {
            case R.id.btn_scan_qrcode://二维码扫瞄
                startActivity(new Intent(this, QRcodeScanActivity.class));
                break;
            case R.id.btn_generate_qrcode://生成二维码
                startActivity(new Intent(this, QRcodeGenerateActivity.class));
                break;
            case R.id.btn_image_picker://相册选择图片
                startActivityForResult(new Intent(this, ImageGridActivity.class), REQUEST_CODE);
                break;
            case R.id.btn_take_pickers://拍照并选择图片
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_arcgis_map://加载arcgis地图
                startActivity(new Intent(this, ArcGISActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                switch (resultCode){
                    case ImagePicker.RESULT_CODE_ITEMS:
                        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        Toast.makeText(this, images.get(0).path, Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refWatcher.watch(this);
    }
}
