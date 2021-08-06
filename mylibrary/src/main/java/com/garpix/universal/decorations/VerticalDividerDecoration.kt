package com.garpix.universal.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides for adding vertical margins for items in recyclerview
 *
 * @param viewType is viewType of current item
 * @param innerDivider is divider between items of the same viewType
 * @param outerDivider is divider between items of the different viewType
 */

class VerticalItemDecoration(
    private val viewType: Int,
    private val innerDivider: Int,
    private val outerDivider: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildViewHolder(view).itemViewType != viewType) return

        val adapter = parent.adapter ?: return
        val currentPosition =
            parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION } ?: return

        val isPrevTargetView = adapter.isPrevTargetView(currentPosition, viewType)
        val isNextTargetView = adapter.isNextTargetView(currentPosition, viewType)

        val oneSideInnerDivider = innerDivider / 2

        with(outRect) {
            top = if (isPrevTargetView) oneSideInnerDivider else outerDivider
            bottom = if (isNextTargetView) oneSideInnerDivider else outerDivider
        }
    }

    /**
     * Checks if the previous item has the same viewType
     *
     * @param currentPosition position of current item in RecyclerView
     * @param viewType is viewType of current item
     *
     * @return the boolean result of comparing ViewTypes with the previous item in the list
     */

    private fun RecyclerView.Adapter<*>.isPrevTargetView(
        currentPosition: Int,
        viewType: Int
    ) = currentPosition != 0 && getItemViewType(currentPosition - 1) == viewType

    /**
     * Checks if the next item has the same viewType
     *
     * @param currentPosition position of current item in RecyclerView
     * @param viewType is viewType of current item
     *
     * @return the boolean result of comparing ViewTypes with the next item in the list
     */

    private fun RecyclerView.Adapter<*>.isNextTargetView(
        currentPosition: Int,
        viewType: Int
    ): Boolean {
        val lastIndex = itemCount - 1
        return currentPosition != lastIndex && getItemViewType(currentPosition + 1) == viewType
    }

}