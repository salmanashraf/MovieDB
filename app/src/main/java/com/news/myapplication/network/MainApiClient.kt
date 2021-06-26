package com.news.myapplication.network

import com.news.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MainApiClient {

    private var retrofit: Retrofit
    private lateinit var movieApi: ApiServices

    init {
        retrofit = initRetrofit()
    }

    private fun initRetrofit(): Retrofit {
        // Set logging level
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(loggingInterceptor)
        val okHttpClient = okHttpBuilder.build()

        val retrofitInstance = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        movieApi = retrofitInstance.create(ApiServices::class.java)

        return retrofitInstance
    }

    fun execute(): ApiServices {
        return movieApi
    }

}