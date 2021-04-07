package ru.donolaktys.repo.room

import androidx.room.RoomDatabase
import ru.donolaktys.repo.room.dao.HistoryDao

@androidx.room.Database(entities = [RoomHistoryWord::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        const val DB_NAME = "ru_donolaktys_translator_database.db"
    }
}