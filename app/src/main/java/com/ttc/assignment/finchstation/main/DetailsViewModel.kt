package com.ttc.assignment.finchstation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ttc.assignment.finchstation.data.GroupedStopTimes
import com.ttc.assignment.finchstation.data.Route
import com.ttc.assignment.finchstation.data.StopTime

/**
 * Created by edison on 10/14/20.
 */
class DetailsViewModel : ViewModel() {

    val routeName: LiveData<String>
        get() = name

    val stopTimes: LiveData<List<GroupedStopTimes>>
        get() = groupedStopTimes

    private val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val groupedStopTimes: MutableLiveData<List<GroupedStopTimes>> by lazy {
        MutableLiveData<List<GroupedStopTimes>>()
    }

    private var route: Route? = null

    fun setRoute(route: Route) {
        this.route = route

        name.value = route.name

        // Groups the stop times with the same shape
        groupedStopTimes.value = route.stopTimes.groupBy { it.shape }.map {
            val stopTimes = it.value
            val firstElement = stopTimes.first()

            GroupedStopTimes(
                stopTimes.map { stopTime ->  stopTime.departureTime },
                firstElement.shape,
                firstElement.departureTimeStamp,
                firstElement.serviceId
            )
        }
    }
}