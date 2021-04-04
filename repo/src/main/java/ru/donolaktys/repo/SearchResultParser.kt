package ru.donolaktys.repo

import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.model.Meanings
import ru.donolaktys.repo.room.RoomHistoryWord

fun mapHistoryEntityToSearchResult(list: List<RoomHistoryWord>): List<ru.donolaktys.model.DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModel(entity.name, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): RoomHistoryWord? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                RoomHistoryWord(searchResult[0].text!!, null)
            }
        }
        else -> null
    }
}
