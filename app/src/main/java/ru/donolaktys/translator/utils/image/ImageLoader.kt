package ru.donolaktys.translator.utils.image

interface ImageLoader<T> {
    suspend fun loadInto(url: String, container: T)
}