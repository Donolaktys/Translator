package ru.donolaktys.repo.datasource

import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.repo.convertDataModelSuccessToEntity
import ru.donolaktys.repo.mapHistoryEntityToSearchResult
import ru.donolaktys.repo.room.dao.HistoryDao

class RoomDataBaseImplementation(private val dao: HistoryDao) : DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(dao.getAllSearchHistory())
    }

    override suspend fun putData(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            dao.insert(it)
        }
    }
}
