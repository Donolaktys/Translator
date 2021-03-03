package ru.donolaktys.translator.App

import android.app.Application
import org.koin.core.context.startKoin
import ru.donolaktys.translator.di.application
import ru.donolaktys.translator.di.wordScreen

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, wordScreen))
        }
    }
}