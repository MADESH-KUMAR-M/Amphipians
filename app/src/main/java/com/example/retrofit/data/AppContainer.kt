package com.example.retrofit.data

import com.example.retrofit.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val repository: Repository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val repository: Repository by lazy {
        DefaultRepository(retrofitService)
    }
}