package ru.donolaktys.translator

import io.reactivex.rxjava3.core.Observable
import ru.donolaktys.translator.data.AppState

interface Contract {

    interface View { fun renderData(appState: AppState) }

    interface Presenter<T : AppState, V : View> {
        fun attachView(view: V)
        fun detachView(view: V)
        fun getData(word: String, isOnline: Boolean)
    }

    interface Interactor<T> {
        fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
    }

    interface Repository<T> {
        fun getData(word: String): Observable<T>
    }

    interface DataSource<T> {
        fun getData(word: String): Observable<T>
    }
}