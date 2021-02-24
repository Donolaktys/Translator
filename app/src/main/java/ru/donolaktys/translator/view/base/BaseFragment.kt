package ru.donolaktys.translator.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.donolaktys.translator.Contract
import ru.donolaktys.translator.data.AppState

abstract class BaseFragment<T : AppState> : Fragment(), Contract.View {

    protected lateinit var presenter: Contract.Presenter<T, Contract.View>

    protected abstract fun createPresenter(): Contract.Presenter<T, Contract.View>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}