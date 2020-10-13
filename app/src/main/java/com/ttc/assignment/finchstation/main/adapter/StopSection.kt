package com.ttc.assignment.finchstation.main.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ttc.assignment.finchstation.R
import com.ttc.assignment.finchstation.data.Route
import com.ttc.assignment.finchstation.data.Stop
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import kotlinx.android.synthetic.main.route_item.view.*
import kotlinx.android.synthetic.main.stop_header.view.*

/**
 * Created by edison on 10/13/20.
 */
class StopSection(private val stop: Stop, private val callback: Callback) : Section(
    SectionParameters.builder()
        .headerResourceId(R.layout.stop_header)
        .itemResourceId(R.layout.route_item)
        .build()) {

    override fun getContentItemsTotal(): Int = stop.routes.size

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder = HeaderViewHolder(view)

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        (holder as HeaderViewHolder).bindData(stop.name)
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = RouteViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RouteViewHolder).bindData(stop.routes[position])
    }

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val headerView: TextView = view.headerView

        fun bindData(stopName: String) {
            headerView.text = stopName
        }
    }

    inner class RouteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val routeTextView: TextView = view.routeTextView

        fun bindData(route: Route) {
            itemView.setOnClickListener {
                callback.onClickRoute(route, routeTextView)
            }

            routeTextView.text = route.name
        }
    }

    interface Callback {
        fun onClickRoute(route: Route, view: View)
    }
}