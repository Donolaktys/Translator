package ru.donolaktys.translator.App

import android.app.Application
import ru.donolaktys.translator.di.AppComponent
import ru.donolaktys.translator.di.DaggerAppComponent

class TranslatorApp : Application() {

    companion object {
        lateinit var instance: TranslatorApp
        val component get() = instance._appComponent
    }

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        _appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}