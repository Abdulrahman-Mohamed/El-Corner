package com.abdullrahman.ecommerce.presentation.product

import android.util.Log
import androidx.lifecycle.*
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.Products
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.repository.mainRepo.MainRepoImp
import com.abdullrahman.ecommerce.utils.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    val productsApi: MainRepoImp,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {
    var product = MutableLiveData<ProductsItem?>()
    var productsList = MutableLiveData<List<ProductsItem?>>()
    var more = MutableLiveData<Boolean>(false)
    var tempoList = mutableListOf<ProductsItem?>()
    var remainList = mutableListOf<ProductsItem?>()
    fun getAllProducts(id: Long){
        productsList = MutableLiveData<List<ProductsItem?>>()
        tempoList = mutableListOf<ProductsItem?>()
        remainList = mutableListOf<ProductsItem?>()
        viewModelScope.launch(Dispatchers.IO) {
            when(val products = productsApi.getSelectedCollection(id))
            {
                is Response.Success ->{
                    if (products.data != null)
                        tempoList = products.data!!.products!!.toMutableList()
                        loadProductsPaginating()


                }
                is Response.Loading -> {
                    Log.e("MainViewModel", products.message!!)
                    if (products.message!! == "No Internet Connection")
                        getAllProducts(id)
                }
            }
        }
    }


    fun getListLikes():LiveData<List<WishList>>{
      return  productsApi.getWishListProducts()
    }
     fun loadProductsPaginating() {
        viewModelScope.launch {


        if (tempoList.size > 7)
        {
            more.value = true
            remainList.addAll(productsApi.addVariants(Products(tempoList.subList(0,6))))
            productsList.postValue(remainList.toList())

            for (i in 0 ..6)
            tempoList.removeAt(0)

        }
            else{
            more.value = false
            remainList.addAll(productsApi.addVariants(Products(tempoList)))
            productsList.postValue(remainList.toList())
            tempoList.clear()
            remainList.clear()
            }
        }
    }


    fun getProduct(id: Long) {
        product = MutableLiveData<ProductsItem?>()
        viewModelScope.launch{

            when (val products = productsApi.getProduct(id)) {
                is Response.Success -> {
                    if (products.data != null) {
                        product.postValue(products.data!!.product!!)                    }

                }
                is Response.Error -> {
                    Log.e("MainViewModel", products.message!!)
                    if (products.message!! == "No Internet Connection")
                        getProduct(id)
                }

            }
        }
    }
}