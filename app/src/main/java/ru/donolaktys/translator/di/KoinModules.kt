package ru.donolaktys.translator.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.model.datasource.RetrofitImplementation
import ru.donolaktys.translator.model.datasource.RoomDataBaseImplementation
import ru.donolaktys.translator.model.repository.RepositoryImplementation
import ru.donolaktys.translator.view.words.interactor.WordsFragmentInteractor
import ru.donolaktys.translator.viewmodel.WordsViewModel

val application = module {

    single<Contract.Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImplementation(
        RetrofitImplementation()
    ) }
    single<Contract.Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImplementation(
        RoomDataBaseImplementation()
    ) }
}

val wordScreen = module {
    factory { WordsFragmentInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { WordsViewModel(get()) }
}
