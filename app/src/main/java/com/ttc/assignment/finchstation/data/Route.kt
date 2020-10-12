package com.ttc.assignment.finchstation.data

import com.google.gson.annotations.SerializedName

/**
 * Created by edison on 10/12/20.
 */
data class Route(
    @SerializedName("stop_times")
    val stopTimes: List<StopTime>,

    @SerializedName("route_group_id")
    val routeGroupId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("uri")
    val uri: String
)