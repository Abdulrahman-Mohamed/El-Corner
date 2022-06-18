package com.abdullrahman.ecommerce.domain.recyclersAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.ItemFeaturedResturantBinding
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick

class FeaturedAdapter(val list: List<ProductsItem?>, context: Context?,val listener:ProductOnClick) : RecyclerView.Adapter<FeaturedAdapter.VH>() {
    inner class VH(val item:ItemFeaturedResturantBinding):RecyclerView.ViewHolder(item.root)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH (
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_featured_resturant,
                parent,
                false
            )
                )

    override fun onBindViewHolder(holder: VH, position: Int) {
      holder.item.item = list[position]!!
        holder.item.root.setOnClickListener {
            listener.onClick(list[position]!!)
        }
    }

    override fun getItemCount() = list!!.size

}
