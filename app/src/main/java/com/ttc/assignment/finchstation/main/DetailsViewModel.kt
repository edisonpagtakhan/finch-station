package com.ttc.assignment.finchstation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttc.assignment.finchstation.data.GroupedStopTimes
import com.ttc.assignment.finchstation.data.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by edison on 10/14/20.
 */
class DetailsViewModel : ViewModel() {
    companion object {
        private const val AUTO_REFRESH_INTERVAL_IN_MILLIS = 30000L
        private const val UPDATING_LAYOUT_DURATION = 3000L
    }

    val routeName: LiveData<String>
        get() = name

    val stopTimes: LiveData<List<GroupedStopTimes>>
        get() = groupedStopTimes

    val isUpdating: LiveData<Boolean>
        get() = willShowUpdatingLayout

    private val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val groupedStopTimes: MutableLiveData<List<GroupedStopTimes>> by lazy {
        MutableLiveData<List<GroupedStopTimes>>()
    }

    private val willShowUpdatingLayout: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private var route: Route? = null

    fun setRoute(route: Route) {
        this.route = route

        name.value = route.name

        updateStopTimes()
    }

    private fun updateStopTimes() {
        startAutoRefresh()

        val now = Calendar.getInstance().timeInMillis / 1000L

        // Groups future stop times with the same shape
        groupedStopTimes.value = route!!.stopTimes
            .filter { stopTime -> stopTime.departureTimeStamp > now }
            .groupBy { it.shape }
            .map {
                val stopTimes = it.value
                val firstElement = stopTimes.first()

                GroupedStopTimes(
                    stopTimes.map { stopTime -> stopTime.departureTime },
                    firstElement.shape,
                    firstElement.departureTimeStamp,
                    firstElement.serviceId
                )

            }
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            delay(AUTO_REFRESH_INTERVAL_IN_MILLIS)
            showUpdatingLayout()
            updateStopTimes()
        }
    }

    // Add delay before updating layout to make the update more visible
    private suspend fun showUpdatingLayout() {
        willShowUpdatingLayout.value = true

        delay(UPDATING_LAYOUT_DURATION)

        willShowUpdatingLayout.value = false

    }
}