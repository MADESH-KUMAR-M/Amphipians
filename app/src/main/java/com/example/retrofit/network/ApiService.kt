package com.example.retrofit.network

import com.example.retrofit.model.Animal
import retrofit2.http.GET

interface ApiService {
    @GET("amphibians")
    suspend fun getInfo(): List<Animal>
}