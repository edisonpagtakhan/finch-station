package com.ttc.assignment.finchstation.views

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by edison on 10/15/20.
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("willShow")
    fun willShow(view: View, willShow: Boolean) {
        view.visibility = if (willShow) View.VISIBLE else View.GONE
    }
}