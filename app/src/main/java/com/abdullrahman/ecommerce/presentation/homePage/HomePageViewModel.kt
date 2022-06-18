package com.abdullrahman.ecommerce.presentation.homePage

import android.util.Log
import androidx.lifecycle.*
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.data.models.SmartCollections
import com.abdullrahman.ecommerce.repository.mainRepo.MainRepoImp
import com.abdullrahman.ecommerce.utils.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    val productsApi: MainRepoImp,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val IsHomeLoading = MutableLiveData< Boolean>(true)
    val IsSaleLoading = MutableLiveData< Boolean>(true)
    var likedList:LiveData<List<WishList>>? = null
    val itemhomePageProducts = MutableLiveData< List<ProductsItem?>>()
    val itemSaleProducts = MutableLiveData< List<ProductsItem?>>()
    val homePageCollections = MutableLiveData<SmartCollections>()

    init {
        getHomePageProducts()
        getSaleProducts()
        getSmartCollection()
        getLikes()
    }
    fun addlike(WishList:WishList){
        viewModelScope.launch(Dispatchers.IO){
            productsApi.addWishListProduct(WishList)
        }
    }
    fun deletelike(WishList:WishList){
        viewModelScope.launch(Dispatchers.IO){
            productsApi.deleteWishList(WishList)
        }
    }
    fun getLikes()
    {
        likedList = productsApi.getWishListProducts()

    }
    fun getHomePageProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val products = productsApi.getHomepageList()) {
                is Response.Success -> {
                    if (products.data != null) {
                        itemhomePageProducts.postValue(products.data!!.products!!)
                    }

                }
                is Response.Error -> {
                    Log.e("MainViewModel", products.message!!)
                    if (products.message!! == "No Internet Connection")
                        getHomePageProducts()
                }

            }
        }
    }
    fun getSaleProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val products = productsApi.getSaleList()) {
                is Response.Success -> {
                    if (products.data != null){
                        itemSaleProducts.postValue(products.data!!.products!!)
                    }

                }
                is Response.Error -> {
                    Log.e("MainViewModel", products.message!!)
                    if (products.message!! == "No Internet Connection")
                        getSaleProducts()
                }

            }
        }
    }

    fun getSmartCollection() {
        val list = mutableListOf<Long>()
        list.add(268875595869)
        list.add(268875333725)
        list.add(268875497565)

        list.add(268875563101)
        list.add(268875300957)
        list.add(268875432029)

        list.add(268875530333)
        list.add(268875235421)
        list.add(268875366493)

        viewModelScope.launch(Dispatchers.IO) {
            when (val collection = productsApi.getSmartCollections()) {
                is Response.Success -> {
                    if (collection.data != null
                            )
                        homePageCollections.postValue(SmartCollections(collection.data.smartCollections!!.filter { s -> !list.contains(s!!.id) }))
                }
                is Response.Error -> {
                    Log.e("MainViewModel", collection.message!!)
                    if (collection.message!! == "No Internet Connection")
                        getSmartCollection()
                }
            }
        }
    }
}