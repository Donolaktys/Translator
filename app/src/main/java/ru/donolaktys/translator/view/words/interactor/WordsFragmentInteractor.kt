package ru.donolaktys.translator.view.words.interactor

import io.reactivex.rxjava3.core.Observable
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.di.NAME_LOCAL
import ru.donolaktys.translator.di.NAME_REMOTE
import javax.inject.Inject
import javax.inject.Named

class WordsFragmentInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Contract.Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: Contract.Repository<List<DataModel>>
) : Contract.Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}