package com.ttc.assignment.finchstation.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val top: Int = 0, private val left: Int = 0,
    private val bottom: Int = 0, private val right: Int = 0,
    private val willApplyToLastItem: Boolean = false
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position == parent.adapter!!.itemCount - 1 && !willApplyToLastItem) {
            return
        }

        outRect.let {
            it.top = top
            it.left = left
            it.bottom = bottom
            it.right = right
        }
    }
}