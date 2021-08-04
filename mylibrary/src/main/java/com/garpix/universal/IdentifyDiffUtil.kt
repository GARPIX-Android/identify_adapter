package com.garpix.universal

import androidx.recyclerview.widget.DiffUtil

class IdentifyDiffUtil(
    private val fingerprints: List<ItemIdentify<*, *>>,
) : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areContentsTheSame(oldItem, newItem)
    }

    private fun getItemCallback(
        item: Item
    ): DiffUtil.ItemCallback<Item> = fingerprints.find { it.isRelativeItem(item) }
        ?.getDiffUtil()
        ?.let { it as DiffUtil.ItemCallback<Item> }
        ?: throw IllegalStateException("DiffUtil not found for $item")

}