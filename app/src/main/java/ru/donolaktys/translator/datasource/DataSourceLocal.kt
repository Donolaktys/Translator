package geekbrains.ru.translator.model.datasource

import io.reactivex.rxjava3.core.Observable
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.data.DataModel
import ru.donolaktys.translator.datasource.RoomDataBaseImplementation

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    Contract.DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
