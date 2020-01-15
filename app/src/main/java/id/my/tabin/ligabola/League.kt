package id.my.tabin.ligabola

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
    @SerializedName("idLeague")
    val id: String? = null,
    @SerializedName("strLeague")
    val name: String?,
    val image: Int?,
    @SerializedName("strBadge")
    val badge: String?,
    @SerializedName("strDescriptionEN")
    val description: String?
) : Parcelable