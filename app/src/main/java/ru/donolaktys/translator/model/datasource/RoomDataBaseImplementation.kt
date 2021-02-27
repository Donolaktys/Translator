package ru.donolaktys.translator.model.datasource

import io.reactivex.rxjava3.core.Observable
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.DataModel

class RoomDataBaseImplementation : Contract.DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
