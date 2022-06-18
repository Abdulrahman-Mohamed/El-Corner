package com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick

import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList

interface LikeOnClick {
    fun OnClick(WishList: WishList, statue:Boolean)
}