package com.ttc.assignment.finchstation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.ttc.assignment.finchstation.R
import com.ttc.assignment.finchstation.data.Route
import com.ttc.assignment.finchstation.databinding.FragmentDetailsBinding
import com.ttc.assignment.finchstation.main.adapter.StopTimesAdapter
import com.ttc.assignment.finchstation.views.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_details.*

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
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val route: Route = requireArguments().getParcelable(ROUTE_PARAM)!!
        detailsViewModel.setRoute(route)

        initializeRecyclerView()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        val margin = resources.getDimensionPixelSize(R.dimen.recycler_item_padding)

        stopTimesRecyclerView.apply {
            adapter = StopTimesAdapter()
            addItemDecoration(MarginItemDecoration(right = margin, left = margin, bottom = margin))
        }
    }

    private fun observeViewModel() {
        detailsViewModel.apply {
            routeName.observe(owner = viewLifecycleOwner) { routeName ->
                dataBinding.routeName = routeName
            }

            stopTimes.observe(owner = viewLifecycleOwner) { stopTimes ->
                (stopTimesRecyclerView.adapter as StopTimesAdapter).setData(stopTimes)
            }

            isUpdating.observe(owner = viewLifecycleOwner) { isUpdating ->
                dataBinding.isUpdating = isUpdating
            }
        }
    }
}