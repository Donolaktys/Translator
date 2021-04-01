package ru.donolaktys.translator.view.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import ru.donolaktys.translator.R
import ru.donolaktys.model.AppState
import ru.donolaktys.model.DataModel
import ru.donolaktys.translator.databinding.FragmentWordsBinding
import ru.donolaktys.translator.view.base.BaseFragment
import ru.donolaktys.utils.BackButtonListener

class WordsFragment : BaseFragment<AppState, WordsFragmentInteractor>(),
    BackButtonListener {

    override lateinit var model: WordsViewModel
    private var adapter: WordsFragmentAdapter? = null
    private var binding: FragmentWordsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordsBinding.inflate(inflater, container, false)
        initViewModel()
        binding?.searchFab?.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchListener(object :
                SearchDialogFragment.OnSearchListener {
                override fun onClick(searchWord: String) {
                    isNetworkAvailable = ru.donolaktys.utils.network.isOnline(requireContext())
                    if (isNetworkAvailable) {
                        model.getData(searchWord, true)
                    } else {
                        showNoInternetConnectionDialog()
                    }

                }
            })
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        return binding?.root
    }

    private fun initViewModel() {
        if (binding?.wordsFragmentRecyclerview?.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: WordsViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel: List<DataModel>? = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    adapter?.setData(dataModel)
                        ?: binding?.wordsFragmentRecyclerview?.let {
                            it.layoutManager = LinearLayoutManager(requireContext())
                            it.adapter = WordsFragmentAdapter(onListItemClickListener, dataModel)
                        }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                binding?.let {
                    if (appState.progress != null) {
                        it.progressBarHorizontal.visibility = View.VISIBLE
                        it.progressBarRound.visibility = View.GONE
                        it.progressBarHorizontal.progress = appState.progress ?: 0
                    } else {
                        it.progressBarHorizontal.visibility = View.GONE
                        it.progressBarRound.visibility = View.VISIBLE
                    }
                }

            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private val onListItemClickListener: WordsFragmentAdapter.OnListItemClickListener =
        object : WordsFragmentAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                model.openDescriptionScreen(data)
            }
        }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding?.errorTextview?.text = error ?: getString(R.string.undefined_error)
        binding?.reloadButton?.setOnClickListener {
            isNetworkAvailable = ru.donolaktys.utils.network.isOnline(requireContext())
            if (isNetworkAvailable) {
                model.getData("search", isNetworkAvailable)
            } else {
                showNoInternetConnectionDialog()
            }
        }
    }

    private fun showViewSuccess() {
        binding?.let {
            it.successLinearLayout.visibility = View.VISIBLE
            it.loadingFrameLayout.visibility = View.GONE
            it.errorLinearLayout.visibility = View.GONE
        }
    }

    private fun showViewLoading() {
        binding?.let {
            it.successLinearLayout.visibility = View.GONE
            it.loadingFrameLayout.visibility = View.VISIBLE
            it.errorLinearLayout.visibility = View.GONE
        }
    }

    private fun showViewError() {
        binding?.let {
            it.successLinearLayout.visibility = View.GONE
            it.loadingFrameLayout.visibility = View.GONE
            it.errorLinearLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        binding = null
        adapter = null
        super.onDestroy()
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "ru.donolaktys.translator.view.words.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
    }

    override fun backPressed(): Boolean {
        model.backClick()
        return true
    }
}