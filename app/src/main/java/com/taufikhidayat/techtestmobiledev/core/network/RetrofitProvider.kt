package com.taufikhidayat.techtestmobiledev.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    fun create(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(OkHttpProvider.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}