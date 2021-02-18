package ru.donolaktys.translator.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meanings(
    @Expose @SerializedName("id") val id: String?,
    @Expose @SerializedName("translation") val translation: Translation?,
    @Expose @SerializedName("imageUrl") val imageUrl: String?
) : Parcelable
