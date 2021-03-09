package ru.donolaktys.translator.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.donolaktys.translator.R
import ru.donolaktys.translator.databinding.FragmentHistoryBinding
import ru.donolaktys.translator.databinding.LoadingLayoutBinding
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.view.base.BaseFragment

class HistoryFragment : BaseFragment<AppState, HistoryFragmentInteractor>() {

    private var binding: FragmentHistoryBinding? = null
    private var bindLoad: LoadingLayoutBinding? = null
    private val adapter: HistoryFragmentAdapter by lazy { HistoryFragmentAdapter() }
    override lateinit var model: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        bindLoad = LoadingLayoutBinding.inflate(inflater,container,false)
        initViewModel()
        return binding?.root
    }

    private fun initViewModel() {
        if (binding?.historyFragmentRecyclerview?.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, observer)
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
                        adapter?.setData(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    bindLoad?.let {
                        it.progressBarHorizontal.visibility = View.VISIBLE
                        it.progressBarRound.visibility = View.GONE
                        it.progressBarHorizontal.progress = appState.progress
                    }
                } else {
                    bindLoad?.let {
                        it.progressBarHorizontal.visibility = View.GONE
                        it.progressBarRound.visibility = View.VISIBLE
                    }
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
        bindLoad?.loadingFrameLayout?.visibility = View.GONE
    }

    private fun showViewLoading() {
        bindLoad?.loadingFrameLayout?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        binding = null
        bindLoad = null
        super.onDestroy()
    }
}