package ru.donolaktys.translator.model.data.room

import androidx.room.RoomDatabase
import ru.donolaktys.translator.model.data.room.dao.HistoryDao

@androidx.room.Database(entities = [RoomHistoryWord::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        const val DB_NAME = "ru_donolaktys_translator_database.db"
    }
}