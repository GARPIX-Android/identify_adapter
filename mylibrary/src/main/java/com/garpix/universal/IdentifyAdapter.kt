package com.garpix.universal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
 * Adapter working with a list of objects that implement the [Item] interface
 * and a list of objects that implement the [ItemIdentify] interface and describe the processing of objects of each specific types
 *
 * @param identifiers list of [ItemIdentify] to describe the logic and behavior for each type of [Item] used
 */

class IdentifyAdapter(
    private val identifiers: List<ItemIdentify<*, *>>,
) : ListAdapter<Item, BaseViewHolder<ViewBinding, Item>>(
    IdentifyDiffUtil(identifiers)
) {

    /**
     * Searches by [identifiers]
     * and returns the appropriate [BaseViewHolder] type
     * for the given [Item] type
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, Item> {
        val inflater = LayoutInflater.from(parent.context)
        return identifiers.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, Item> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, Item>, position: Int) {
        holder.onBind(currentList[position])
    }

    /**
     * Searches by [identifiers]
     * and returns the appropriate viewType
     */

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return identifiers.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

}