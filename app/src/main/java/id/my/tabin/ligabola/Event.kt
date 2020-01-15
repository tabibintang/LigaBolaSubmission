package id.my.tabin.ligabola

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("idEvent")
    val id: String? = null,
    @SerializedName("strHomeTeam")
    val homeTeam: String? = null,
    @SerializedName("strAwayTeam")
    val awayTeam: String? = null,
    @SerializedName("intHomeScore")
    val homeScore: String? = null,
    @SerializedName("intAwayScore")
    val awayScore: String? = null,
    val homeBadge: String? = null,
    val awayBadge: String? = null,
    @SerializedName("dateEventLocal")
    val dateEventLocal: String? = null,
    @SerializedName("strTimeLocal")
    val timeLocal: String? = null
) : Parcelable