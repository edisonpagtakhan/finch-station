package com.ttc.assignment.finchstation.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialContainerTransform
import com.ttc.assignment.finchstation.R
import com.ttc.assignment.finchstation.data.Route
import com.ttc.assignment.finchstation.databinding.ActivityMainBinding
import com.ttc.assignment.finchstation.main.adapter.StopSection
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StopSection.Callback {

    private val viewModel: FinchStationViewModel by viewModels()
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureViews()
        initializeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount > 0) {
            return
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }

        fragmentContainer.visibility = View.GONE
    }

    override fun onClickRoute(route: Route, view: View) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val fragment = DetailsFragment.newInstance(route)

        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(fragmentContainer.id, fragment, DetailsFragment.TAG)
            .addToBackStack(DetailsFragment.TAG)
            .commit()

        val transform = MaterialContainerTransform().apply {
            startView = view
            addTarget(fragmentContainer)
        }

        TransitionManager.beginDelayedTransition(stopsRecyclerView, transform)
        fragmentContainer.visibility = View.VISIBLE
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
    }

    private fun initializeViewModel() {
        val lifeCycleOwner = this

        viewModel.apply {
            requestData()

            isLoading.observe(owner = lifeCycleOwner) { isLoading ->
                dataBinding.isLoading = isLoading
            }

            stationName.observe(owner = lifeCycleOwner) { stationName ->
                supportActionBar!!.title = stationName
                swipeRefreshLayout.isRefreshing = false
            }

            stops.observe(owner = lifeCycleOwner) { stops ->
                val adapter = stopsRecyclerView.adapter as SectionedRecyclerViewAdapter
                adapter.removeAllSections()

                stops.forEach { stop ->
                    val section = StopSection(stop, this@MainActivity)

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