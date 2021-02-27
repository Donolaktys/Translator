package ru.donolaktys.translator.di

import android.app.Application

class TranslatorApp : Application() {

    companion object {
        lateinit var instance: TranslatorApp
        val component get() = instance._appComponent
    }

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        _appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}