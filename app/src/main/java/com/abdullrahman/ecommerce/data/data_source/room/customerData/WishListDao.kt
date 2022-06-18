package com.abdullrahman.ecommerce.data.data_source.room.customerData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WishListDao
{
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product:WishList)
    @Delete
    suspend fun deleteProduct(product:WishList)
    @Query("DELETE  FROM WishList")
    suspend fun deleteAll()
    @Query("SELECT * FROM WishList")
    fun getProduct(): LiveData<List<WishList>>
}