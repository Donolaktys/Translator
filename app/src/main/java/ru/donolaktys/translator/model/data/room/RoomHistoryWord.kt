package ru.donolaktys.translator.model.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomHistoryWord(
    @PrimaryKey
    var name : String,
    var description : String?
)