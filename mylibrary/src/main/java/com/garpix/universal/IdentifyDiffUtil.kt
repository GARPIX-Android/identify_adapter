package com.garpix.universal

import androidx.recyclerview.widget.DiffUtil

/**
 * The class inherits from [DiffUtil.ItemCallback],
 * performs the primary basic comparison of items and,
 * if successful, returns [DiffUtil.ItemCallback] for each specific [ItemIdentify]
 */

class IdentifyDiffUtil(
    private val identifiers: List<ItemIdentify<*, *>>,
) : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areContentsTheSame(oldItem, newItem)
    }

    /**
     * @return [DiffUtil.ItemCallback] for each specific [ItemIdentify]
     */

    private fun getItemCallback(
        item: Item
    ): DiffUtil.ItemCallback<Item> = identifiers.find { it.isRelativeItem(item) }
        ?.getDiffUtil()
        ?.let { it as DiffUtil.ItemCallback<Item> }
        ?: throw IllegalStateException("DiffUtil not found for $item")
}