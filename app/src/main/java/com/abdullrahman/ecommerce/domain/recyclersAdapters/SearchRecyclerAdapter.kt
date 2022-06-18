package com.abdullrahman.ecommerce.domain.recyclersAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.ItemPopularItemsBinding
import com.abdullrahman.ecommerce.databinding.ItemSearchProductBinding
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick

class SearchRecyclerAdapter(
    var list: List<ProductsItem?>?,

    context: Context?,
    val mListener: ProductOnClick,
    var likeListener: LikeOnClick
) :
    RecyclerView.Adapter<SearchRecyclerAdapter.VH>() {
    var favList: List<WishList?>? = null

    inner class VH(val itemData: ItemSearchProductBinding) : RecyclerView.ViewHolder(itemData.root)

    fun addLike(fav: List<WishList?>?)
    {
        favList = fav
        notifyDataSetChanged()
    }
    fun addList(listAlt: List<ProductsItem?>) {
        list = listAlt
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_search_product,
                parent,
                false
            )
        )
    var isThere:List<WishList?>? = null
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemData.item = list!![position]!!
        holder.itemData.root.setOnClickListener {
            mListener.onClick(list!![position]!!)
        }
        isThere = null
        var statue = false
        if (favList != null) {
             isThere = favList!!.filter { item -> item!!.title == list!![position]!!.title }

            if (!isThere.isNullOrEmpty()) {
                holder.itemData.favourite.setImageResource(R.drawable.ic_like_marked)
                statue = true
            }else{
                holder.itemData.favourite.setImageResource(R.drawable.ic_like_group)
                statue = false
            }
        }
        holder.itemData.favourite.setOnClickListener {
            val wishlist = WishList(
                id = list!![position]!!.id,
                image = list!![position]!!.image,
                images = list!![position]!!.images,
                bodyHtml = list!![position]!!.bodyHtml,
                options = list!![position]!!.options,
                title = list!![position]!!.title,
                tags = list!![position]!!.tags,
                variants = list!![position]!!.variants,
                vendor = list!![position]!!.vendor,
                status = list!![position]!!.status,
                productType = list!![position]!!.productType,
            )
            if (statue)
            {
                holder.itemData.favourite.setImageResource(R.drawable.ic_like_group)
                statue = true

            }
            else
            {
                holder.itemData.favourite.setImageResource(R.drawable.ic_like_marked)
                statue = false
            }
            likeListener.OnClick(wishlist,statue)
        }
    }

    override fun getItemCount() = list!!.size
}