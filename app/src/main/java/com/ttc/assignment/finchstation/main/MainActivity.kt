package com.ttc.assignment.finchstation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.ttc.assignment.finchstation.R

class MainActivity : AppCompatActivity() {

    private val viewModel: FinchStationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        val lifeCycleOwner = this

        viewModel.apply {
            requestData()

            isLoading.observe(owner = lifeCycleOwner) {
                //TODO: Handle loading
            }

            station.observe(owner = lifeCycleOwner) { station ->

            }
        }


    }
}