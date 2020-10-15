package com.ttc.assignment.finchstation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttc.assignment.finchstation.data.EventWrapper
import com.ttc.assignment.finchstation.data.Station
import com.ttc.assignment.finchstation.data.Stop
import com.ttc.assignment.finchstation.network.RetrofitClient
import kotlinx.coroutines.launch

/**
 * Created by edison on 10/12/20.
 */
class FinchStationViewModel: ViewModel() {

    private var station: Station? = null

    private val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val mutableStationName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val mutableStops: MutableLiveData<List<Stop>> by lazy {
        MutableLiveData<List<Stop>>()
    }

    private val showAllStops: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val hasApiError: MutableLiveData<EventWrapper<Boolean>> by lazy {
        MutableLiveData<EventWrapper<Boolean>>()
    }

    val isLoading: LiveData<Boolean>
        get() = loading

    val stationName: LiveData<String>
        get() = mutableStationName

    val stops: LiveData<List<Stop>>
        get() = mutableStops

    val willShowAllStops: LiveData<Boolean>
        get() = showAllStops

    val hasError: LiveData<EventWrapper<Boolean>>
        get() = hasApiError

    fun requestData(isRefreshing: Boolean = false) {
        loading.value = !isRefreshing

        viewModelScope.launch {
            try {
                val response = RetrofitClient
                    .getInstance()
                    .getApiService()
                    .getFinchStationData()

                loading.value = false
                station = response

                mutableStationName.value = station!!.name
                updateStops()

            } catch (exception: Exception) {
                loading.value = false
                hasApiError.value = EventWrapper(true)
            }
        }
    }

    fun toggleShowAll(willShowAll: Boolean) {
        showAllStops.value = willShowAll
        updateStops()
    }

    private fun updateStops() {
        val stops = station?.stops ?: return

        mutableStops.value = if (showAllStops.value!!) {
            stops.sortedByDescending { it.routes.isNotEmpty() }
        } else {
            stops.filter { it.routes.isNotEmpty() }
        }
    }
}