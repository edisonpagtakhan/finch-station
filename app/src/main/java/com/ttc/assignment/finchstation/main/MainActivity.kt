package com.ttc.assignment.finchstation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ttc.assignment.finchstation.R

class MainActivity : AppCompatActivity() {

    private val viewModel: FinchStationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    fun getData() {

    }
}