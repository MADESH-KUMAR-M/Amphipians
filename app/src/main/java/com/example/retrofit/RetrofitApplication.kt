package com.example.retrofit

import android.app.Application
import com.example.retrofit.data.AppContainer
import com.example.retrofit.data.DefaultAppContainer

class RetrofitApplication: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}