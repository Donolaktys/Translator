package ru.donolaktys.translator.model.data.room.dao

import androidx.room.*
import ru.donolaktys.translator.model.data.room.RoomHistoryWord

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: RoomHistoryWord)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(words: List<RoomHistoryWord>)

    @Update
    suspend fun update(word: RoomHistoryWord)

    @Update
    suspend fun update(words: List<RoomHistoryWord>)

    @Delete
    suspend fun delete(word: RoomHistoryWord)

    @Delete
    suspend fun delete(words: List<RoomHistoryWord>)

    @Query("SELECT * FROM RoomHistoryWord")
    suspend fun getAllSearchHistory() : List<RoomHistoryWord>

    @Query("SELECT * FROM RoomHistoryWord WHERE name = :name LIMIT 1")
    suspend fun findWordByName(name: String): RoomHistoryWord
}