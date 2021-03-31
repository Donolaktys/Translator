package ru.donolaktys.translator.model.datasource

import ru.donolaktys.translator.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun putData(appState: AppState)
}