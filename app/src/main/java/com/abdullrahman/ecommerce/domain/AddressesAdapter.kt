package com.abdullrahman.ecommerce.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.AddressesItem
import com.abdullrahman.ecommerce.databinding.ItemAddressesBinding
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.AddressOnClick

class AddressesAdapter(
    val list: MutableList<AddressesItem?>,
    val context: Context,
    val mListener: AddressOnClick
) : RecyclerView.Adapter<AddressesAdapter.VH>() {
    inner class VH(val bindingItem: ItemAddressesBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {

    }
    fun add(address:AddressesItem,position:Int){
        list.add(position,address)
        notifyItemInserted(position)
    }
    fun remove(position: Int)
    {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_addresses,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindingItem.address = list[position]
        holder.bindingItem.textView5.setOnClickListener {
            mListener.onClick(list[position]!!)
        }
    }

    override fun getItemCount() = list.size
}