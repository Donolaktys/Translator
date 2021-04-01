package ru.donolaktys.translator.model.repository

import ru.donolaktys.model.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun putData(appState: AppState)
}