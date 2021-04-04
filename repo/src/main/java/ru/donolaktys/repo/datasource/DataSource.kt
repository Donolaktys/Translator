package ru.donolaktys.repo.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}