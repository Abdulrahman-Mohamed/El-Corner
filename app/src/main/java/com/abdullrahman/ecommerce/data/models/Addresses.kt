package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class Addresses(

	@field:SerializedName("addresses")
	val addresses: List<AddressesItem?>? = null
)
