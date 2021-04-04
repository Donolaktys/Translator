package ru.donolaktys.translator.view.history

import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.repo.repository.Repository
import ru.donolaktys.repo.repository.RepositoryLocal
import ru.donolaktys.core.viewmodel.Interactor

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