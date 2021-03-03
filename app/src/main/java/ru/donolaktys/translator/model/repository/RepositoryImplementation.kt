package ru.donolaktys.translator.model.repository

import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.DataModel

class RepositoryImplementation(private val dataSource: Contract.DataSource<List<DataModel>>) :
    Contract.Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
