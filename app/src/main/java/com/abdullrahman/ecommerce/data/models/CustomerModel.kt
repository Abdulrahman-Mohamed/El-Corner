package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class CustomerModel (
    @SerializedName( "customer")
    val customerPostModel: Customer
)