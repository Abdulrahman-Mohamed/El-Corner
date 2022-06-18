package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class Search(

	@field:SerializedName("customers")
	val customers: List<Customer?>? = null
)
