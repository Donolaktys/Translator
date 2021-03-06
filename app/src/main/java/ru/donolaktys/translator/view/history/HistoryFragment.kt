package ru.donolaktys.translator.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import ru.donolaktys.translator.R
import ru.donolaktys.translator.databinding.FragmentHistoryBinding
import ru.donolaktys.translator.databinding.LoadingLayoutBinding
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.utils.BackButtonListener
import ru.donolaktys.translator.view.base.BaseFragment

class HistoryFragment : BaseFragment<AppState, HistoryFragmentInteractor>(), BackButtonListener {

    private var binding: FragmentHistoryBinding? = null
    private val adapter: HistoryFragmentAdapter by lazy { HistoryFragmentAdapter() }
    private val bindLoad : LoadingLayoutBinding by lazy { LoadingLayoutBinding.inflate(layoutInflater) }
    override lateinit var model: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        initViewModel()
        initViews()
        model.getData("", false)
        return binding?.root
    }

    private fun initViewModel() {
        if (binding?.historyFragmentRecyclerview?.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        binding?.historyFragmentRecyclerview?.adapter = adapter
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        adapter.setData(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    bindLoad.progressBarHorizontal.visibility = View.VISIBLE
                    bindLoad.progressBarRound.visibility = View.GONE
                    bindLoad.progressBarHorizontal.progress = appState.progress
                }
            else {
                    bindLoad.progressBarHorizontal.visibility = View.GONE
                    bindLoad.progressBarRound.visibility = View.VISIBLE
            }
        }
        is AppState.Error -> {
            showViewWorking()
            showAlertDialog(getString(R.string.error_stub), appState.error.message)
        }
    }
}

private fun showViewWorking() {
    bindLoad.loadingFrameLayout.visibility = View.GONE
}

private fun showViewLoading() {
    bindLoad.loadingFrameLayout.visibility = View.VISIBLE
}

override fun onDestroy() {
    binding = null
    super.onDestroy()
}

    override fun backPressed(): Boolean {
        model.backClick()
        return true
    }
}