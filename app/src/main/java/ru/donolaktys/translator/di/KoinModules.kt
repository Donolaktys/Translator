package ru.donolaktys.translator.di

import androidx.room.Room
import org.koin.dsl.module
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.model.data.room.HistoryDatabase
import ru.donolaktys.translator.model.datasource.RetrofitImplementation
import ru.donolaktys.translator.model.datasource.RoomDataBaseImplementation
import ru.donolaktys.translator.model.repository.Repository
import ru.donolaktys.translator.model.repository.RepositoryImplementation
import ru.donolaktys.translator.model.repository.RepositoryImplementationLocal
import ru.donolaktys.translator.model.repository.RepositoryLocal
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
//    single<ImageLoader<ImageView>> { GlideImageLoader() }
}

val wordScreen = module {
    factory { WordsViewModel(get()) }
    factory { WordsFragmentInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryFragmentInteractor(get(), get()) }
}
