package com.abdullrahman.ecommerce.presentation.categories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullrahman.ecommerce.data.models.SmartCollections
import com.abdullrahman.ecommerce.data.models.SmartCollectionsItem
import com.abdullrahman.ecommerce.repository.mainRepo.MainRepoImp
import com.abdullrahman.ecommerce.utils.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val productsApi: MainRepoImp,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val sellected = MutableLiveData<String>("men")
    val sellectedCollection = MutableLiveData<List<SmartCollectionsItem?>>()
    val collections = MutableLiveData<List<SmartCollectionsItem?>>()

    init {
        getSmartCollections()
    }

    fun getSmartCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val collection = productsApi.getSmartCollections()) {
                is Response.Success -> {
                    if (collection.data != null
                    )
                        collections.postValue(collection.data.smartCollections!!)
                }
                is Response.Error -> {
                    Log.e("MainViewModel", collection.message!!)
                    if (collection.message!! == "No Internet Connection")
                    getSmartCollections()
                }
            }
        }
    }

    fun setSellectedCollection(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sellectedCollection.postValue(collections.value!!.filter { it -> it!!.rules!!.any { item -> item!!.condition == title } })

        }

    }
}

