package com.abramchuk.itbookstore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
    companion object{
        lateinit var INSTANCE: App
    }
}