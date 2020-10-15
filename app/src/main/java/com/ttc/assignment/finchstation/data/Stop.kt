package com.ttc.assignment.finchstation.data

import com.google.gson.annotations.SerializedName

/**
 * Created by edison on 10/12/20.
 */
data class Stop(
    @SerializedName("routes")
    val routes: List<Route>,

    @SerializedName("name")
    val name: String,

    @SerializedName("uri")
    val uri: String,

    @SerializedName("agency")
    val agency: String
)