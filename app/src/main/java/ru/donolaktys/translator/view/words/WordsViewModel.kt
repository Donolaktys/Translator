package ru.donolaktys.translator.view.words

import androidx.lifecycle.LiveData
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.exit
import com.github.terrakok.modo.forward
import kotlinx.coroutines.launch
import ru.donolaktys.translator.Screens
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.utils.parseOnlineSearchResults
import ru.donolaktys.translator.viewmodel.BaseViewModel

class WordsViewModel (
    private val interactor: WordsFragmentInteractor,
    private val modo: Modo
    ) : BaseViewModel<AppState>() {

    private val liveDataToObserve: LiveData<AppState> = _mutableLiveData

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataToObserve
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(parseOnlineSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }

    fun openDescriptionScreen(dataModel: DataModel) {
        modo.forward(Screens.DescriptionScreen(dataModel))
    }
    fun backClick() {
        modo.exit()
    }
}