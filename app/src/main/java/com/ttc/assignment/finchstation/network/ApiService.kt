package com.ttc.assignment.finchstation.network

import com.ttc.assignment.finchstation.data.Station
import retrofit2.http.GET

/**
 * Created by edison on 10/12/20.
 */
interface ApiService {
    @GET("finch_station.json")
    suspend fun getFinchStationData(): Station
}