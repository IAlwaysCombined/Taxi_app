package com.example.taxi_app.`interface`

import com.example.taxi_app.R
import com.example.taxi_app.response.*
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("maps/api/distancematrix/json")
    fun getCurrentDistanceAsync(
        @Query("origins") origins: String,
        @Query("destinations") destinations: String,
    ): Deferred<CurrentDistanceResponse>

    @GET("maps/api/distancematrix/json")
    fun getCurrentDistanceCenterPointAsync(
        @Query("origins") origins: String,
        @Query("destinations") destinations: String,
    ): Deferred<CurrentDistanceResponse>

    companion object {

        operator fun invoke(): ApiInterface {

            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", APP_ACTIVITY.getString(R.string.google_maps_key))
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://maps.googleapis.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}