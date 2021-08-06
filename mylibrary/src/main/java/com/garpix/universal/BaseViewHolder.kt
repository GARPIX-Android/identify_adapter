package com.garpix.universal

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base class for inheritance of all others ViewHolder's
 *
 * @param V ViewBinding for this ViewHolder
 * @param I item extends [Item]
 */

abstract class BaseViewHolder<out V : ViewBinding, I : Item>(
     val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    /**
     * function to be implemented
     * Required to link the object model to the layout.
     * It is necessary to describe the logic of the behavior of this item
     *
     * @param item extends [Item]
     */

    open fun onBind(item: I) {
        this.item = item
    }
}