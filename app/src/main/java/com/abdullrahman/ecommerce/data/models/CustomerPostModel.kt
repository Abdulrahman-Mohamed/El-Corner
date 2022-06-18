package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class CustomerPostModel (

    @field:SerializedName("first_name")
    val firstName: String? = null,
    @field:SerializedName("last_name")
    val lastName: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("phone")
    val phone: Any? = null,
    @field:SerializedName("addresses")
    var addresses: List<AddressesItemPostModel?>? = null,
)
data class AddressesItemPostModel(
    @field:SerializedName("country")
    val country: String? = null,
    @field:SerializedName("address1")
    val address1: String? = null,
    @field:SerializedName("last_name")
    val lastName: Any? = null,
    @field:SerializedName("city")
    val city: String? = null,
    @field:SerializedName("phone")
    val phone: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("first_name")
    val firstName: String? = null,
)