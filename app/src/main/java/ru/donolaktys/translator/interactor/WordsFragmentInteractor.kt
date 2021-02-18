package ru.donolaktys.translator.interactor

import io.reactivex.rxjava3.core.Observable
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.data.AppState
import ru.donolaktys.translator.data.DataModel

class WordsFragmentInteractor(
    private val remoteRepository: Contract.Repository<List<DataModel>>,
    private val localRepository: Contract.Repository<List<DataModel>>
) : Contract.Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}