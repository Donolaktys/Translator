package ru.donolaktys.translator.model.datasource

import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.DataModel

class RoomDataBaseImplementation : Contract.DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
