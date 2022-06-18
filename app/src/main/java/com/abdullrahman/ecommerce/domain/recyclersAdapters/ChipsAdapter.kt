package com.abdullrahman.ecommerce.domain.recyclersAdapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.OptionsItem
import com.abdullrahman.ecommerce.databinding.ItemVariantsSelectionsBinding

class ChipsAdapter(val options:List<OptionsItem?>,val context:Context,val pos: Int) : RecyclerView.Adapter<ChipsAdapter.VH>() {
    inner class VH(val item: ItemVariantsSelectionsBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_variants_selections,
                parent,
                false
            )
        )
    var selectedNum: Int = 0
    override fun onBindViewHolder(holder: VH, position: Int) {
        val chip = options[0]!!.values!![position]
        holder.item.item = chip
        holder.item.root.setOnClickListener {
            selectedNum = position
            if (position == selectedNum)
                holder.item.root.setBackgroundColor(context.resources.getColor(R.color.orange))
            notifyDataSetChanged()
        }
        if (position != selectedNum)
            holder.item.root.setBackgroundColor(context.resources.getColor(R.color.orange))
        else
            holder.item.root.setBackgroundColor(context.resources.getColor(R.color.orange))

    }

    override fun getItemCount() = options.size
}