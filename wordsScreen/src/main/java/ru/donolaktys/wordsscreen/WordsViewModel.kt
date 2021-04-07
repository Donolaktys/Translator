package ru.donolaktys.wordsscreen

import androidx.lifecycle.LiveData
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.exit
import kotlinx.coroutines.launch
import ru.donolaktys.core.INavigation
import ru.donolaktys.core.parseOnlineSearchResults
import ru.donolaktys.model.AppState
import ru.donolaktys.core.viewmodel.BaseViewModel

class WordsViewModel (
    private val interactor: WordsFragmentInteractor,
    private val navigate: INavigation<Modo>
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

    fun openDescriptionScreen(text: String, translation: String, imageUrl: String) {
        navigate.toDescriptionScreen(text, translation, imageUrl)
    }
    fun backClick() {
        navigate.getRouter().exit()
    }
}