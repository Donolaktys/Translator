package ru.donolaktys.translator.view.base

import androidx.fragment.app.Fragment
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.viewmodel.BaseViewModel

abstract class BaseFragment<T : AppState> : Fragment() {

    abstract val viewModel : BaseViewModel<T>
    abstract fun renderData(appState: T)
}