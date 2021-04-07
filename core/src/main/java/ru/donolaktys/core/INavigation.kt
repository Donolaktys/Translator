package ru.donolaktys.core

interface INavigation<T> {
    fun toWordsScreen()
    fun toHistoryScreen()
    fun toDescriptionScreen(text: String, translation: String, imageUrl: String)
    fun getRouter() : T
}