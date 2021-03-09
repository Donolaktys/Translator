package ru.donolaktys.translator.model.datasource

import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.model.data.room.dao.HistoryDao
import ru.donolaktys.translator.utils.convertDataModelSuccessToEntity
import ru.donolaktys.translator.utils.mapHistoryEntityToSearchResult

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
