package ru.donolaktys.translator.di

import dagger.Module
import dagger.Provides
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.model.datasource.RetrofitImplementation
import ru.donolaktys.translator.model.datasource.RoomDataBaseImplementation
import ru.donolaktys.translator.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: Contract.DataSource<List<DataModel>>): Contract.Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: Contract.DataSource<List<DataModel>>): Contract.Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): Contract.DataSource<List<DataModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): Contract.DataSource<List<DataModel>> = RoomDataBaseImplementation()
}