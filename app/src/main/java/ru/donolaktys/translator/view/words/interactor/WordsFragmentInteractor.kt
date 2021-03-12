package ru.donolaktys.translator.view.words.interactor

import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.model.data.DataModel

class WordsFragmentInteractor (
    private val remoteRepository: Contract.Repository<List<DataModel>>,
    private val localRepository: Contract.Repository<List<DataModel>>
) : Contract.Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}