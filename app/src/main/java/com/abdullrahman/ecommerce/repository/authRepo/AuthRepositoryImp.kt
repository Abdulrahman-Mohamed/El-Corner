package com.abdullrahman.ecommerce.repository.authRepo

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.abdullrahman.ecommerce.data.data_source.room.customerData.CustomerDao
import com.abdullrahman.ecommerce.data.data_source.room.customerData.Customer_RoomModel
import com.abdullrahman.ecommerce.data.data_source.shopify_api.ShopifyApi
import com.abdullrahman.ecommerce.data.models.*
import com.abdullrahman.ecommerce.domain.Constants
import com.abdullrahman.ecommerce.utils.response.Response
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@ActivityScoped
class AuthRepositoryImp @Inject constructor(@ApplicationContext val context: Context, private val api: ShopifyApi, val customerDao:CustomerDao) :
    AuthRepository {
     var id:Long=0



    override suspend fun signIn(email: String, passwrod: String): Response<Customer> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.searchCustomer(email)
        return if (result.isSuccessful && result.body()!!.customers!!.size != 0) {

            val customer: Customer = result.body()!!.customers!!.get(0)!!
            val cDao = Customer_RoomModel(
                id = customer.id,
                firstName = customer.firstName!!.split(" ")[0],
                lastName = customer.firstName!!.split(" ")[1],
                phone = customer.phone.toString(),
                email = customer.email.toString(),
                currency = customer.currency.toString(),
                addresses = customer.addresses!!.toMutableList()
            )
            id = customer.id!!

            println("customer in database:${cDao}")
            customerDao.insertCustomer(cDao)
            Response.Success(customer)

        }else{
            Response.Error("Error")
        }
        }else{
            withContext(Dispatchers.Main){
             //   Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }

    }

    override suspend fun signup(
        customer: CustomerMokup
    ): Response<CustomerModelMokup> {
        if (isNetworkConnected() && internetIsConnected()) {

        val gson = Gson()
        val json = gson.toJson(CustomerModelMokup(customer))
        Log.e("Repo",json)
        val result = api.register(CustomerModelMokup(customer) )
        return if (result.isSuccessful)
            Response.Success(result.body()!!)
        else
            Response.Error("error message")
        }else{
            withContext(Dispatchers.Main){
            //    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }

    }

    override suspend fun addAddress(
        addressesItem: AddressesItem,
        customerId: Long
    ): Response<AddressContainer> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.addAddress(Constants.id,AddressContainer(addressesItem) )
        println(result)
        return if (result.isSuccessful)
            Response.Success(result.body()!!)
        else
            Response.Error("error message")
        }else{
            withContext(Dispatchers.Main){
            //    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun deleteAddress(
        addressId: Long,
        customerId: Long
    ): Response<AddressContainer> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.deleteAddress(Constants.id,addressId)

        return if (result.isSuccessful)
            Response.Success(result.body()!!)
        else
            Response.Error("error message")
        }else{
            withContext(Dispatchers.Main){
            //    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun updateAddress(
        addressId: Long,
        customerId: Long,
        addressContainer: AddressContainer
    ): Response<AddressContainer> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.updateAddress(customerId,addressId,addressContainer)
        println(result)
        return if (result.isSuccessful)
            Response.Success(result.body()!!)
        else
            Response.Error("error message")
        }else{
            withContext(Dispatchers.Main){
             //   Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun getAddresses(customerId: Long): Response<Addresses> {
        if (isNetworkConnected() && internetIsConnected()) {

            val result = api.getAddresses(customerId)
        return if (result.isSuccessful)
        {
            Response.Success(result.body()!!)
        }else
        {
            Response.Error("Error")
        }
        }else{
            withContext(Dispatchers.Main){
              //  Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override  fun getCustomer(): LiveData<List<Customer_RoomModel>> {
       return customerDao.getCustomer()
    }

    override suspend fun delete() {
        customerDao.deleteAll()
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