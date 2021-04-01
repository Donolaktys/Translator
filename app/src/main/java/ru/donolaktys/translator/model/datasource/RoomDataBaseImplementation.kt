package ru.donolaktys.translator.model.datasource

import ru.donolaktys.translator.App.convertDataModelSuccessToEntity
import ru.donolaktys.translator.App.mapHistoryEntityToSearchResult
import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.translator.model.data.room.dao.HistoryDao

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
