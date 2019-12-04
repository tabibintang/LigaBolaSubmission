package id.my.tabin.ligabola

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(val name:String?, val image: Int?, val description:String?) : Parcelable