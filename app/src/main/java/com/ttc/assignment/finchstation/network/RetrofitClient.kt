package com.ttc.assignment.finchstation.network

import com.ttc.assignment.finchstation.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by edison on 10/12/20.
 */
class RetrofitClient private constructor() {
    companion object {
        private const val BASE_URL = "https://myttc.ca"
        private const val TIMEOUT_IN_SEC = 60L

        private var instance: RetrofitClient? = null

        fun getInstance(): RetrofitClient {
            if (instance == null) {
                instance = RetrofitClient()
            }

            return instance!!
        }
    }

    init {
        initializeApiService()
    }

    private lateinit var apiService: ApiService

    fun getApiService(): ApiService = apiService

    private fun initializeApiService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    private fun createClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor())
        }

        return okHttpClient.build()
    }

}