package ru.donolaktys.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Translation(
    @Expose @SerializedName("text") val translation: String?
    ) : Parcelable
