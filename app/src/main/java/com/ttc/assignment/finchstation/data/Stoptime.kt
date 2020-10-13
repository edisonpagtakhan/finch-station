package com.ttc.assignment.finchstation.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by edison on 10/12/20.
 */
@Parcelize
data class StopTime(
    @SerializedName("departure_time")
    val departureTime: String,

    @SerializedName("shape")
    val shape: String,

    @SerializedName("departure_time_stamp")
    val departureTimeStamp: Int,

    @SerializedName("service_id")
    val serviceId: Int
): Parcelable