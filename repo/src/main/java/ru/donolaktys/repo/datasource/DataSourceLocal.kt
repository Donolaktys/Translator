package ru.donolaktys.repo.datasource

import ru.donolaktys.model.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun putData(appState: AppState)
}