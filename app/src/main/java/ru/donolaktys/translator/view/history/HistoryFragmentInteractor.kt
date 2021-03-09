package ru.donolaktys.translator.view.history

import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.model.repository.Repository
import ru.donolaktys.translator.model.repository.RepositoryLocal
import ru.donolaktys.translator.viewmodel.Interactor

class HistoryFragmentInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState
    }

}