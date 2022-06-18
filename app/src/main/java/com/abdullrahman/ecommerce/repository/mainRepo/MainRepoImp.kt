package com.abdullrahman.ecommerce.repository.mainRepo

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.abdullrahman.ecommerce.data.data_source.room.customerData.CustomerDao
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishListDao
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.data_source.shopify_api.ShopifyApi
import com.abdullrahman.ecommerce.data.models.Product
import com.abdullrahman.ecommerce.data.models.Products
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.data.models.SmartCollections
import com.abdullrahman.ecommerce.utils.response.Response
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class MainRepoImp @Inject constructor(
    @ApplicationContext val context: Context,
    private val api: ShopifyApi,
    val customerDao: CustomerDao
    ,val wishListDao: WishListDao
) : MainRepo {
    override suspend fun getHomepageList(): Response<Products> {
        if (isNetworkConnected() && internetIsConnected()){
        val result = api.getHomePageCollection()
        return if (result.isSuccessful) {
            //val startTime = System.currentTimeMillis()
//            val list = addVariants(result.body()!!)
//            result.body()!!.products = list
            //   val endTime = System.currentTimeMillis()
            //    println("time difference: ${endTime - startTime} ")
            Response.Success(result.body()!!)
        } else {
            Response.Error("error products")
        }}else{
            withContext(Dispatchers.Main){
              //  Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun getProduct(id: Long): Response<Product> {
        if (isNetworkConnected() && internetIsConnected()){

            val result = api.getProduct(id)
        return if (result.isSuccessful) {
            Response.Success(result.body()!!)
        } else {
            Response.Error("error products")
        }
        }else{
            withContext(Dispatchers.Main){
              //  Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun getSelectedCollection(id: Long): Response<Products> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.getSelectedCollection(id)
            return if (result.isSuccessful) {

                val list = result.body()!!.products
                result.body()!!.products = list
                Response.Success(result.body()!!)
            } else {
                Response.Error("error products")
            }
        }else{
                withContext(Dispatchers.Main){
              //      Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

                }
                return Response.Error("No Internet Connection")
            }
    }

    override suspend fun getAllProductsList(): Response<Products> {
        if (isNetworkConnected() && internetIsConnected()) {
            val result = api.getAllProductList()
            return if (result.isSuccessful) {
            val filteredList = result.body()!!.products!!
//            val list = addVariants(Products(filteredList))
            result.body()!!.products = filteredList
            Response.Success(result.body()!!)
        } else {
            Response.Error("error products")
        }
        }else{
            withContext(Dispatchers.Main){
            //    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }
    var moreList:MutableList<ProductsItem?> = mutableListOf()
    override suspend fun subList(products: List<ProductsItem?>?): List<ProductsItem?>? {

        var tempList = mutableListOf<ProductsItem?>()
        var subList = return if (moreList!!.size > 7) {
            tempList = moreList.subList(6,moreList.size-1)
            return moreList.subList(0, 6).toList()
        } else {
            moreList.toList()
        }
        moreList = tempList
        return addVariants(Products(subList))
    }

    override suspend fun addWishListProduct(WishList: WishList) {
        wishListDao.insertProduct(WishList)
    }

    override fun getWishListProducts(): LiveData<List<WishList>> {
       return wishListDao.getProduct()
    }

    override suspend fun deleteWishList(WishList: WishList) {
        wishListDao.deleteProduct(WishList)
    }

    suspend fun addVariants(body: Products): List<ProductsItem?> {

        var id: Long = 0
        val list = body.products!!
        for (i in 0 until list.size!!) {
            id = list[i]!!.id!!
            val result = api.getProductVariants(id)
            println(result.body())
            if (result.isSuccessful)
                list[i]!!.variants = result.body()!!.variants!!
        }
        return list
    }


    override suspend fun getSmartCollections(): Response<SmartCollections> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.getSmartCollections()
        return if (result.isSuccessful) {
            Response.Success(result.body()!!)
        } else {
            Response.Error("error products")
        }
    }else{
        withContext(Dispatchers.Main){
          //  Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

        }
        return Response.Error("No Internet Connection")
    }
    }

    override suspend fun getSaleList(): Response<Products> {
        if (isNetworkConnected() && internetIsConnected()) {

        val result = api.getSaleCollection()
        return if (result.isSuccessful) {
            val list = addVariants(result.body()!!)
            result.body()!!.products = list
            Response.Success(result.body()!!)
        } else {
            Response.Error("error products")
        }
        }else{
            withContext(Dispatchers.Main){
           //     Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }
    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}