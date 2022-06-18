package com.abdullrahman.ecommerce.data.data_source.room.customerData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdullrahman.ecommerce.data.models.AddressesItem

@Entity
@JvmSuppressWildcards
@TypeConverters(
        Converters::class
)
data class Customer_RoomModel (
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long? = null,
        @ColumnInfo(name = "firstName")
        val firstName: String,
        @ColumnInfo(name = "lastName")
        val lastName:String,
        @ColumnInfo(name = "email")
        val email: String,
        @ColumnInfo(name = "phone")
        val phone: String,
        @ColumnInfo(name = "currency")
        val currency: String,
        @ColumnInfo(name = "addresses")
        val addresses: MutableList<AddressesItem?>?=null
        )