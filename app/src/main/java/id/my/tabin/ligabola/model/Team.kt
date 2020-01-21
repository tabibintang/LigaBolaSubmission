package id.my.tabin.ligabola.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null,

    @SerializedName("strStadium")
    var stadium: String? = null,

    @SerializedName("strWebsite")
    var website: String? = null,

    @SerializedName("strDescriptionEN")
    var description: String? = null

) : Parcelable