package com.abramchuk.itbookstore

import android.app.Application
import com.abramchuk.itbookstore.modules.AppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    val diContainer = AppContainer()
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
    companion object{
        lateinit var INSTANCE: App
    }
}