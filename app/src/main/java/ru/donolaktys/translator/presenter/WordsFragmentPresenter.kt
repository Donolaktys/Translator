package ru.donolaktys.translator.presenter

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.data.AppState
import ru.donolaktys.translator.interactor.WordsFragmentInteractor
import ru.donolaktys.translator.model.datasource.DataSourceLocal
import ru.donolaktys.translator.model.datasource.DataSourceRemote
import ru.donolaktys.translator.repository.RepositoryImplementation
import ru.donolaktys.translator.rx.SchedulerProvider

class WordsFragmentPresenter<T : AppState, V : Contract.View>(
    private val interactor: WordsFragmentInteractor = WordsFragmentInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Contract.Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}