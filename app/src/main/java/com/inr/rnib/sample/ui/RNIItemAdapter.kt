package com.inr.rnib.sample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inr.rnib.sample.R
import com.inr.rnib.sample.RNIItemResult
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rni_check.view.*

class RNIItemAdapter : RecyclerView.Adapter<RNIItemAdapter.RNIItemVH>() {
    private val items: MutableList<RNIItemResult> = mutableListOf()

    fun update(results: List<RNIItemResult>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RNIItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return RNIItemVH(
            inflater.inflate(
                R.layout.item_rni_check,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RNIItemVH, position: Int) = holder.bind(items[position])

    fun add(rniItemResult: RNIItemResult) {
        items.add(rniItemResult)
        notifyItemInserted(items.size - 1)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class RNIItemVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: RNIItemResult) {
            containerView.rniItemText.text = item.text
            containerView.rniItemResultIcon.update(isdrni = item.result)
        }
    }
}


