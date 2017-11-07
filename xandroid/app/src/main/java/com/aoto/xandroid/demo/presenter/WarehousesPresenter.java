package com.aoto.xandroid.demo.presenter;

import android.util.Log;

import com.aoto.xandroid.demo.contract.IWarehousesContract;
import com.aoto.xandroid.demo.model.WarehousesModel;
import com.aoto.xandroid.utils.orm.BasePresenter;
import com.aoto.xandroid.utils.orm.DbHelper;

import org.xutils.common.util.KeyValue;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/31.
 */
public class WarehousesPresenter extends BasePresenter implements IWarehousesContract.Presenter {
    @Inject
    public WarehousesPresenter() {
    }

    public List<WarehousesModel> queryEntityss() {
        String sql = "select * from warehouses";
        return DbHelper.queryEntitys(super.db, sql, WarehousesModel.class);
    }

    public List<Map<String, String>> queryMaps() {
        String sql = "select * from warehouses";
        return DbHelper.queryMaps(super.db, sql);
    }

    public Map<String, String> queryMap() {
        String sql = "select * from user";
        return DbHelper.queryMap(super.db, sql);
    }


    public void test() {
        WarehousesModel warehouse = new WarehousesModel();
        warehouse.setState("1");
        warehouse.setAddress("北京");
        warehouse.setClassify("1");
        warehouse.setCreateDate("2017-09-24");
        warehouse.setCreateUser(1);
        warehouse.setFlag("1");
        warehouse.setLatitude(105);
        warehouse.setLongitude(78);
        warehouse.setWcode("BJ_HD_001");
        warehouse.setWname("aoto");
        //WarehousesModel warehouseModel = this.saveEntity(warehouse);
        Observable
                .just(this.saveEntity(warehouse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((u)-> Log.e("AOTO", "onNext:"+u.getWname()));


       // List<WarehousesModel> warehouseModels = this.queryEntitys(WarehousesModel.class);
        Observable
                .fromIterable(this.queryEntitys(WarehousesModel.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((u)-> Log.e("AOTO", "onNext:"+ u.getWname()));

        List<WarehousesModel> warehouseModels1 = this.queryEntitysByPage(1, 5, WarehousesModel.class);
        WarehousesModel warehouse1 = new WarehousesModel();
        warehouse1.setId(1);
        List<WarehousesModel> userModels2 = this.queryEntityByFilters(WarehousesModel.class, warehouse1);


        List<Map<String, String>> maps = this.queryMaps();
        Map<String, String> stringMap = this.queryMap();

        WarehousesModel warehouse2 = new WarehousesModel();
        warehouse2.setId(1);
        int i = this.modifyEntity(WarehousesModel.class, warehouse2, new KeyValue("address", "西安"));
        int i1 = this.modifyEntityById(WarehousesModel.class, 1, new KeyValue("address", "武汉"));
    }
}
