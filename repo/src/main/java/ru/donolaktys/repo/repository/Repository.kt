package ru.donolaktys.repo.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}