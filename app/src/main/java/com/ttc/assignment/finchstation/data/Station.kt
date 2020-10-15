package com.ttc.assignment.finchstation.data

import com.google.gson.annotations.SerializedName

/**
 * Created by edison on 10/12/20.
 */
data class Station(
    @SerializedName("time")
    val time: Long,

    @SerializedName("stops")
    val stops: List<Stop>,

    @SerializedName("uri")
    val uri: String,

    @SerializedName("name")
    val name: String
)