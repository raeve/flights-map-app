package com.rubenexposito.flightsmap.data.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rubenexposito.flightsmap.data.SharedRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitAdapter(sharedRepository: SharedRepository) {
    val retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor {
                val request = it.request()
                if (request.url().toString().contains("oauth/token")) {
                    return@addInterceptor it.proceed(request)

                }

                val newRequest = request.newBuilder()
                    .header("Authorization", "Bearer " + sharedRepository.getToken())
                    .header("Accept", "application/json")
                    .build()

                return@addInterceptor it.proceed(newRequest)

            }
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        this.retrofit = Retrofit.Builder()
            .baseUrl(NetworkConfig.API_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}