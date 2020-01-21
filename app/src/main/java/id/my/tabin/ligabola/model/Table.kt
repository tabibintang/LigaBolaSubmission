package id.my.tabin.ligabola.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Table(
    @SerializedName("teamid")
    val teamId: String? = null,
    @SerializedName("name")
    val teamName: String? = null,
    @SerializedName("win")
    val win: String? = null,
    @SerializedName("draw")
    val draw: String? = null,
    @SerializedName("loss")
    val loss: String? = null,
    val teamBadge: String? = null
) : Parcelable