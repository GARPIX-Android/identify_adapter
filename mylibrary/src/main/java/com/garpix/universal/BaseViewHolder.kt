package com.garpix.universal

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out V : ViewBinding, I : Item>(
    private val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }
}