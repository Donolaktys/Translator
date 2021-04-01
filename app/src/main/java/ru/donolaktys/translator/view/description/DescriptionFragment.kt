package ru.donolaktys.translator.view.description

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.multidex.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.back
import org.koin.android.ext.android.inject
import ru.donolaktys.translator.R
import ru.donolaktys.translator.databinding.FragmentDescriptionBinding
import ru.donolaktys.model.DataModel
import ru.donolaktys.utils.BackButtonListener
import ru.donolaktys.utils.network.isOnline
import ru.donolaktys.utils.ui.AlertDialogFragment

class DescriptionFragment : Fragment(), BackButtonListener {

    private val modo: Modo by inject()
    private var binding: FragmentDescriptionBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.descriptionScreenSwipeRefreshLayout?.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    private fun setData() {
        val bundle = arguments
        binding?.descriptionHeader?.text = bundle?.getString(WORD_TAG)
        binding?.descriptionTextview?.text = bundle?.getString(DESCRIPTION_TAG)
        val imageLink = bundle?.getString(URL_TAG)
        if (!imageLink.isNullOrBlank()) {
            useGlideToLoadPhoto(binding?.descriptionImageview as ImageView, imageLink)
        }
    }

    private fun startLoadingOrShowError() {
        if (isOnline(requireContext())) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                childFragmentManager,
                DIALOG_FRAGMENT_TAG
            )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        val dssrl = binding?.descriptionScreenSwipeRefreshLayout ?: return
        if (dssrl.isRefreshing) {
            dssrl.isRefreshing = false
        }
    }

    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_no_photo_vector)
                    .centerCrop()
            )
            .into(imageView)
    }

    companion object {
        private const val APP_ID = BuildConfig.APPLICATION_ID
        private const val DIALOG_FRAGMENT_TAG = APP_ID + ".DIALOG_FRAGMENT_TAG"

        private const val WORD_TAG = APP_ID + ".WORD_TAG"
        private const val DESCRIPTION_TAG = APP_ID + ".DESCRIPTION_TAG"
        private const val URL_TAG = APP_ID + ".URL_TAG"

        fun newInstance(data: DataModel) = DescriptionFragment().apply {
            arguments = Bundle().apply {
                putString(WORD_TAG, data.text)
                putString(DESCRIPTION_TAG, data.meanings?.get(0)?.translation?.translation)
                putString(URL_TAG, data.meanings?.get(0)?.imageUrl)
            }
        }
    }

    override fun backPressed(): Boolean {
        modo.back()
        return true
    }
}
