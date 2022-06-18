package com.abdullrahman.ecommerce.domain

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.data.models.VariantsItem
import com.abdullrahman.ecommerce.domain.recyclersAdapters.FeaturedAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.SaleAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.WishAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick
import com.bumptech.glide.Glide
import com.github.siyamed.shapeimageview.CircularImageView
import com.github.siyamed.shapeimageview.RoundedImageView

@BindingAdapter("featuredAdapter","listener")
fun setAdapterFeatured(view: RecyclerView, list: List<ProductsItem?>?,listener:ProductOnClick)
{
    if (list != null){
        view.layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
        view.setHasFixedSize(true)
        view.adapter = FeaturedAdapter(list!!,view.context,listener)
    }
}
 var salesAdapter: SaleAdapter? = null
var sizeLike = 0
@BindingAdapter("saleAdapter","listener","like_listener","like_list")
fun setAdapterSale(view: RecyclerView, list: List<ProductsItem?>?,listener:ProductOnClick,likeListener: LikeOnClick,likeList:List<WishList?>?)
{
    if (list != null && view.adapter == null){
        salesAdapter = SaleAdapter(list!!,view.context,listener,likeListener)
        if (likeList != null ){
            salesAdapter!!.addLike(likeList)
            sizeLike = likeList.size
        }
        view.layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
        view.setHasFixedSize(true)
        view.adapter = salesAdapter
    }
//    if (list != null && salesAdapter == null){
//        salesAdapter = SaleAdapter(list!!,view.context,listener,likeListener)
//        if (likeList != null){
//            salesAdapter!!.addLike(likeList)
//            sizeLike = likeList.size}
//        view.layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
//        view.setHasFixedSize(true)
//        view.adapter = salesAdapter
//    }else if (salesAdapter != null )
//    {
//        if (likeList != null)
//            salesAdapter!!.addLike(likeList)
//        view.layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
//        view.setHasFixedSize(true)
//        view.adapter = salesAdapter
//    }
}
var WishAdapter: WishAdapter? = null

@BindingAdapter("wishAdapter","listener_wish","like_listener_wish")
fun setAdapterWish(view: RecyclerView, likeList:List<WishList?>?, listener:ProductOnClick, likeListener: LikeOnClick)
{
    if (likeList != null ){
        WishAdapter = WishAdapter(likeList!!,view.context,listener,likeListener)

        view.layoutManager= GridLayoutManager(view.context, 2)
        view.setHasFixedSize(true)
        view.adapter = WishAdapter
    }
}

@BindingAdapter("circle_src")
fun setImageViewResourceCircle(imageView: CircularImageView, resource: String) {
    Glide.with(imageView.context)
        .load(resource)
        .fitCenter()
        .into(imageView)

}

@BindingAdapter("round_src")
fun setImageViewResource(imageView: RoundedImageView, resource: String) {
    Glide.with(imageView.context)
        .load(resource)
        .fitCenter()
        .into(imageView)
}
@BindingAdapter("set_price")
fun setprice(text: TextView, variants: List<VariantsItem?>?) {
    text.text = variants!![0]!!.price

}