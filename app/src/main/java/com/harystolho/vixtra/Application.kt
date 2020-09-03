package com.harystolho.vixtra

import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@)
            modules(listOf(appInject, viewModelModule, interactorModule, repositoryModule))
        }
    }

}