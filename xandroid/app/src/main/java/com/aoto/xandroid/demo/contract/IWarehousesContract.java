package com.aoto.xandroid.demo.contract;

import com.aoto.xandroid.demo.model.WarehousesModel;
import com.aoto.xandroid.utils.view.IBaseView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface IWarehousesContract {
    interface View extends IBaseView{

    }
    interface Presenter {

    }

    interface RetrofitService{
        @GET("warehouses/query")
        Observable<List<WarehousesModel>>  getWarehouses();
        @GET("warehouses/query/{id}")
        Observable<WarehousesModel> getWarehouse(@Path("id") int id);
        @FormUrlEncoded
        @POST("warehouses/query")
        Observable<WarehousesModel> getWarehouseByFilter(@Field("id") int id);
    }
}
