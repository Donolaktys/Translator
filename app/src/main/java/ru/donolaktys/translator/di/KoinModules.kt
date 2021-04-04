package ru.donolaktys.translator.di

import androidx.room.Room
import com.github.terrakok.modo.AppReducer
import com.github.terrakok.modo.Modo
import org.koin.dsl.module
import ru.donolaktys.core.INavigation
import ru.donolaktys.model.DataModel
import ru.donolaktys.repo.room.HistoryDatabase
import ru.donolaktys.repo.datasource.RetrofitImplementation
import ru.donolaktys.repo.datasource.RoomDataBaseImplementation
import ru.donolaktys.repo.repository.Repository
import ru.donolaktys.repo.repository.RepositoryImplementation
import ru.donolaktys.repo.repository.RepositoryImplementationLocal
import ru.donolaktys.repo.repository.RepositoryLocal
import ru.donolaktys.translator.Navigate
import ru.donolaktys.translator.view.history.HistoryFragmentInteractor
import ru.donolaktys.translator.view.words.WordsFragmentInteractor
import ru.donolaktys.translator.view.history.HistoryViewModel
import ru.donolaktys.translator.view.words.WordsViewModel

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, HistoryDatabase.DB_NAME).build() }
    single { get<HistoryDatabase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
    single { Modo(AppReducer(get())) }
    single<INavigation<Modo>> { Navigate(get()) }
}

val wordScreen = module {
    factory { WordsViewModel(get(), get()) }
    factory { WordsFragmentInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get(), get()) }
    factory { HistoryFragmentInteractor(get(), get()) }
}