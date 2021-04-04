package ru.donolaktys.translator.view.words

import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.core.viewmodel.Interactor
import ru.donolaktys.repo.repository.Repository
import ru.donolaktys.repo.repository.RepositoryLocal

class WordsFragmentInteractor (
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
            if (fromRemoteSource) {
                appState = AppState.Success(remoteRepository.getData(word))
                localRepository.putData(appState)
            } else {
                appState = AppState.Success(localRepository.getData(word))
            }
        return appState
    }
}