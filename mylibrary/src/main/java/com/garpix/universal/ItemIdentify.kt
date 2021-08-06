package com.garpix.universal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

/**
 * The interface regulating the work of the data handler for each [Item] that got into the recyclerview
 */

interface ItemIdentify<V : ViewBinding, I : Item> {

    /**
     * It is necessary to implement a function that determines whether the passed [Item]
     * is an object of the class of the current [ItemIdentify]
     *
     * @param item Item to check
     *
     * @return boolean result of checking
     */

    fun isRelativeItem(item: Item): Boolean

    /**
     * Function return the layout id for the current [ItemIdentify]
     *
     * @return layout id for the current [ItemIdentify]
     */

    @LayoutRes
    fun getLayoutId(): Int

    /**
     *Get the required [BaseViewHolder]
     *
     *@param parent ViewGroup for create needed [BaseViewHolder]
     *@param layoutInflater LayoutInflater for create needed [BaseViewHolder]
     *
     *@return [BaseViewHolder] for working with the current Item
     */

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<V, I>

    /**
     * [DiffUtil] is a utility class that calculates the difference between two lists
     * and outputs a list of update operations that converts
     * the first list into the second one
     *
     * @return callback for calculating the diff between two non-null current items in a list
     */

    fun getDiffUtil(): DiffUtil.ItemCallback<I>

}