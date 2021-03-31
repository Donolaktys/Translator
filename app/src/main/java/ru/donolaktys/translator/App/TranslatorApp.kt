package ru.donolaktys.translator.App

import android.app.Application
import com.github.terrakok.modo.AppReducer
import com.github.terrakok.modo.Modo
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.donolaktys.translator.di.*

class TranslatorApp : Application() {

    val modo = Modo(AppReducer(this))

    companion object {
        lateinit var instance: TranslatorApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, wordScreen, historyScreen))
        }
    }
}