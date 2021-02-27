package ru.donolaktys.translator.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.donolaktys.translator.view.words.WordsFragment
import javax.inject.Singleton

@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(wordsFragment: WordsFragment)
}