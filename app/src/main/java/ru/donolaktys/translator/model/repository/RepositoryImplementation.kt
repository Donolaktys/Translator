package ru.donolaktys.translator.model.repository

import io.reactivex.rxjava3.core.Observable
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.DataModel

class RepositoryImplementation(private val dataSource: Contract.DataSource<List<DataModel>>) :
    Contract.Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
