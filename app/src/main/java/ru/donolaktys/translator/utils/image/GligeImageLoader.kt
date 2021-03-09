package ru.donolaktys.translator.utils.image

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader: ImageLoader<ImageView> {
    override suspend fun loadInto(url: String, container: ImageView) {
        Glide
            .with(container.context)
            .load(url)
            .into(container)
    }
}