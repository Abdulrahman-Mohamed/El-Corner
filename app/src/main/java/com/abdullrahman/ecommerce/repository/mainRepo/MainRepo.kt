package com.abdullrahman.ecommerce.repository.mainRepo

import androidx.lifecycle.LiveData
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.Product
import com.abdullrahman.ecommerce.data.models.Products
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.data.models.SmartCollections
import com.abdullrahman.ecommerce.utils.response.Response

interface MainRepo{
    suspend fun getHomepageList():Response<Products>
    suspend fun getSmartCollections():Response<SmartCollections>
    suspend fun getSaleList():Response<Products>
    suspend fun getProduct(id:Long):Response<Product>
    suspend fun getSelectedCollection(id:Long):Response<Products>
    suspend fun getAllProductsList():Response<Products>
    suspend fun subList(products: List<ProductsItem?>?): List<ProductsItem?>?
    suspend fun addWishListProduct(WishList: WishList)
    fun getWishListProducts(): LiveData<List<WishList>>
    suspend fun deleteWishList(WishList: WishList)

}