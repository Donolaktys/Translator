package ru.donolaktys.translator.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.donolaktys.translator.R
import ru.donolaktys.translator.model.data.AppState
import ru.donolaktys.translator.utils.network.isOnline
import ru.donolaktys.translator.utils.ui.AlertDialogFragment
import ru.donolaktys.translator.viewmodel.BaseViewModel

abstract class BaseFragment <T : AppState> : Fragment() {

    abstract val viewModel : BaseViewModel<T>
    abstract fun renderData(appState: T)

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetworkAvailable = isOnline(requireContext())
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(requireContext())
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(childFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return childFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }
}