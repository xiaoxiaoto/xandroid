package com.aoto.xandroid.utils.http;

import com.aoto.xandroid.Constants;
import com.aoto.xandroid.utils.exception.CustomException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 处理无网络异常
 */

public class HttpNoNetWorkHandleIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if(NetWorkUtils.isNetworkConnected()){
            return chain.proceed(chain.request());
        }else{
            throw new CustomException(Constants.NO_NET,"没有网络，请检查网络设置！");
        }
    }
}
