package com.ttc.assignment.finchstation.data

/**
 * Created by edison on 10/14/20.
 */
data class GroupedStopTimes(
    val departureTimes: List<String>,

    val shape: String,

    val nextDepartureTimeStamp: Long,

    val serviceId: Int
) {
    val serviceIdStr: String = serviceId.toString()
}