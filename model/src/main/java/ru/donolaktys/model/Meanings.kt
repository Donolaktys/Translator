package ru.donolaktys.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meanings(
    @Expose @SerializedName("translation") val translation: Translation?,
    @Expose @SerializedName("imageUrl") val imageUrl: String?
) : Parcelable
