package com.ttc.assignment.finchstation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val isLoading: LiveData<Boolean>
        get() = loading

    val stationName: LiveData<String>
        get() = mutableStationName

    val stops: LiveData<List<Stop>>
        get() = mutableStops

    val willShowAllStops: LiveData<Boolean>
        get() = showAllStops

    fun requestData() {
        loading.value = true

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
            }
        }
    }

    private fun updateStops() {
        val stops = station!!.stops

        mutableStops.value = if (showAllStops.value!!) {
            stops
        } else {
            stops.filter { it.routes.isNotEmpty() }
        }
    }
}