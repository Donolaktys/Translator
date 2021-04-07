package ru.donolaktys.repo.datasource

import ru.donolaktys.model.DataModel
import ru.donolaktys.repo.api.ApiService

class RetrofitImplementation(private val apiService: ApiService) : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return apiService.search(word).await()
    }
}
