package com.vbeeon.iotdbs.data.remote;


import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json;ty=9")
                .addHeader("Content-Type", "application/json;ty=4")
                .addHeader("X-M2M-RI", "123gsyuuiuihi45")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("X-M2M-Origin", "a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e")
                .addHeader("Accept", "application/json")
                .build();
        return chain.proceed(request);
    }
}
