package com.vbeeon.iotdbs.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://parentalcontrol.vsmart.net"
object ApiClient {
    private val retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 30
    private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
    private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor())
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                requestBuilder.addHeader("Content-Type", "application/json;charset=utf-8")
                requestBuilder.addHeader("device_type", "2")
                requestBuilder.addHeader("Accept-Language", Locale.getDefault().language)
//                val token: String = AppUtils.prctrApllication().appComponent.preferencesHelper().getTokenDevice()
//                requestBuilder.addHeader("Authorization", "Bearer $token")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    fun getClient(): ApiInterface {
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(requestInterceptor)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}