package id.my.tabin.ligabola

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("idEvent")
    val id: String? = null,
    @SerializedName("strEvent")
    val eventName: String? = null,
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
    @SerializedName("dateEvent")
    val dateEventLocal: String? = null,
    @SerializedName("strTimeLocal")
    val timeLocal: String? = null,
    @SerializedName("intRound")
    val round: String? = null,
    @SerializedName("strHomeFormation")
    val homeFormation: String? = null,
    @SerializedName("strHomeLineupGoalkeeper")
    val homeKeeper: String? = null,
    @SerializedName("strHomeLineupDefense")
    val homeDefense: String? = null,
    @SerializedName("strHomeLineupMidfield")
    val homeMidField: String? = null,
    @SerializedName("strHomeLineupForward")
    val homeForward: String? = null,
    @SerializedName("strHomeLineupSubstitutes")
    val homeSubtitute: String? = null,
    @SerializedName("strHomeGoalDetails")
    val homeGoal: String? = null,
    @SerializedName("strAwayFormation")
    val awayFormation: String? = null,
    @SerializedName("strAwayLineupGoalkeeper")
    val awayKeeper: String? = null,
    @SerializedName("strAwayLineupDefense")
    val awayDefense: String? = null,
    @SerializedName("strAwayLineupMidfield")
    val awayMidField: String? = null,
    @SerializedName("strAwayLineupForward")
    val awayForward: String? = null,
    @SerializedName("strAwayLineupSubstitutes")
    val awaySubtitute: String? = null,
    @SerializedName("strAwayGoalDetails")
    val awayGoal: String? = null

    ) : Parcelable