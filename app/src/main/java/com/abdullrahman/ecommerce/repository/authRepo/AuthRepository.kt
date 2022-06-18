package com.abdullrahman.ecommerce.repository.authRepo

import androidx.lifecycle.LiveData
import com.abdullrahman.ecommerce.data.data_source.room.customerData.Customer_RoomModel
import com.abdullrahman.ecommerce.data.models.*
import com.abdullrahman.ecommerce.presentation.profile.UpdateAddressFragment
import com.abdullrahman.ecommerce.utils.response.Response

interface AuthRepository {
    suspend fun signIn(email:String,passwrod:String):Response<Customer>
    suspend fun signup(customer: CustomerMokup):Response<CustomerModelMokup>
    suspend fun addAddress(addressesItem: AddressesItem,customerId:Long):Response<AddressContainer>
    suspend fun deleteAddress(addressId:Long,customerId: Long):Response<AddressContainer>
    suspend fun updateAddress(addressId: Long,customerId: Long,addressContainer: AddressContainer):Response<AddressContainer>
    suspend fun getAddresses(customerId: Long):Response<Addresses>
     fun getCustomer(): LiveData<List<Customer_RoomModel>>
    suspend fun delete()

}