package com.abdullrahman.ecommerce.presentation.search

import android.util.Log
import androidx.lifecycle.*
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.repository.mainRepo.MainRepoImp
import com.abdullrahman.ecommerce.utils.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val productsApi: MainRepoImp,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var searchProducts = MutableLiveData<List<ProductsItem?>>()
    var allProducts = MutableLiveData<List<ProductsItem?>>()
    var likedList: LiveData<List<WishList>>? = null

    init {

        getProducts()
        getLikes()
    }
    fun getLikes()
    {
        likedList = productsApi.getWishListProducts()

    }
    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val products = productsApi.getAllProductsList()) {

                is Response.Success -> {
                    if (products.data != null)
                        allProducts.postValue(products.data.products!!)


                }
                is Response.Loading -> {
                    Log.e("MainViewModel", products.message!!)

                }
            }
        }

    }

    fun getProductsSearch(search: String) {

        viewModelScope.launch {
            searchProducts.postValue(allProducts.value!!.filter { item ->
                item!!.title!!.lowercase().contains(search.lowercase()) || item.tags!!.lowercase()
                    .contains(search.lowercase())
        })
        }
    }
}