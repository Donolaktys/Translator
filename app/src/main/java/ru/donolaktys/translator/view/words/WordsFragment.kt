package ru.donolaktys.translator.view.words

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.donolaktys.translator.R
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.databinding.FragmentWordsBinding
import ru.donolaktys.translator.App.TranslatorApp
import ru.donolaktys.translator.view.base.BaseFragment
import ru.donolaktys.translator.view.words.adapter.WordsFragmentAdapter
import ru.donolaktys.translator.viewmodel.WordsViewModel
import javax.inject.Inject

class WordsFragment : BaseFragment<AppState>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: WordsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(WordsViewModel::class.java)
    }

    private var adapter: WordsFragmentAdapter? = null
    private var binding: FragmentWordsBinding? = null

    override fun onAttach(context: Context) {
        TranslatorApp.component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordsBinding.inflate(inflater, container, false)
        binding?.searchFab?.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchListener(object :
                SearchDialogFragment.OnSearchListener {
                override fun onClick(searchWord: String) {
                    viewModel.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        viewModel.subscribe().observe(this, observer)
        return binding?.root
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
                        it.progressBarHorizontal.progress = appState.progress
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

    private val observer = Observer<AppState> { renderData(it) }

    private val onListItemClickListener: WordsFragmentAdapter.OnListItemClickListener =
        object : WordsFragmentAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding?.errorTextview?.text = error ?: getString(R.string.undefined_error)
        binding?.reloadButton?.setOnClickListener {
            viewModel.getData("search", true)
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
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}