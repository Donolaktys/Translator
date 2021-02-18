package ru.donolaktys.translator.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel (
    @Expose @SerializedName("id") val id: String?,
    @Expose @SerializedName("text") val text: String?,
    @Expose @SerializedName("meanings") val meanings: List<Meanings>?
        ) : Parcelable