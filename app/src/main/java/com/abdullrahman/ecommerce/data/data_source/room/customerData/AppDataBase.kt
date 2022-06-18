package com.abdullrahman.ecommerce.data.data_source.room.customerData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Customer_RoomModel::class,WishList::class], version = 4, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DatabaseName = "AppDataBase"
    }
    abstract fun CustomerDao(): CustomerDao
    abstract fun WishListDao(): WishListDao

}