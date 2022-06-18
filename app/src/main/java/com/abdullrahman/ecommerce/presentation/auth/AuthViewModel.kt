package com.abdullrahman.ecommerce.presentation.auth

import android.util.Log
import androidx.lifecycle.*
import com.abdullrahman.ecommerce.data.data_source.room.customerData.Customer_RoomModel
import com.abdullrahman.ecommerce.data.models.AddressContainer
import com.abdullrahman.ecommerce.data.models.AddressesItem
import com.abdullrahman.ecommerce.data.models.Customer
import com.abdullrahman.ecommerce.data.models.CustomerMokup
import com.abdullrahman.ecommerce.domain.Constants
import com.abdullrahman.ecommerce.repository.authRepo.AuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val authApi: AuthRepositoryImp,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val addresses = MutableLiveData<List<AddressesItem?>>()
    val authResult = MutableLiveData<Customer>()
    val isSignedIn = MutableLiveData<Boolean>()
    val isloading = MutableLiveData<Boolean>()
    val isSignedUp = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val customer = MutableLiveData<CustomerMokup>()
    val addressItem = MutableLiveData<AddressesItem>()
    val addressAdded = MutableLiveData<Boolean>(false)

    init {


    }
    fun UpdateAddress()
    {
        viewModelScope.launch {
            val address =  AddressContainer( addressItem.value!!)
            val addressId = addressItem.value!!.id!!
            authApi.updateAddress(addressId,Constants.id,
                address)
        }
    }
    fun getAddresses(customerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = authApi.getAddresses(customerId))
            {
                is com.abdullrahman.ecommerce.utils.response.Response.Success ->{
                    addresses.postValue(result.data!!.addresses!!)
                }
                is com.abdullrahman.ecommerce.utils.response.Response.Error ->{
                    Log.e("AuthViewModel", "Error Addresses")
                    if (result.message!! == "No Internet Connection")
                        getAddresses(customerId)
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            authApi.delete()
            isSignedIn.postValue(false)

        }

    }

    fun SignIn() {
        isloading.value = true
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty())
            viewModelScope.launch {
                when (val auth = authApi.signIn(email.value!!, password.value!!)) {
                    is com.abdullrahman.ecommerce.utils.response.Response.Success -> {
                        if (password.value == auth.data!!.lastName) {
                            authResult.postValue(auth.data!!)
                            isSignedIn.value = true
                            isloading.value = false

                            println("Customer ${auth.data!!}")
                        } else {
                            isSignedIn.value = false
                            isloading.value = false
                        }
                    }
                    is com.abdullrahman.ecommerce.utils.response.Response.Error -> {
                        Log.e("ViewModelAuth", "error")
                        isSignedIn.value = false
                        isloading.value = false
                        if (auth.message!! == "No Internet Connection")
                            SignIn()

                    }
                }
            }
    }

    fun CustomerCheck(): LiveData<List<Customer_RoomModel>> {

        return authApi.getCustomer()
    }

    fun addAddress(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val auth = authApi.addAddress(addressItem.value!!, id)) {
                is com.abdullrahman.ecommerce.utils.response.Response.Success -> {
                    addressAdded.postValue(true)

                }
                is com.abdullrahman.ecommerce.utils.response.Response.Error -> {
                    addressAdded.postValue(false)
                    if (auth.message!! == "No Internet Connection")
                        addAddress(id)

                }
            }
        }
    }

    fun signUp() {
        val list = listOf(addressItem.value!!)
        customer.value!!.addresses = list
        viewModelScope.launch {
            when (val auth = authApi.signup(customer.value!!)) {
                is com.abdullrahman.ecommerce.utils.response.Response.Success -> {

                    isSignedUp.postValue(true)
                    println("Customer ${auth.data!!}")
                }
                is com.abdullrahman.ecommerce.utils.response.Response.Error -> {
                    Log.e("ViewModelAuth", "error")
                    isSignedUp.postValue(false)
                    if (auth.message!! == "No Internet Connection")
                        signUp()

                }
            }
        }
    }

    fun delete(addressesItem: Long,customerid:Long) {
        viewModelScope.launch(Dispatchers.Main) {
        authApi.deleteAddress(addressesItem,customerid)
        }

    }
}