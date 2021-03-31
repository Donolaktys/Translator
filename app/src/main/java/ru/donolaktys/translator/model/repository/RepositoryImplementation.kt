package ru.donolaktys.translator.model.repository

import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
