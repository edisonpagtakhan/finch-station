package com.ttc.assignment.finchstation.main

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialContainerTransform
import com.ttc.assignment.finchstation.R
import com.ttc.assignment.finchstation.data.Route
import com.ttc.assignment.finchstation.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    companion object {
        const val TAG = "DetailsFragment"
        const val ROUTE_PARAM = "route"

        fun newInstance(route: Route) = DetailsFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable(ROUTE_PARAM, route)

            arguments = bundle
        }
    }

    private lateinit var dataBinding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val route: Route = requireArguments().getParcelable(ROUTE_PARAM)!!

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        dataBinding.routeName = route.name

        return dataBinding.root
    }
}