package com.aoto.xandroid;

import android.content.Context;

import com.aoto.xandroid.utils.gson.GsonConverterFactory;
import com.aoto.xandroid.utils.http.HttpNoNetWorkHandleIntercepter;
import com.aoto.xandroid.utils.http.HttpQueryParamIntercepter;
import com.aoto.xandroid.utils.orm.DbHelper;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.DbManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class MainModule {
    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    MainApplication provideMainApplication() {
        return (MainApplication) context.getApplicationContext();
    }

    @Provides
    @Singleton
    RefWatcher provideRefWatcher() {
        return ((MainApplication)context.getApplicationContext()).refWatcher;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpNoNetWorkHandleIntercepter())
                .addInterceptor(new HttpQueryParamIntercepter())//自定义的拦截器，用于添加公共参数
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//拦截器，用于日志的打印;
                .connectTimeout(Constants.HTTP_TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(Constants.HTTP_TIMEOUT_WRITE,TimeUnit.SECONDS)
                .readTimeout(Constants.HTTP_TIMEOUT_READ, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//失败重连
        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Constants.HTTP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                .build();
    }

    @Provides
    @Singleton
    DbManager provideDbManager() {
        return DbHelper.initDB();
    }

}