package ru.donolaktys.translator.viewmodel

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.observers.DisposableObserver
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.data.AppState
import ru.donolaktys.translator.interactor.WordsFragmentInteractor
import ru.donolaktys.translator.model.datasource.DataSourceLocal
import ru.donolaktys.translator.model.datasource.DataSourceRemote
import ru.donolaktys.translator.repository.RepositoryImplementation

class WordsViewModel (
    private val interactor: Contract.Interactor<AppState> = WordsFragmentInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataToObserve
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = state
                liveDataToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}