package com.example.retrofit.data

import com.example.retrofit.model.Animal
import com.example.retrofit.network.ApiService

interface Repository {
    suspend fun getInfo() : List<Animal>
}

class DefaultRepository(
    private val apiService: ApiService
): Repository{
    override suspend fun getInfo(): List<Animal> =apiService.getInfo()
}