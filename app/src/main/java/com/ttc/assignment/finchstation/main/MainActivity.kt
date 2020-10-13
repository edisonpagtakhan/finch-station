package com.ttc.assignment.finchstation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.ttc.assignment.finchstation.R
import com.ttc.assignment.finchstation.databinding.ActivityMainBinding
import com.ttc.assignment.finchstation.main.adapter.StopSection
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: FinchStationViewModel by viewModels()
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureViews()
        initializeViewModel()
    }

    private fun configureViews() {
        setSupportActionBar(toolbar)
        configureSwitch()
        configurePullToRefresh()
        configureRecyclerView()
    }

    private fun configureSwitch() {
        hideRoutesSwitch.setOnCheckedChangeListener { _, willHide ->
            viewModel.toggleShowAll(!willHide)
        }
    }

    private fun configurePullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.requestData(true)
        }
    }

    private fun configureRecyclerView() {
        stopsRecyclerView.adapter = SectionedRecyclerViewAdapter()
        //TODO: Add divider
    }

    private fun initializeViewModel() {
        val lifeCycleOwner = this

        viewModel.apply {
            requestData()

            isLoading.observe(owner = lifeCycleOwner) { isLoading ->
                //TODO: Handle loading
            }

            stationName.observe(owner = lifeCycleOwner) { stationName ->
                supportActionBar!!.title = stationName
                swipeRefreshLayout.isRefreshing = false
            }

            stops.observe(owner = lifeCycleOwner) { stops ->
                val adapter = stopsRecyclerView.adapter as SectionedRecyclerViewAdapter
                adapter.removeAllSections()

                stops.forEach { stop ->
                    val section = StopSection(stop)

                    adapter.addSection(section)
                }

                adapter.notifyDataSetChanged()
            }

            willShowAllStops.observe(owner = lifeCycleOwner) { willShow ->
                dataBinding.isChecked = !willShow
            }
        }


    }
}