package ru.donolaktys.translator.model.repository

import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.translator.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun putData(appState: AppState) {
        dataSource.putData(appState)
    }
}