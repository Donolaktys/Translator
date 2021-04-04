package ru.donolaktys.repo.repository

import ru.donolaktys.model.DataModel
import ru.donolaktys.repo.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
