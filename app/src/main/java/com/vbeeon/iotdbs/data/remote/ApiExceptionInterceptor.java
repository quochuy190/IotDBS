package com.vbeeon.iotdbs.data.remote;


import com.vbeeon.iotdbs.data.model.ApiException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiExceptionInterceptor implements Interceptor {
    private static final String TAG = "ApiExceptionInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain != null) {
            try {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (!response.isSuccessful()) {
                    try {
                        throw new ApiException(response.code(), "error");
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        } else
            return null;

    }
}
