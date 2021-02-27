package ru.donolaktys.translator.di

import dagger.Module
import dagger.Provides
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.data.DataModel
import ru.donolaktys.translator.interactor.WordsFragmentInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Contract.Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Contract.Repository<List<DataModel>>
    ) = WordsFragmentInteractor(repositoryRemote, repositoryLocal)
}