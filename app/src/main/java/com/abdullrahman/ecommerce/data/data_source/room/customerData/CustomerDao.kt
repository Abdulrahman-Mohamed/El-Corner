package com.abdullrahman.ecommerce.data.data_source.room.customerData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CustomerDao {
    @Insert
    suspend fun insertCustomer(customer:Customer_RoomModel)
    @Delete
    suspend fun deleteCustomer(customer: Customer_RoomModel)
    @Query("DELETE  FROM Customer_RoomModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM Customer_RoomModel")
    fun getCustomer():LiveData<List<Customer_RoomModel>>
}