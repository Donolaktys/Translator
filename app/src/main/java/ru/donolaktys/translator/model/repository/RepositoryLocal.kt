package ru.donolaktys.translator.model.repository

import ru.donolaktys.translator.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun putData(appState: AppState)
}