package ru.donolaktys.translator.view.words

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.donolaktys.translator.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment(), TextView.OnEditorActionListener {

    private lateinit var searchEditText: TextInputEditText
    private lateinit var clearTextImageView: ImageView
    private var onSearchListener: OnSearchListener? = null
    private var binding: SearchDialogFragmentBinding? = null

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (searchEditText.text != null && !searchEditText.text.toString().isEmpty()) {
                clearTextImageView.visibility = View.VISIBLE
            } else {
                clearTextImageView.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = binding ?: return
        searchEditText = bind.searchEditText
        clearTextImageView = bind.clearTextImageview
        clearTextImageView.visibility = View.GONE
        searchEditText.setOnEditorActionListener(this)
        searchEditText.addTextChangedListener(textWatcher)
        addOnClearClickListener()
    }

    private fun addOnClearClickListener() {
        clearTextImageView.setOnClickListener {
            searchEditText.setText(getEmptyString())
        }
    }

    private fun getEmptyString() : String = ""

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onSearchListener?.onClick(searchEditText.text.toString())
            dismiss()
            return true
        }
        return false
    }

    interface OnSearchListener {
        fun onClick(searchWord: String)
    }

    internal fun setOnSearchListener(listener: OnSearchListener) {
        onSearchListener = listener
    }

    override fun onDestroyView() {
        onSearchListener = null
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }
}
