package ru.donolaktys.translator.view.history

import androidx.lifecycle.LiveData
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.back
import com.github.terrakok.modo.backTo
import com.github.terrakok.modo.exit
import kotlinx.coroutines.launch
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.utils.parseLocalSearchResults
import ru.donolaktys.translator.viewmodel.BaseViewModel

class HistoryViewModel(
    private val interactor: HistoryFragmentInteractor,
    private val modo: Modo
) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }

    fun backClick() {
        modo.back()
    }
}