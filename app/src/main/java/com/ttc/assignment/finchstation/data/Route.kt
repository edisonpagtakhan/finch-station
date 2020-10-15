package com.ttc.assignment.finchstation.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by edison on 10/12/20.
 */
@Parcelize
data class Route(
    @SerializedName("stop_times")
    val stopTimes: List<StopTime>,

    @SerializedName("route_group_id")
    val routeGroupId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("uri")
    val uri: String
): Parcelable