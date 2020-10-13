package com.ttc.assignment.finchstation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttc.assignment.finchstation.data.Station
import com.ttc.assignment.finchstation.network.RetrofitClient
import kotlinx.coroutines.launch

/**
 * Created by edison on 10/12/20.
 */
class FinchStationViewModel: ViewModel() {

    private val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val stationResp: MutableLiveData<Station> by lazy {
        MutableLiveData<Station>()
    }

    val isLoading: LiveData<Boolean>
        get() = loading

    val station: LiveData<Station>
        get() = stationResp

    fun requestData() {
        loading.value = true

        viewModelScope.launch {
            try {
                val response = RetrofitClient
                    .getInstance()
                    .getApiService()
                    .getFinchStationData()

                loading.value = false
                stationResp.value = response

            } catch (exception: Exception) {
                loading.value = false
            }
        }
    }
}