package com.abdullrahman.ecommerce.domain.recyclersAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.ItemWishListBinding
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick

class WishAdapter(
    var list: List<WishList?>?,
    context: Context?,
    val mListener: ProductOnClick,
    var likeListener: LikeOnClick
) :
    RecyclerView.Adapter<WishAdapter.VH>() {
    inner class VH(val itemData: ItemWishListBinding) : RecyclerView.ViewHolder(itemData.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_wish_list,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemData.item = list!![position]
        holder.itemData.favourite.setImageResource(R.drawable.ic_like_marked)
        val product = ProductsItem(
            id = list!![position]!!.id,
            title = list!![position]!!.title,
            variants = list!![position]!!.variants,
            tags = list!![position]!!.tags,
            vendor = list!![position]!!.vendor,
            options = list!![position]!!.options,
            bodyHtml = list!![position]!!.bodyHtml,
            image = list!![position]!!.image,
            images = list!![position]!!.images,

        )
        holder.itemData.root.setOnClickListener {
            mListener.onClick(product)

        }
        holder.itemData.favourite.setOnClickListener {
            likeListener.OnClick(list!![position]!!,true)

        }
    }

    override fun getItemCount() = list!!.size
}
