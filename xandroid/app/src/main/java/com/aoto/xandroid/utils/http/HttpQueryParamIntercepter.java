package com.aoto.xandroid.utils.http;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http参数拦截器主要作用是添加公共参数
 */

public class HttpQueryParamIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // 添加公共的新的参数
        HttpUrl.Builder authorizedUrlBuilder = request.url().newBuilder();
        Map<String, Object> globalParams = new HashMap<>();//这里是我们的公共参数，根据自己项目实际情况获得，具体添加什么样的参数，在这里做逻辑判断即可
        Iterator it = globalParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            //如果是中文/其他字符，会直接把字符串用BASE64加密，
            String s = URLDecoder.decode(String.valueOf(entry.getValue()));
            authorizedUrlBuilder.addQueryParameter((String) entry.getKey(), s);
        }
        //生成新的请求
        Request newrequest = request.newBuilder()
                .method(request.method(), request.body())
                .url(authorizedUrlBuilder.build())
                .build();
        return chain.proceed(newrequest);
    }
}
