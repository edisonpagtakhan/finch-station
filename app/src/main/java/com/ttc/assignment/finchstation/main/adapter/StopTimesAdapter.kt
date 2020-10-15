package com.ttc.assignment.finchstation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ttc.assignment.finchstation.R
import com.ttc.assignment.finchstation.data.GroupedStopTimes
import com.ttc.assignment.finchstation.databinding.StopTimeItemBinding
import java.util.*

/**
 * Created by edison on 10/14/20.
 */
class StopTimesAdapter(private var stopTimes: List<GroupedStopTimes>? = null) :
    RecyclerView.Adapter<StopTimesAdapter.ViewHolder>() {

    override fun getItemCount(): Int = stopTimes?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: StopTimeItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.stop_time_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(stopTimes!![position])
    }

    fun setData(stopTimes: List<GroupedStopTimes>?) {
        this.stopTimes = stopTimes

        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: StopTimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val chipGroup: ChipGroup = binding.stopTimesGroup


        fun bindData(groupedStopTimes: GroupedStopTimes) {
            binding.stopTime = groupedStopTimes

            chipGroup.removeAllViews()

            val context = itemView.context

            groupedStopTimes.departureTimes.forEach { departureTime ->
                val chip = Chip(context).apply {
                    text = departureTime
                }

                chipGroup.addView(chip)
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("eta")
        fun setNextEta(textView: TextView, timestamp: Long) {
            val now = Calendar.getInstance().timeInMillis / 1000L
            val diff = timestamp - now

            val secs = (diff % 60).toInt()
            val mins = (diff / 60 % 60).toInt()
            val hrs = (diff / 3600).toInt()

            val resources = textView.resources

            val eta = resources.run {
                val hoursStr = getQuantityString(R.plurals.hrs, hrs, hrs)
                val minsStr = getQuantityString(R.plurals.mins, mins, mins)
                val secsStr = getQuantityString(R.plurals.secs, secs, secs)

                when {
                    hrs >= 1 -> "$hoursStr $minsStr"

                    mins >= 1 -> "$minsStr $secsStr"

                    else -> getString(R.string.arriving_soon)
                }
            }

            textView.text = eta
        }
    }
}